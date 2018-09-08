package com.rsp.rsp.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rsp.rsp.domain.Formula;
import com.rsp.rsp.domain.Org;
import com.rsp.rsp.domain.bean.CategoryBean;
import com.rsp.rsp.domain.bean.FormulaBean;

import net.sf.json.JSONObject;

@Transactional(rollbackFor=RuntimeException.class)
public interface FormulaService {
	/**
     * @return
     */
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    List<Formula> findAll();

    /**
     * @param Formula
     */
    Formula save(Formula Formula);
    
    /**
     * @param Formula
     */
    Formula merge(Formula Formula);

    /**
     * @param Formula
     */
    Formula update(Formula Formula);

    /**
     * @param id
     */
    void delete(Long id);
    /**
     * 手机端筛选接口 返回org对象
     * @param bean
     * @return
     * @throws Exception
     */
    List<Org> filter(FormulaBean bean) throws Exception;
    /**
     * pc端接口 返回金额对象
     * @param bean
     * @return
     * @throws Exception
     */
    List<JSONObject> tableFilter(FormulaBean bean) throws Exception;
    /**
     * pc端  公式列表（增删改查用）
     * @param type
     * @return
     * @throws Exception
     */
    List<CategoryBean> columns(Long typeId) throws Exception;
    
    List<JSONObject> findAll(FormulaBean bean) throws Exception;
    
}
