package com.rsp.rsp.domain.bean;

import java.util.List;

public class FormulaBean{

	private List<String> keys;
	
	private List<ParamsBean> param;
	
	private Integer draw;

	public List<ParamsBean> getParam() {
		return param;
	}

	public void setParam(List<ParamsBean> param) {
		this.param = param;
	}

	public List<String> getKeys() {
		return keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}
}
