package com.rsp.rsp.domain.bean;

public class ParamsBean {

	private String key;
	private Double value;
	public String getKey() {
		return key.toUpperCase();
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
}
