package com.fbb.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtil {
	
	public static SimpleDateFormat mTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat mMonthFormat = new SimpleDateFormat("yyyyMM");
	private static final String LogFolderPath = Constant.LOG_PATH;
	private static final String HeartFilePath = LogFolderPath+"/heart.txt";
	private static final String NormalLogPath = LogFolderPath+"/normal.txt";
	
	private static boolean openScreenLog = true;
	public static boolean init() {
		File logFolder = new File(LogFolderPath);
		if(!logFolder.exists()){
			logFolder.mkdirs();
		}
		return true;
	}
	
	public static void writeHeart() {
		writeLocal("更新", HeartFilePath, false, false, null);
	}
	
	public static void writeNormalLog(String msg) {
		String logFolderPath = LogFolderPath +"/"+ mMonthFormat.format(new Date());
		File folder = new File(logFolderPath);
		if(!folder.exists()) {
			folder.mkdirs();
		}
		String logFilePath = logFolderPath+"/"+mDateFormat.format(new Date())+".txt";
		File file = new File(logFilePath);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		writeLocal(msg, logFilePath, true, false, null);
	}
	
	public static void writeErrorLog(String msg) {
		String logFolderPath = LogFolderPath +"/"+ mMonthFormat.format(new Date());
		File folder = new File(logFolderPath);
		if(!folder.exists()) {
			folder.mkdirs();
		}
		String logFilePath = logFolderPath+"/"+mDateFormat.format(new Date())+".txt";
		File file = new File(logFilePath);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		writeLocal(msg, logFilePath, true, true, "#ff0000");
	}
	
	public static void d(String msg) {
		if(openScreenLog) {
			System.out.println(msg);
		}
		writeNormalLog(msg);
	}
	
	public static void e(String msg) {
		if(openScreenLog) {
			System.out.println(msg);
		}
		writeErrorLog(msg);
	}
	
	public static void screenLog(String msg){
		if(openScreenLog) {
			System.out.println(msg);
		}
	}
	
	public static void writeLocal(String msg, String path, boolean append,boolean useColor, String colorText){
		PrintWriter writer = null;
		FileWriter fWriter = null;
		try {
			File logFile = new File(path);
			if(!logFile.exists()) {
				logFile.createNewFile();
			}
			fWriter = new FileWriter(logFile, append);
			writer = new PrintWriter(fWriter);
			String formatTimeText = mTimeFormat.format(new Date());
			String content = formatTimeText +" | "+ msg;
			if(useColor) {
				if(colorText == null || "".equalsIgnoreCase("")){
					content = content + "<br>";
				} else {
					content = "<font color=\""+colorText+"\">"+content + "</font><br>";
				}
			}
			writer.println(content);
			writer.flush();
			fWriter.close();
			writer.close();
			fWriter = null;
			writer = null;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(fWriter != null){
					fWriter.close();
					fWriter = null;
				}
				if(writer != null){
					writer.close();
					writer = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
