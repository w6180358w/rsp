package com.rsp.rsp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.rsp.rsp.dao.CategoryRepository;
import com.rsp.rsp.dao.FormulaRepository;
import com.rsp.rsp.dao.OrgRepository;
import com.rsp.rsp.dao.SubCategoryRepository;
import com.rsp.rsp.domain.Category;
import com.rsp.rsp.domain.Formula;
import com.rsp.rsp.domain.Org;
import com.rsp.rsp.domain.SubCategory;
import com.rsp.rsp.domain.bean.CategoryBean;
import com.rsp.rsp.domain.bean.FormulaBean;
import com.rsp.rsp.domain.bean.ParamsBean;
import com.rsp.rsp.service.FormulaService;

import fr.expression4j.core.Expression;
import fr.expression4j.core.Parameters;
import fr.expression4j.factory.ExpressionFactory;
import fr.expression4j.factory.NumberFactory;
import net.sf.json.JSONObject;

/**
 * 大类
 * @author sjb
 */
@Service
public class FormulaServiceImpl implements FormulaService {

    @Autowired
    private FormulaRepository FormulaRepository;
    @Autowired
    private OrgRepository orgRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Override
    public List<Formula> findAll() {
        return FormulaRepository.findAll();
    }

    @Override
    public void save(Formula Formula) {
        FormulaRepository.save(Formula);
    }

    @Override
    public void update(Formula newFormula) {
        Formula formula = FormulaRepository.findById(newFormula.getId());
        formula.setFormula(newFormula.getFormula());
        formula.setSubCategoryKey(newFormula.getSubCategoryKey());
        formula.setOrgId(newFormula.getOrgId());
        FormulaRepository.save(formula);
    }

    @Override
    public void delete(Long id) {
        FormulaRepository.deleteById(id);
    }
    
	@Override
	public List<Org> filter(FormulaBean bean) throws Exception {
		//封装参数
		Parameters parameters=ExpressionFactory.createParameters();
		String base = this.getBaseParamExpr(bean,parameters);
		
		List<Org> orgList = this.orgRepository.findAll();
		Map<Long,Org> orgMap = orgList.stream().collect(Collectors.toMap(Org::getId, org->org));
		//所有公式
		List<Formula> forList = this.FormulaRepository.findAll();
		for (Formula formula : forList) {
			//如果orgMap中机构不存在（数据不全或已经被筛除）  跳过
			if(orgMap.get(formula.getOrgId())==null) {
				continue;
			}
			//如果公式为空  跳过
			String formu = formula.getFormula();
			if(formu==null || "".equals(formu)) {
				continue;
			}
			String expr = (base+formu).toUpperCase();
			Expression expression = ExpressionFactory.createExpression(expr);
			Double value = expression.evaluate(parameters).getRealValue();
			System.out.println("机构："+orgMap.get(formula.getOrgId()).getName()+"公式："+expr+"值："+value);
			if(value<0) {
				orgMap.remove(formula.getOrgId());
			}
		}
		List<Org> result = new ArrayList<>();
		for (Entry<Long, Org> entry : orgMap.entrySet()) {
			result.add(entry.getValue());
		}
		return result;
	}

	@Override
	public List<JSONObject> tableFilter(FormulaBean bean) throws Exception {
		List<JSONObject> result = new ArrayList<>();
		if(bean.getParam()==null || bean.getParam().isEmpty()) {
			return result;
		}
		//封装参数
		Parameters parameters=ExpressionFactory.createParameters();
		String base = this.getBaseParamExpr(bean,parameters);
		
		List<Org> orgList = this.orgRepository.findAll();
		
		Map<Long,JSONObject> orgMap = orgList.stream().collect(Collectors.toMap(Org::getId, Org::toJSON));
		//所有公式
		List<Formula> forList = this.FormulaRepository.findAll((Specification<Formula>) (root, query, criteriaBuilder) -> {
			In<Object> in = criteriaBuilder.in(root.get("subCategoryKey"));
			List<Predicate> list = new ArrayList<>();
        	for (String key : bean.getKeys()) {
				in.value(key);
			}
        	list.add(in);
        	Predicate[] p = new Predicate[list.size()];
        	query.where(criteriaBuilder.and(list.toArray(p)));
        	return query.getRestriction();
      	});
		for (Formula formula : forList) {
			//如果orgMap中机构不存在（数据不全或已经被筛除）  跳过
			JSONObject org = orgMap.get(formula.getOrgId());
			if(org==null) {
				continue;
			}
			Double value = null;
			//如果公式为空  跳过
			String formu = formula.getFormula();
			if(formu!=null && !"".equals(formu)) {
				String expr = (base+formu).toUpperCase();
				Expression expression = ExpressionFactory.createExpression(expr);
				value = expression.evaluate(parameters).getRealValue();
				System.out.println("机构："+orgMap.get(formula.getOrgId()).getString("name")+"公式："+expr+"值："+value);
			}
			org.put(formula.getSubCategoryKey(), value);
			orgMap.put(formula.getOrgId(), org);
			if(value<0) {
				orgMap.remove(formula.getOrgId());
			}
		}
		
		for (Entry<Long, JSONObject> entry : orgMap.entrySet()) {
			result.add(entry.getValue());
		}
		return result;
	}
	
	private String getBaseParamExpr(FormulaBean bean,Parameters parameters) {
		StringBuffer base = new StringBuffer("result(");
		List<ParamsBean> fo = bean.getParam();
		for (int i=0,j=fo.size();i<j;i++) {
			ParamsBean p = fo.get(i);
			parameters.addParameter(p.getKey(), NumberFactory.createReal(p.getValue()));
			
			base.append(p.getKey());
			if(i+1<j) {
				base.append(",");
			}
		}
		base.append(")=");
		return base.toString();
	}

	@Override
	public List<CategoryBean> columns(String type) throws Exception {
		List<CategoryBean> result = new ArrayList<>();
		List<SubCategory> subList = this.subCategoryRepository.findAll();
		//组装大类ID和小类的映射
		Map<Long,List<SubCategory>> subMap = new HashMap<>();
		for (SubCategory sub : subList) {
			List<SubCategory> list = subMap.get(sub.getCategoryId());
			if(list==null)list = new ArrayList<>();
			list.add(sub);
			subMap.put(sub.getCategoryId(),list);
		}
		//组装对象
		List<Category> cateList = this.categoryRepository.findAll((Specification<Category>) (root, query, criteriaBuilder) -> {
            	Predicate p1 = criteriaBuilder.equal(root.get("type").as(String.class), type);
            	query.where(criteriaBuilder.and(p1));
            	return query.getRestriction();
	      	});
		for (Category category : cateList) {
			List<SubCategory> list = subMap.get(category.getId());
			if(list==null || list.isEmpty()) {
				continue;
			}
			CategoryBean bean = (CategoryBean) JSONObject.toBean(JSONObject.fromObject(category),CategoryBean.class);
			bean.setSubList(subMap.get(bean.getId()));
			result.add(bean);
		}
		return result;
	}

	@Override
	public List<JSONObject> findAll(FormulaBean bean) throws Exception {
		List<JSONObject> result = new ArrayList<>();
		
		//根据前端传来的id查询公式
		List<Formula> forList = this.FormulaRepository.findAll((Specification<Formula>) (root, query, criteriaBuilder) -> {
			In<Object> in = criteriaBuilder.in(root.get("subCategoryKey"));
			List<Predicate> list = new ArrayList<>();
			for (String key : bean.getKeys()) {
				in.value(key);
			}
			list.add(in);
			Predicate[] p = new Predicate[list.size()];
			query.where(criteriaBuilder.and(list.toArray(p)));
			return query.getRestriction();
		});
		
		//机构ID 和 公式的映射
		Map<Long,List<Formula>> forMap = new HashMap<>();
		for (Formula form : forList) {
			List<Formula> list = forMap.get(form.getOrgId());
			if(list==null)list = new ArrayList<>();
			list.add(form);
			forMap.put(form.getOrgId(), list);
		}
		
		List<Org> orgList = this.orgRepository.findAll();
		
		for (Org org : orgList) {
			JSONObject json = new JSONObject();
			json.put("name", org.getName());
			json.put("orgId", org.getId());
			
			List<Formula> list = forMap.get(org.getId());
			for (Formula formula : list) {
				JSONObject j = new JSONObject();
				j.put("id", formula.getId());
				j.put("formula", formula.getFormula());
				json.put(formula.getSubCategoryKey(), j);
			}
			result.add(json);
		}
		
		return result;
	}

	@Override
	public void merge(Formula formula) {
		if(formula.getId()==null) {
			this.save(formula);
		}else {
			this.update(formula);
		}
	}
}
