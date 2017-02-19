package com.fbb.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fbb.bean.Shares;
import com.fbb.dao.SharesDao;
import com.fbb.util.Constant;
import com.fbb.util.LogUtil;

public class MorningTask extends TimerTask{

	@Override
	public void run() {
		LogUtil.d(Constant.LINE + " I'm Morning Task " + Constant.LINE);
		updateSharesList();
		//TODO 下单
	}

	private void updateSharesList() {
		ArrayList<Shares> sharesList = null;
		try {
			sharesList = requestAllSharesList();
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof IOException){
				LogUtil.e("shares列表数据解析失败");
			} else {
				LogUtil.e("获取shares列表失败");
			}
		}
		if(sharesList != null && sharesList.size() > 0){
			LogUtil.e("获取shares数据列表成功 count:"+sharesList.size());
			SharesDao sharesDao = new SharesDao();
			int successCount = sharesDao.addOrUpdateSharesList(sharesList);
			if(successCount > 0){
				LogUtil.d("更新shares表成功 successCount:"+successCount);
			} else {
				LogUtil.d("更新shares表失败");
			}
		}
	}

	private ArrayList<Shares> requestAllSharesList() throws IOException, Exception{
		ArrayList<Shares> sharesList = new ArrayList<Shares>();
//		ArrayList<Shares> othersList = new ArrayList<Shares>();
		try {
			Document doc = Jsoup.connect("http://quote.eastmoney.com/stocklist.html").get();
			Element content = doc.getElementById("quotesearch");
			Elements links = content.getElementsByTag("li");
			for (Element link : links) {  
				Elements nodes = link.getElementsByTag("a");
				for(Element node : nodes) {
					String value = node.text();
					if(value != null){
//						System.out.println(value);
						String code = value.substring(value.indexOf("(")+1, value.lastIndexOf(")"));
						String name = value.substring(0, value.lastIndexOf("("));
//						System.out.println("code:"+code+" | name:"+name);
						Shares shares = new Shares();
						shares.setCode(code);
						shares.setName(name);
						if(code.startsWith("3")||code.startsWith("6")||code.startsWith("0")){
							sharesList.add(shares);
						} else {
//							othersList.add(shares);
						}
					}
				}
			}  
		} catch (IOException e) {
			throw e;
		} catch (Exception e){
			throw e;
		}
		return sharesList;
	}
	
	private void printAllSharesList() {
		SharesDao sharesDao = new SharesDao();
		ArrayList<Shares> allSharesList = sharesDao.getAllSharesList();
		for(Shares shares : allSharesList){
			System.out.println("code:"+shares.getCode()+" || name:"+shares.getName());
		}
	}
}
