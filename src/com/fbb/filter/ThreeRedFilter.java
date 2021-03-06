package com.fbb.filter;

import java.sql.Date;
import java.util.ArrayList;

import com.fbb.bean.FilterResult;
import com.fbb.bean.ThreeRed;


public class ThreeRedFilter implements BaseFilter{

	public static void main(String[] args) {

	}

	@Override
	public ArrayList<FilterResult> filter(ArrayList kLines) {
		ArrayList<FilterResult> result = new ArrayList<FilterResult>();
		for(Object kline : kLines) {
			if(!(kline instanceof ThreeRed))  continue;
			ThreeRed bean = (ThreeRed) kline;
			Date cur_trade_date = bean.getCur_trade_date() ;
			Date pre_trade_date = bean.getPre_trade_date();
			Date prepre_trade_date = bean.getPrepre_trade_date();
			if(!(cur_trade_date != null && pre_trade_date != null && prepre_trade_date != null)){
				continue;
			}
			if(!(cur_trade_date.getTime() > pre_trade_date.getTime() &&  pre_trade_date.getTime() > prepre_trade_date.getTime())){
				continue;
			}
			//TODO importent 处于底部
			//上涨
			if(!(bean.getCur_increase() > 0 && bean.getPre_increase() > 0 && bean.getPrepre_increase() > 0)) {
				continue;
			}
			//红
			if(!(bean.getCur_end_price() > bean.getCur_start_price() && bean.getPre_end_price() > bean.getPre_start_price() && bean.getPrepre_end_price() > bean.getPrepre_start_price())) {
				continue;
			}
			FilterResult filterResult = new FilterResult();
			filterResult.setCode(bean.getCode());
			filterResult.setFilter_name("three_red");
			filterResult.setTrade_Date(bean.getCur_trade_date());
			filterResult.setLevel(1);
			result.add(filterResult);
			
			//交易量增加
			if(!(bean.getCur_trade_num() > bean.getPre_trade_num() && bean.getPre_trade_num() > bean.getPrepre_trade_num())){
				continue;
			}
			filterResult.setLevel(2);
			
			//三红柱交错
			if(!(bean.getCur_start_price() < bean.getPre_end_price() && bean.getCur_end_price() > bean.getPre_end_price()
					&& bean.getPre_start_price() < bean.getPrepre_end_price() && bean.getPre_end_price() > bean.getPrepre_end_price())){
				continue;
			}
			filterResult.setLevel(3);
			
			// 线小于红柱实体
			if(Math.abs(bean.getCur_max_price() - bean.getCur_end_price()) <= Math.abs(bean.getCur_end_price() - bean.getCur_start_price())){
				if(Math.abs(bean.getCur_start_price() -bean.getCur_min_price()) <= Math.abs(bean.getCur_end_price() - bean.getCur_start_price())){
					if(Math.abs(bean.getPre_max_price() - bean.getPre_end_price()) <= Math.abs(bean.getPre_end_price() - bean.getPre_start_price())){
						if(Math.abs(bean.getPre_start_price() -bean.getPre_min_price()) <= Math.abs(bean.getPre_end_price() - bean.getPre_start_price())){
							if(Math.abs(bean.getPrepre_max_price() - bean.getPrepre_end_price()) <= Math.abs(bean.getPrepre_end_price() - bean.getPrepre_start_price())){
								if(Math.abs(bean.getPrepre_start_price() -bean.getPrepre_min_price()) <= Math.abs(bean.getPrepre_end_price() - bean.getPrepre_start_price())){
									filterResult.setLevel(4);
								}
							}
						}
					}
				}
			}
		}
		return result;
	}

}
