package com.fbb.bean;

import java.sql.Date;
import java.text.SimpleDateFormat;



public class ThreeRed implements KLine{
	private String code;
	private String name;
	
	private float cur_increase;
	private float cur_start_price;
	private float cur_end_price;
	private long cur_trade_num;
	private float cur_max_price;
	private float cur_min_price;
	private Date cur_trade_date;
	
	private float pre_increase;
	private float pre_start_price;
	private float pre_end_price;
	private long pre_trade_num;
	private float pre_max_price;
	private float pre_min_price;
	private Date pre_trade_date;
	
	private float prepre_increase;
	private float prepre_start_price;
	private float prepre_end_price;
	private long prepre_trade_num;
	private float prepre_max_price;
	private float prepre_min_price;
	private Date prepre_trade_date;
	
	private Date update_time;
	
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getCur_increase() {
		return cur_increase;
	}

	public void setCur_increase(float cur_increase) {
		this.cur_increase = cur_increase;
	}

	public float getCur_start_price() {
		return cur_start_price;
	}

	public void setCur_start_price(float cur_start_price) {
		this.cur_start_price = cur_start_price;
	}

	public float getCur_end_price() {
		return cur_end_price;
	}

	public void setCur_end_price(float cur_end_price) {
		this.cur_end_price = cur_end_price;
	}

	public long getCur_trade_num() {
		return cur_trade_num;
	}

	public void setCur_trade_num(long cur_trade_num) {
		this.cur_trade_num = cur_trade_num;
	}

	public float getCur_max_price() {
		return cur_max_price;
	}

	public void setCur_max_price(float cur_max_price) {
		this.cur_max_price = cur_max_price;
	}

	public float getCur_min_price() {
		return cur_min_price;
	}

	public void setCur_min_price(float cur_min_price) {
		this.cur_min_price = cur_min_price;
	}

	public Date getCur_trade_date() {
		return cur_trade_date;
	}

	public void setCur_trade_date(Date cur_trade_date) {
		this.cur_trade_date = cur_trade_date;
	}

	public float getPre_increase() {
		return pre_increase;
	}

	public void setPre_increase(float pre_increase) {
		this.pre_increase = pre_increase;
	}

	public float getPre_start_price() {
		return pre_start_price;
	}

	public void setPre_start_price(float pre_start_price) {
		this.pre_start_price = pre_start_price;
	}

	public float getPre_end_price() {
		return pre_end_price;
	}

	public void setPre_end_price(float pre_end_price) {
		this.pre_end_price = pre_end_price;
	}

	public long getPre_trade_num() {
		return pre_trade_num;
	}

	public void setPre_trade_num(long pre_trade_num) {
		this.pre_trade_num = pre_trade_num;
	}

	public float getPre_max_price() {
		return pre_max_price;
	}

	public void setPre_max_price(float pre_max_price) {
		this.pre_max_price = pre_max_price;
	}

	public float getPre_min_price() {
		return pre_min_price;
	}

	public void setPre_min_price(float pre_min_price) {
		this.pre_min_price = pre_min_price;
	}

	public Date getPre_trade_date() {
		return pre_trade_date;
	}

	public void setPre_trade_date(Date pre_trade_date) {
		this.pre_trade_date = pre_trade_date;
	}

	public float getPrepre_increase() {
		return prepre_increase;
	}

	public void setPrepre_increase(float prepre_increase) {
		this.prepre_increase = prepre_increase;
	}

	public float getPrepre_start_price() {
		return prepre_start_price;
	}

	public void setPrepre_start_price(float prepre_start_price) {
		this.prepre_start_price = prepre_start_price;
	}

	public float getPrepre_end_price() {
		return prepre_end_price;
	}

	public void setPrepre_end_price(float prepre_end_price) {
		this.prepre_end_price = prepre_end_price;
	}

	public long getPrepre_trade_num() {
		return prepre_trade_num;
	}

	public void setPrepre_trade_num(long prepre_trade_num) {
		this.prepre_trade_num = prepre_trade_num;
	}

	public float getPrepre_max_price() {
		return prepre_max_price;
	}

	public void setPrepre_max_price(float prepre_max_price) {
		this.prepre_max_price = prepre_max_price;
	}

	public float getPrepre_min_price() {
		return prepre_min_price;
	}

	public void setPrepre_min_price(float prepre_min_price) {
		this.prepre_min_price = prepre_min_price;
	}

	public Date getPrepre_trade_date() {
		return prepre_trade_date;
	}

	public void setPrepre_trade_date(Date prepre_trade_date) {
		this.prepre_trade_date = prepre_trade_date;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
	
	
	
	
}
