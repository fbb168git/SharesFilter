package com.fbb.shares;

import java.io.File;
import java.util.ArrayList;

import com.fbb.bean.ThreeRed;
import com.fbb.bean.TradeDetail;
import com.fbb.dao.ThreeRedDao;
import com.fbb.dao.TradeDetailDao;
import com.fbb.task.FilterTask;
import com.fbb.timertask.NightTask;
import com.fbb.util.Constant;
import com.fbb.util.LogUtil;

public class ImportDataUtil {
	NightTask nightTask;
	public static void main(String[] args) {
		ImportDataUtil util = new ImportDataUtil();
		util.init();
		
		//TODO -i 仅导入数据 -f 仅过滤结果 -b 仅执行购买  -ifb 导入一个文件过滤一次结果执行一次购买 
		//-i -b -f 顺序执行 最终只过滤一次 只购买一次
		util.startImportTask();
		util.startFilterTask();
	}
	
	private void init() {
		nightTask = new  NightTask();
	}
	
	private void startImportTask() {
		LogUtil.d("--------startImportTask--------");
		String sharesDetailFolderPath = Constant.SHARES_DETAIL_FOLDER_PATH+"/";
		LogUtil.e("文件地址："+sharesDetailFolderPath);
		File mainFolder = new File(sharesDetailFolderPath);
		if(!mainFolder.exists()){
			LogUtil.e("文件目录不存在");
			return;
		}
		String[] folders = mainFolder.list();
		if(folders == null) {
			LogUtil.e("detail主目录下无文件");
			return;
		}
		orderByName(folders);
		for(int i =0;i<folders.length;i++){
			String folderPath = sharesDetailFolderPath+folders[i];
			File folder = new File(folderPath);
			LogUtil.d("准备导入"+folder.getName());
			File[] files = folder.listFiles();
			if(files == null) {
				LogUtil.e("detail分类目录下无文件");
				continue;
			}
			orderByName(files);
			for(int j=0;j<files.length;j++){
				File file = files[j];
				importFileData(file);
				LogUtil.d("---导入"+file.getName()+"完成");
			}
			
		}
	}
	
	private void importFileData(File file) {
		ArrayList<TradeDetail> tradeDetailList = new ArrayList<TradeDetail>();
		nightTask.fillArraysUseTradeDetailFile(file.getAbsolutePath(), tradeDetailList);
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
		nightTask.updateThreeRedUseTradeDetail(tradeDetailList, threeRedList);
		int successCount = threeRedDao.addOrUpdateThreeRedList(threeRedList);
		LogUtil.d("成功更新three_red表 条数:"+successCount);
	}

	private void startFilterTask() {
		FilterTask filterTask = new FilterTask();
		filterTask.start();
	}
	
	private void orderByName(String[] list) {
		if(list == null || list.length == 0) return;
		String cache = null;
		for(int j = list.length; j > 0; j--) {
			LogUtil.screenLog("第"+j+"次排序");
			for(int i =0; i < j -1; i++) {
				String left = list[i];
				String right = list[i+1];
				if(left.compareToIgnoreCase(right) > 0) {
					cache = right;
					right = left;
					left = cache;
				}
			}
		}
		LogUtil.screenLog("文件夹排序结果：");
		for(int i =0;i<list.length;i++){
			LogUtil.screenLog("---"+list[i]);
		}
	}
	
	private void orderByName(File[] list){
		if(list == null || list.length == 0) return;
		File cache = null;
		for(int j = list.length; j > 0; j--) {
			LogUtil.screenLog("第"+(list.length - j)+"次排序");
			for(int i =0; i < j -1; i++) {
				File left = list[i];
				File right = list[i+1];
				LogUtil.screenLog("i:"+i+" left:"+left.getName()+" right:"+right.getName());
				if(left.getName().compareToIgnoreCase(right.getName()) > 0) {
					LogUtil.screenLog("交换左右值");
					cache = list[i];
					list[i] = list[i+1];
					list[i+1] = cache;
				} else {
					LogUtil.screenLog("不交换");
				}
			}
		}
		LogUtil.screenLog("文件排序结果：");
		for(int i =0;i<list.length;i++){
			LogUtil.screenLog("---"+list[i]);
		}
	}
	
}
