package com.fbb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlPaser {
	public static final String api ="http://web.juhe.cn:8080/finance/stock/hs";
	public static final String EncodeType = "UTF-8";
	public static void main(String[] args) {
		PrintWriter writer = openWriter("D:/20170217.txt");
		PrintWriter writerShares = openWriter("D:/20170217-share.txt");
		PrintWriter writerInfo = openWriter("D:/20170217-info.txt");
		try {
			Document doc = Jsoup.connect("http://quote.eastmoney.com/stocklist.html").get();
			Element content = doc.getElementById("quotesearch"); 
			Elements links = content.getElementsByTag("li");  
			int count = 0;
			int countShare = 0;
			boolean tag = false;
			for (Element link : links) {  
				Elements nodes = link.getElementsByTag("a");
				for(Element node : nodes){
					String value = node.text();
					if(value != null){
						count++;
						System.out.println(value);
						writer.println(value);
						String code = value.substring(value.indexOf("(")+1, value.lastIndexOf(")"));
						System.out.println(code);
						if(code.startsWith("3")||code.startsWith("6")||code.startsWith("0")){
							writerShares.println(code);
							countShare++;
							if(code.startsWith("3")||code.startsWith("0")){
								code="sz"+code;
							} else {
								code="sh"+code;
							}
//							if(!tag){
								String info = HttpUtil.sendGet(api, "gid="+code+"&type=&key=2a0da37a75f05c3e5959befd0b49fd35");
								writerInfo.println(info);
//								tag = true;
//							}
						}
					}
				}
			}  
			System.out.println("count:"+count +"|| countShare:"+countShare);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeWriter(writer);
			closeWriter(writerInfo);
			closeWriter(writerShares);
			writer = null;
			writerInfo = null;
			writerShares = null;
		}

	}
	
	private static PrintWriter openWriter(String path){
		PrintWriter printWriter = null;
		try {
			File file = new File(path);
			if(!file.exists()){
				file.createNewFile();
			}
			OutputStreamWriter outstream = new OutputStreamWriter(new FileOutputStream(path), EncodeType);
			printWriter = new PrintWriter(outstream);
			
//			FileWriter fileWriter=new FileWriter(path);  
//			printWriter = new PrintWriter(fileWriter);
	        
//			FileOutputStream writerStream = new FileOutputStream(file);    
//			bufferWriter = new BufferedWriter(new OutputStreamWriter(writerStream, "GBK"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return printWriter;
	}
	
	private static void closeWriter(PrintWriter writer){
		if(writer != null){
			writer.flush();
			writer.close();
		}
	}

}
