package com.fbb.timertask;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimerTask;

import com.fbb.bean.FilterResult;
import com.fbb.bean.Shares;
import com.fbb.bean.ThreeRed;
import com.fbb.bean.TradeDetail;
import com.fbb.dao.FilterResultDao;
import com.fbb.dao.SharesDao;
import com.fbb.dao.ThreeRedDao;
import com.fbb.dao.TradeDetailDao;
import com.fbb.filter.ThreeRedFilter;
import com.fbb.task.FilterTask;
import com.fbb.util.Constant;
import com.fbb.util.FileUtil;
import com.fbb.util.HttpUtil;
import com.fbb.util.LogUtil;

public class NightTask extends TimerTask {
	public static final String api ="http://web.juhe.cn:8080/finance/stock/hs";
	private String mDetailFilePath;
	
	@Override
	public void run() {
		//TODO 周六日不执行操作
		Calendar cal = Calendar.getInstance();
		int DAY_OF_WEEK = cal.get(Calendar.DAY_OF_WEEK);
		if(DAY_OF_WEEK == Calendar.SUNDAY || DAY_OF_WEEK == Calendar.SATURDAY){
			LogUtil.d(Constant.LINE + " today is "+(DAY_OF_WEEK==Calendar.SUNDAY?"Sunday":"Saturday")+", Night Task don't work !" + Constant.LINE);
			return;
		}
		LogUtil.d(Constant.LINE + " I'm Night Task " + Constant.LINE);
		mDetailFilePath = createSharesDetailFile();
		if(mDetailFilePath == null) {
			LogUtil.e("创建sharesDetail文件 fail");
			return;
		}
		boolean success = requestDetailsToLocalFile(mDetailFilePath);
		if(!success){
			LogUtil.e("请求shareDetail数据并写入文件 fail");
			return;
		}
		LogUtil.e("请求shareDetail数据 success");
		ArrayList<TradeDetail> tradeDetailList = new ArrayList<TradeDetail>();
		fillArraysUseTradeDetailFile(mDetailFilePath, tradeDetailList);
		if(tradeDetailList.size() > 0) {
			LogUtil.d("读取shareDetail文件并fillinArray success");
			LogUtil.d("--读取数据条数："+tradeDetailList.size());
			TradeDetailDao dao = new TradeDetailDao();
			int successCount = dao.addOrUpdateTradeDetailList(tradeDetailList);
			LogUtil.d("--成功插入数据库条数："+successCount);
		} else {
			LogUtil.e("读取shareDetail文件并fillinArray fail");
			return;
		}
		
		ThreeRedDao threeRedDao = new ThreeRedDao();
		ArrayList<ThreeRed> threeRedList = threeRedDao.getAllThreeRedList();
		LogUtil.d("读取three_red表 条数:"+threeRedList.size());
		updateThreeRedUseTradeDetail(tradeDetailList, threeRedList);
		int successCount = threeRedDao.addOrUpdateThreeRedList(threeRedList);
		LogUtil.d("成功更新three_red表 条数:"+successCount);
		
		
		FilterTask filterTask = new FilterTask();
		filterTask.start();
	}

	public void updateThreeRedUseTradeDetail(ArrayList<TradeDetail> tradeDetailList, ArrayList<ThreeRed> threeRedList) {
		HashMap<String, ThreeRed> threeRedMap = genMapUseArray(threeRedList);
		int addCount = 0;
		int updateCount = 0;
		int realUpdateCount = 0;
		int uselessCount = 0;
		for(TradeDetail tradeDetail : tradeDetailList) {
			if(tradeDetail.getStatus() != 0) {
				uselessCount++;
				continue;//只更新正常交易的
			}
			ThreeRed threeRed = threeRedMap.get(tradeDetail.getCode());
			if(threeRed != null) {
				updateCount++;
				if(threeRed.getCur_trade_date().getTime() < tradeDetail.getTraDate().getTime()){//避免重复执行更新操作破坏数据的结构
					fillThreeRedUseTradeDetail(threeRed, tradeDetail);
					realUpdateCount++;
				}
			} else {
				addCount++;
				threeRed = new ThreeRed();
				threeRed.setCode(tradeDetail.getCode());
				threeRed.setName(tradeDetail.getName());
				threeRed.setCur_increase(tradeDetail.getIncrePer());
				threeRed.setCur_start_price(tradeDetail.getTodayStartPri());
				threeRed.setCur_end_price(tradeDetail.getNowPri());
				threeRed.setCur_max_price(tradeDetail.getTodayMax());
				threeRed.setCur_min_price(tradeDetail.getTodayMin());
				threeRed.setCur_trade_num(tradeDetail.getTraNumber());
				threeRed.setCur_trade_date(tradeDetail.getTraDate());
				
				threeRedList.add(threeRed);
				threeRedMap.put(threeRed.getCode(), threeRed);
			}
		}
		LogUtil.d("需要update到three_red表的条数："+updateCount+" 实际update到表的条数："+realUpdateCount);
		LogUtil.d("需要insert到表的条数："+addCount+" 无效数据(tingpai数据)："+uselessCount);
		
	}

	private static void fillThreeRedUseTradeDetail(ThreeRed threeRed, TradeDetail tradeDetail) {
		threeRed.setPrepre_increase(threeRed.getPre_increase());
		threeRed.setPrepre_start_price(threeRed.getPre_start_price());
		threeRed.setPrepre_end_price(threeRed.getPre_end_price());
		threeRed.setPrepre_max_price(threeRed.getPre_max_price());
		threeRed.setPrepre_min_price(threeRed.getPre_min_price());
		threeRed.setPrepre_trade_num(threeRed.getPre_trade_num());
		threeRed.setPrepre_trade_date(threeRed.getPre_trade_date());
		
		threeRed.setPre_increase(threeRed.getCur_increase());
		threeRed.setPre_start_price(threeRed.getCur_start_price());
		threeRed.setPre_end_price(threeRed.getCur_end_price());
		threeRed.setPre_max_price(threeRed.getCur_max_price());
		threeRed.setPre_min_price(threeRed.getCur_min_price());
		threeRed.setPre_trade_num(threeRed.getCur_trade_num());
		threeRed.setPre_trade_date(threeRed.getCur_trade_date());
		
		threeRed.setCur_increase(tradeDetail.getIncrePer());
		threeRed.setCur_start_price(tradeDetail.getTodayStartPri());
		threeRed.setCur_end_price(tradeDetail.getNowPri());
		threeRed.setCur_max_price(tradeDetail.getTodayMax());
		threeRed.setCur_min_price(tradeDetail.getTodayMin());
		threeRed.setCur_trade_num(tradeDetail.getTraNumber());
		threeRed.setCur_trade_date(tradeDetail.getTraDate());
	}
	
	public void fillArraysUseTradeDetailFile(String detailFilePath, ArrayList<TradeDetail> tradeDetailList) {
		FileUtil fileUtil = new FileUtil();
		BufferedReader reader = fileUtil.openReader(detailFilePath);
		try {
//			java.sql.Date tradeDate = null;
			String line = null;
			while((line = reader.readLine()) != null) {
				if(line != null && !"".equalsIgnoreCase(line)){
					String code = line.substring(0,line.indexOf("|"));
					line = line.substring(line.indexOf("|")+1);
					TradeDetail tradeDetail = TradeDetail.fromJson(line);
					if(tradeDetail == null) continue;
//					if(tradeDetail.getStatus() == 1) {//tingpai情况的处理
//						tradeDetail.setCode(code);
//						if(tradeDate != null) {
//							tradeDetail.setTraDate(tradeDate);
//						} else {
//							try {
//								java.sql.Date date = genDateFromFile(detailFilePath);
//								tradeDetail.setTraDate(date);
//							} catch (ParseException e) {
//								e.printStackTrace();
//							}
//						}
//					} else {
//						if(tradeDate == null) tradeDate = tradeDetail.getTraDate();
//					}
					tradeDetailList.add(tradeDetail);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			LogUtil.e("fillArraysUseTradeDetailFile异常："+e.getMessage());
		} finally {
			fileUtil.closeReader(reader);
		}
	}
	
	private String createSharesDetailFile() {
		String sharesDetailFolderPath = Constant.SHARES_DETAIL_FOLDER_PATH;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		String detailFolder = sharesDetailFolderPath + "/"
				+ dateFormat.format(new Date());
		File file = new File(detailFolder);
		if (!file.exists()) {
			file.mkdirs();
		}
		dateFormat = new SimpleDateFormat("yyyyMMdd");
		String detailFile = detailFolder + "/" + dateFormat.format(new Date()) + ".txt";
		
		file = new File(detailFile);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return detailFile;
	}

	private boolean requestDetailsToLocalFile(String filePath) {
		LogUtil.d("开始请求数据...");
		SharesDao sharesDao = new SharesDao();
		ArrayList<Shares> allSharesList = sharesDao.getAllSharesList();
		if(allSharesList.size() <= 0) return false;
		
		FileUtil fileUtil = new FileUtil();
		PrintWriter writer = fileUtil.openWriter(filePath);
		try {
			int count = 0;
			for (Shares shares : allSharesList) {
				String code = shares.getCode();
				if(code.startsWith("3")||code.startsWith("0")){
					code="sz"+code;
				} else {
					code="sh"+code;
				}
				String info = HttpUtil.sendGet(api, "gid="+code+"&type=&key=2a0da37a75f05c3e5959befd0b49fd35");
				if(info == null || "".equalsIgnoreCase(info)){
					continue;
				}
				writer.println(shares.getCode() + "|"+info);//pingpai的情况只读取文件无法获取code，所以code存到文件中
				count++;
				if((count % 100) == 0) {
					LogUtil.d("---已获取"+count+"条");
				} else {
					LogUtil.screenLog("---已获取"+count+"条");
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			fileUtil.closeWriter(writer);
		}
		return true;
	}
	
	private HashMap<String, ThreeRed> genMapUseArray(ArrayList<ThreeRed> threeRedList) {
		HashMap<String, ThreeRed> result = new HashMap<String, ThreeRed>();
		for(ThreeRed threeRed : threeRedList) {
			result.put(threeRed.getCode(), threeRed);
		}
		return result;
	}
}
