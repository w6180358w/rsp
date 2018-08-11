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
    void save(Formula Formula);

    /**
     * @param Formula
     */
    void update(Formula Formula);

    /**
     * @param id
     */
    void delete(Long id);
    
    List<Org> filter(FormulaBean bean) throws Exception;
    
    List<JSONObject> tableFilter(FormulaBean bean) throws Exception;
    
    List<CategoryBean> columns(String type) throws Exception;
    
}
