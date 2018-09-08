package com.rsp.rsp.service;

import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONObject;

/**
 * 大类
 * @author sjb
 */
@Transactional(rollbackFor=RuntimeException.class)
public interface StatService {
	/**
	 * X轴为时间
	 * @param start
	 * @param end
	 * @return
	 */
	JSONObject statXTime(Long start,Long end,String city,String group);
	/**
	 * X轴为机构
	 * @param start
	 * @param end
	 * @return
	 */
	JSONObject statXOrg(Long start,Long end,String city,String group);
}
