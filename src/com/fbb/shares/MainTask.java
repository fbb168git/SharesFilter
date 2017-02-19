package com.fbb.shares;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;

import com.fbb.task.HeartBeatTask;
import com.fbb.task.MorningTask;
import com.fbb.task.NightTask;
import com.fbb.util.Constant;
import com.fbb.util.LogUtil;

public class MainTask {
	private final static int MorningTaskHour = 8;
	private final static int NightTaskHour = 16;
//	private final int NightTaskMinute = 10;
	public static void main(String[] args) {
		MainTask task = new MainTask();
		task.init();
		startBeatHeartTask();
		startMorningTask();
		startNightTask();
	}

	private static void startNightTask() {
		Calendar cal = Calendar.getInstance();
		int DAY_OF_MONTH = cal.get(Calendar.DAY_OF_MONTH);
		int HOUR_OF_DAY = cal.get(Calendar.HOUR_OF_DAY);
		int MINUTE = cal.get(Calendar.MINUTE);
		LogUtil.d("currentTime:"+DAY_OF_MONTH + "号 " + HOUR_OF_DAY+":" + MINUTE);
		
		Timer mainTimer = new Timer();
		NightTask mNightTask = new NightTask();
		if(HOUR_OF_DAY >=  NightTaskHour) {
			mNightTask.run();
//			mainTimer.schedule(mMorningTask, 0);
			cal.set(Calendar.DAY_OF_MONTH, DAY_OF_MONTH + 1);//TODO 每月最后一天执行的话会有bug
		}
		cal.set(Calendar.HOUR_OF_DAY, NightTaskHour);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);        
        Date time = cal.getTime();
        LogUtil.d("NightTask首次执行时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time));
		mainTimer.scheduleAtFixedRate(mNightTask, time, 1000 * 60 * 60 * 24);
	}
	
	private static void startMorningTask() {
		Calendar cal = Calendar.getInstance();
		int DAY_OF_MONTH = cal.get(Calendar.DAY_OF_MONTH);
		int HOUR_OF_DAY = cal.get(Calendar.HOUR_OF_DAY);
		int MINUTE = cal.get(Calendar.MINUTE);
		LogUtil.d("currentTime:"+DAY_OF_MONTH + "号 " + HOUR_OF_DAY+":" + MINUTE);
		
		Timer mainTimer = new Timer();
		MorningTask mMorningTask = new MorningTask();
		if(HOUR_OF_DAY >=  MorningTaskHour) {
			mMorningTask.run();
//			mainTimer.schedule(mMorningTask, 0);
			cal.set(Calendar.DAY_OF_MONTH, DAY_OF_MONTH + 1);//TODO 每月最后一天执行的话会有bug
		}
		cal.set(Calendar.HOUR_OF_DAY, MorningTaskHour);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);        
        Date time = cal.getTime();
        LogUtil.d("MorningTask首次执行时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time));
		mainTimer.scheduleAtFixedRate(mMorningTask, time, 1000 * 60 * 60 * 24);
	}
	
	private static void startBeatHeartTask() {
		Timer mainTimer = new Timer();
		HeartBeatTask heatBeatTask = new HeartBeatTask();
		mainTimer.scheduleAtFixedRate(heatBeatTask, 0l, 1000 * 6);
	}
	
	private void init(){
		LogUtil.init();
		createFolders();
	}
	
	private void createFolders(){
		String sharesDetailFolderPath = Constant.SHARES_DETAIL_FOLDER_PATH;
		File sharesDetailFolder = new File(sharesDetailFolderPath);
		if(!sharesDetailFolder.exists()){
			sharesDetailFolder.mkdirs();
		}
	}
	
	private void startTask(){
		
	}
}
