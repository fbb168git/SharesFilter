package com.fbb.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class TradeDetail {
//"competitivePri":"8.37",			/*竞买价*/
//"reservePri":"8.38",				/*竞卖价*/
	private String code;
	private String name;
	private float increPer;/*涨跌百分比*/
	private float increase;/*涨跌额*/
	private float todayStartPri;/*今日开盘价*/
	private float yestodEndPri;/*昨日收盘价*/
	private float nowPri;/*当前价格*/
	private float todayMax;/*今日最高价*/
	private float todayMin;/*今日最低价*/
	private long traNumber;/*成交量*/
	private double traAmount;/*成交金额*/
	private String minurl;/*分时K线图*/
	private String dayurl;/*日K线图*/
	private String weekurl;/*周K线图*/
	private String monthurl;/*月K线图*/
	private Date traDate;/*交易日期*/
	private Date updateTime;//更新时间
	private int status = 0;//交易状态 0正常 1停牌
	
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	
	public static TradeDetail fromJson(String jsonData){
		if(jsonData != null && !"".equalsIgnoreCase(jsonData)){
			TradeDetail result = new TradeDetail();
			JsonParser parse =new JsonParser();  
			JsonObject json=(JsonObject) parse.parse(jsonData);
//			System.out.println("resultcode:"+json.get("resultcode").getAsInt());
			int resultCode = json.get("resultcode").getAsInt();
			int errorCode = json.get("error_code").getAsInt();
			if(resultCode == 200 && errorCode == 0){
				JsonObject resultObj = json.get("result").getAsJsonArray().get(0).getAsJsonObject();
				JsonObject data = resultObj.get("data").getAsJsonObject();
				JsonObject pic = resultObj.get("gopicture").getAsJsonObject();
				
				if(data.has("gid")) {
					String code = data.get("gid").getAsString();
					if(code.contains("sz")) code = code.substring(code.lastIndexOf("z") + 1);
					if(code.contains("sh")) code = code.substring(code.lastIndexOf("h") + 1);
					result.setCode(code);
				}
				if(data.has("name")) result.setName(data.get("name").getAsString());
				if(data.has("increPer")) result.setIncrePer(data.get("increPer").getAsFloat());
				if(data.has("increase")) result.setIncrease(data.get("increase").getAsFloat());
				if(data.has("todayStartPri")) result.setTodayStartPri(data.get("todayStartPri").getAsFloat());
				if(data.has("yestodEndPri")) result.setYestodEndPri(data.get("yestodEndPri").getAsFloat());
				if(data.has("nowPri")) result.setNowPri(data.get("nowPri").getAsFloat());
				if(data.has("todayMax")) result.setTodayMax(data.get("todayMax").getAsFloat());
				if(data.has("todayMin")) result.setTodayMin(data.get("todayMin").getAsFloat());
				if(data.has("traNumber")) result.setTraNumber(data.get("traNumber").getAsLong());
				if(data.has("traAmount")) result.setTraAmount(data.get("traAmount").getAsDouble());
				if(pic.has("minurl")) result.setMinurl(pic.get("minurl").getAsString());
				if(pic.has("dayurl")) result.setDayurl(pic.get("dayurl").getAsString());
				if(pic.has("weekurl")) result.setWeekurl(pic.get("weekurl").getAsString());
				if(pic.has("monthurl")) result.setMonthurl(pic.get("monthurl").getAsString());
				if(data.has("date")) {
					String dateString = data.get("date").getAsString();
					try {
						java.util.Date jdate = dateFormat.parse(dateString);
						result.setTraDate(new Date(jdate.getTime()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				return result;
			} else if(resultCode == 202) {
				//TODO 停牌
				result.setStatus(1);
				return result;
				
			} else {
//				System.out.print("resultCode:"+resultCode+" || errorCode:"+errorCode);
			}
		}
		return null;
	}
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
	public float getIncrePer() {
		return increPer;
	}
	public void setIncrePer(float increPer) {
		this.increPer = increPer;
	}
	public float getIncrease() {
		return increase;
	}
	public void setIncrease(float increase) {
		this.increase = increase;
	}
	public float getTodayStartPri() {
		return todayStartPri;
	}
	public void setTodayStartPri(float todayStartPri) {
		this.todayStartPri = todayStartPri;
	}
	public float getYestodEndPri() {
		return yestodEndPri;
	}
	public void setYestodEndPri(float yestodEndPri) {
		this.yestodEndPri = yestodEndPri;
	}
	public float getNowPri() {
		return nowPri;
	}
	public void setNowPri(float nowPri) {
		this.nowPri = nowPri;
	}
	public float getTodayMax() {
		return todayMax;
	}
	public void setTodayMax(float todayMax) {
		this.todayMax = todayMax;
	}
	public float getTodayMin() {
		return todayMin;
	}
	public void setTodayMin(float todayMin) {
		this.todayMin = todayMin;
	}
	public long getTraNumber() {
		return traNumber;
	}
	public void setTraNumber(long traNumber) {
		this.traNumber = traNumber;
	}
	public double getTraAmount() {
		return traAmount;
	}
	public void setTraAmount(double traAmount) {
		this.traAmount = traAmount;
	}
	public Date getTraDate() {
		return traDate;
	}
	public void setTraDate(Date traDate) {
		this.traDate = traDate;
	}
	public String getMinurl() {
		return minurl;
	}
	public void setMinurl(String minurl) {
		this.minurl = minurl;
	}
	public String getDayurl() {
		return dayurl;
	}
	public void setDayurl(String dayurl) {
		this.dayurl = dayurl;
	}
	public String getWeekurl() {
		return weekurl;
	}
	public void setWeekurl(String weekurl) {
		this.weekurl = weekurl;
	}
	public String getMonthurl() {
		return monthurl;
	}
	public void setMonthurl(String monthurl) {
		this.monthurl = monthurl;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
}
