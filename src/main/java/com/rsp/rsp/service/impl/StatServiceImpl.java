package com.rsp.rsp.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.rsp.rsp.dao.OrgRepository;
import com.rsp.rsp.dao.StatisticsRepository;
import com.rsp.rsp.domain.Org;
import com.rsp.rsp.domain.Statistics;
import com.rsp.rsp.domain.Type;
import com.rsp.rsp.service.StatService;
import com.rsp.rsp.service.TypeService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 统计
 * @author sjb
 */
@Service
public class StatServiceImpl implements StatService {

    @Autowired
    private StatisticsRepository statisticsRepository;
    @Autowired
    private TypeService typeService;
    @Autowired
    private OrgRepository orgRepository;

	@Override
	public JSONObject statXTime(Long start, Long end,String city,String group) {
		JSONObject result = new JSONObject();
		JSONArray legend = new JSONArray();
		JSONArray series = new JSONArray();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//所有机构
		List<Org> orgList = this.orgRepository.findAll();
		
		List<Type> typeList = this.typeService.city(city, group);
		
		//所有查询记录
		List<Statistics> statList = this.statisticsRepository.findAll((Specification<Statistics>) (root, query, criteriaBuilder) -> {
	    	Predicate p2 = criteriaBuilder.gt(root.get("countTime").as(Long.class), start);
	    	Predicate p3 = criteriaBuilder.lt(root.get("countTime").as(Long.class), end);
	    	//根据类型key筛选
	    	In<Object> in = criteriaBuilder.in(root.get("typeId"));
			List<Predicate> list = new ArrayList<>();
			in.value(-1L);//防止参数为空
			for (Type type : typeList) {
				in.value(type.getId());
			}
			list.add(in);
			Predicate[] p = new Predicate[list.size()];
			
	    	Order order =criteriaBuilder.asc(root.get("countTime"));
	    	query.where(criteriaBuilder.and(p2,p3),criteriaBuilder.and(list.toArray(p))).orderBy(order);
	    	return query.getRestriction();
	    });
		//计算次数  根据查询时间和机构ID分组
		Map<String,Integer> map = new HashMap<>();
		Set<Long> timeSet = new LinkedHashSet<>();
		for (Statistics stat : statList) {
			Long time = stat.getCountTime()/1000;
			timeSet.add(time);
			String key = time+"-"+stat.getOrgId();
			Integer num = map.get(key);
			if(num==null) {
				num=1;
			}else {
				num++;
			}
			map.put(key, num);
		}
		//组装对象
		for (Org org : orgList) {
			legend.add(org.getName());
			JSONObject serie = new JSONObject();
			serie.put("name", org.getName());
			serie.put("type", "line");
			JSONArray data = new JSONArray();
			for (Long time : timeSet) {
				JSONArray arr = new JSONArray();
				arr.add(sdf.format(time*1000));
				Integer num = map.get(time+"-"+org.getId());
				arr.add(num==null?0:num);
				
				data.add(arr);
			}
			serie.put("data", data);
			series.add(serie);
		}
		
		result.put("legend", legend);
		result.put("series", series);
		return result;
	}

	@Override
	public JSONObject statXOrg(Long start, Long end,String city,String group) {
		JSONObject result = new JSONObject();
		JSONArray legend = new JSONArray();
		JSONArray series = new JSONArray();
		//所有机构
		List<Org> orgList = this.orgRepository.findAll();
		
		List<Type> typeList = this.typeService.city(city, group);
		
		//所有查询记录
		List<Statistics> statList = this.statisticsRepository.findAll((Specification<Statistics>) (root, query, criteriaBuilder) -> {
	    	Predicate p2 = criteriaBuilder.gt(root.get("countTime").as(Long.class), start);
	    	Predicate p3 = criteriaBuilder.lt(root.get("countTime").as(Long.class), end);
	    	
	    	//根据类型key筛选
	    	In<Object> in = criteriaBuilder.in(root.get("typeId"));
			List<Predicate> list = new ArrayList<>();
			in.value(-1L);//防止参数为空
			for (Type type : typeList) {
				in.value(type.getId());
			}
			list.add(in);
			Predicate[] p = new Predicate[list.size()];
	    	
	    	query.where(criteriaBuilder.and(p2,p3),criteriaBuilder.and(list.toArray(p)));
	    	return query.getRestriction();
	    });
		//计算次数  根据查询时间和机构ID分组
		Map<Long,Integer> map = new HashMap<>();
		for (Statistics stat : statList) {
			Long key = stat.getOrgId();
			Integer num = map.get(key);
			if(num==null) {
				num=1;
			}else {
				num++;
			}
			map.put(key, num);
		}
		//组装对象
		JSONObject serie = new JSONObject();
		serie.put("type", "bar");
		JSONArray data = new JSONArray();
		for (Org org : orgList) {
			legend.add(org.getName());
			JSONObject json = new JSONObject();
			json.put("name", org.getName());
			Integer num = map.get(org.getId());
			json.put("value",num==null?0:num);
			data.add(json);
		}
		serie.put("data", data);
		series.add(serie);
		result.put("legend", legend);
		result.put("xAxis", legend);
		result.put("series", series);
		return result;
	}
    
}
