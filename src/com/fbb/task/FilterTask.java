package com.fbb.task;

import java.util.ArrayList;

import com.fbb.bean.FilterResult;
import com.fbb.bean.ThreeRed;
import com.fbb.dao.FilterResultDao;
import com.fbb.dao.ThreeRedDao;
import com.fbb.filter.ThreeRedFilter;
import com.fbb.util.LogUtil;

public class FilterTask {
	
	public void start() {
		LogUtil.d("--------start FilterTask--------");
		ThreeRedDao threeRedDao = new ThreeRedDao();
		ArrayList<ThreeRed> threeRedList = threeRedDao.getAllThreeRedList();
		LogUtil.d("读取three_red表 条数:"+threeRedList.size());
		
		ThreeRedFilter filter = new ThreeRedFilter();
		ArrayList<FilterResult> filterResults = filter.filter(threeRedList);
		LogUtil.d("-------- end FilterTask --------");
		LogUtil.d("筛选结果: 共"+filterResults.size()+"条");
		printFilterResultLog(filterResults);
		
		ArrayList<FilterResult> updateResults = new ArrayList<FilterResult>();
		for(FilterResult result : filterResults){
			if(result.getLevel() >= 3){
				updateResults.add(result);
			}
		}
		FilterResultDao filterResultDao = new FilterResultDao();
		int successCount = filterResultDao.addFilterResultList(updateResults);
		LogUtil.d("成功插入filter_result表条数 "+successCount);
	}
	
	private void printFilterResultLog(ArrayList<FilterResult> filterResults) {
		StringBuffer leve1 = new StringBuffer();
		StringBuffer leve2 = new StringBuffer();
		StringBuffer leve3 = new StringBuffer();
		StringBuffer leve4 = new StringBuffer();
		int countlevel1 = 0;
		int countlevel2 = 0;
		int countlevel3 = 0;
		int countlevel4 = 0;
		for(FilterResult filterResult : filterResults){
			if(filterResult.getLevel() == 1){
				leve1.append(filterResult.getCode());
				leve1.append(",");
				countlevel1++;
			} else if(filterResult.getLevel() == 2){
				leve2.append(filterResult.getCode());
				leve2.append(",");
				countlevel2++;
			}else if(filterResult.getLevel() == 3){
				leve3.append(filterResult.getCode());
				leve3.append(",");
				countlevel3++;
			} else if(filterResult.getLevel() == 4){
				leve4.append(filterResult.getCode());
				leve4.append(",");
				countlevel4++;
			}
		}
		LogUtil.d("level4: "+countlevel4+"条  "+leve4.toString());
		LogUtil.d("level3: "+countlevel3+"条  "+leve3.toString());
		LogUtil.d("level2: "+countlevel2+"条  "+leve2.toString());
		LogUtil.d("level1: "+countlevel1+"条  "+leve1.toString());
		leve1 = null;
		leve2 = null;
		leve3 = null;
		leve4 = null;
	}
}
