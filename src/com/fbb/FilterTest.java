package com.fbb;

public class FilterTest {
	final String appkey = "2a0da37a75f05c3e5959befd0b49fd35";
	final static String api = "http://web.juhe.cn:8080/finance/stock/hs";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		gid=sh600006&type=&key=2a0da37a75f05c3e5959befd0b49fd35
		String result = HttpUtil.sendGet(api, "gid=sh601009&key=2a0da37a75f05c3e5959befd0b49fd35");
		System.out.print(result);
	}

}
