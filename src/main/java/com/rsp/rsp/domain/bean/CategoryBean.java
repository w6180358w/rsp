package com.rsp.rsp.domain.bean;

import java.util.List;

import com.rsp.rsp.domain.Category;
import com.rsp.rsp.domain.SubCategory;
import com.rsp.rsp.domain.Type;

public class CategoryBean extends Category{

	private List<SubCategory> subList;
	
	private Type type;

	public List<SubCategory> getSubList() {
		return subList;
	}

	public void setSubList(List<SubCategory> subList) {
		this.subList = subList;
	}

	public Type getTypeObj() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
}
