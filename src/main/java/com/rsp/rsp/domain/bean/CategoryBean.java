package com.rsp.rsp.domain.bean;

import java.util.List;

import com.rsp.rsp.domain.Category;
import com.rsp.rsp.domain.SubCategory;

public class CategoryBean extends Category{

	private List<SubCategory> subList;

	public List<SubCategory> getSubList() {
		return subList;
	}

	public void setSubList(List<SubCategory> subList) {
		this.subList = subList;
	}
	
}
