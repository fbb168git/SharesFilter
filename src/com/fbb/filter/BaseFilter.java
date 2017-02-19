package com.fbb.filter;

import java.util.ArrayList;

import com.fbb.bean.FilterResult;

public interface BaseFilter {
	public ArrayList<FilterResult> filter (ArrayList kLines);
}
