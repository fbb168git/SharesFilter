package com.fbb.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class FileUtil {
	public static final String EncodeType = "UTF-8";
	
	public PrintWriter openWriter(String path){
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
	
	public void closeWriter(PrintWriter writer){
		if(writer != null){
			writer.flush();
			writer.close();
		}
	}
	
	public BufferedReader openReader(String path){
		BufferedReader reader = null;
		try {
			FileReader fileReader = new FileReader(path);
			reader =  new BufferedReader(fileReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
		return reader;
	}
	
	public void closeReader(BufferedReader reader) {
		if(reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static final String resFolderName = "shares-filter";
	public static String getProjectResPath() {
		return System.getProperty("user.home") + "/" + resFolderName;
		 
	}
}
