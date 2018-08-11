package com.rsp.rsp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rsp.rsp.dao.FormulaRepository;
import com.rsp.rsp.dao.OrgRepository;
import com.rsp.rsp.domain.Formula;
import com.rsp.rsp.domain.Org;
import com.rsp.rsp.domain.bean.FormulaBean;
import com.rsp.rsp.domain.bean.ParamsBean;
import com.rsp.rsp.service.FormulaService;

import fr.expression4j.basic.MathematicalElement;
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
    
    public static void main(String[] args) throws Exception {
    	Expression expression = ExpressionFactory.createExpression("result(a,b,c,x,d,e,f)=a*x^2+b*x+c");
		System.out.println("Expression name: " + expression.getName());
		
		System.out.println("Expression parameters: " + expression.getParameters());
		
		MathematicalElement element_a=NumberFactory.createReal(1);
		MathematicalElement element_b=NumberFactory.createReal(4);
		MathematicalElement element_c=NumberFactory.createReal(4);
		MathematicalElement element_x=NumberFactory.createReal(1);
		Parameters parameters=ExpressionFactory.createParameters();
		parameters.addParameter("a", element_a);
		parameters.addParameter("b", element_b);
		parameters.addParameter("c", element_c);
		parameters.addParameter("x", element_x);
		System.out.println("Value of expression:  y for x=1 a=1 b=4 c=4 :" +expression.evaluate(parameters).getRealValue());
		
		//y=x^2+6x+9=(x+3)^2
		element_a=NumberFactory.createReal(1);
		element_b=NumberFactory.createReal(6);
		element_c=NumberFactory.createReal(9);
		element_x=NumberFactory.createReal(1);
		parameters=ExpressionFactory.createParameters();
		parameters.addParameter("a", element_a);
		parameters.addParameter("b", element_b);
		parameters.addParameter("c", element_c);
		parameters.addParameter("x", element_x);
		System.out.println("Value of expression:  y for x=1 a=1 b=6 c=9 :" +expression.evaluate(parameters).getRealValue());
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
		//封装参数
		Parameters parameters=ExpressionFactory.createParameters();
		String base = this.getBaseParamExpr(bean,parameters);
		
		List<Org> orgList = this.orgRepository.findAll();
		
		Map<Long,JSONObject> orgMap = orgList.stream().collect(Collectors.toMap(Org::getId, Org::toJSON));
		//所有公式
		List<Formula> forList = this.FormulaRepository.findAll();
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
}
