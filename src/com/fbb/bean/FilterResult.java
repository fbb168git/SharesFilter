package com.fbb.bean;

import java.sql.Date;



public class FilterResult {
	private int id;
	private String code;
	private String filter_name;
	private int level;
	private Date trade_Date;
	private Date update_time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFilter_name() {
		return filter_name;
	}
	public void setFilter_name(String filter_name) {
		this.filter_name = filter_name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Date getTrade_Date() {
		return trade_Date;
	}
	public void setTrade_Date(Date trade_Date) {
		this.trade_Date = trade_Date;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
}
