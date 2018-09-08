package com.rsp.rsp.domain.bean;

import java.util.List;

public class FormulaBean{

	private List<String> keys;		//手机端页面  参数集合  小类ID_rsp_参数值
	
	private List<Long> ids;		//小类ID集合
	
	private List<ParamsBean> param;
	
	private Integer draw;
	
	private Long typeId;
	
	public List<ParamsBean> getParam() {
		return param;
	}

	public void setParam(List<ParamsBean> param) {
		this.param = param;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public List<String> getKeys() {
		return keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}

}
