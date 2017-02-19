package com.fbb.task;

import java.util.TimerTask;

import com.fbb.util.LogUtil;

public class HeartBeatTask extends TimerTask{

	@Override
	public void run() {
//		System.out.println("heart beat");
		LogUtil.writeHeart();
	}

}
