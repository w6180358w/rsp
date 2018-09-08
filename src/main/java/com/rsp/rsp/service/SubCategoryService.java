package com.rsp.rsp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rsp.rsp.domain.SubCategory;
import com.rsp.rsp.domain.query.SubCategoryQuery;

/**
 * 机构
 * @author sjb
 */
@Transactional(rollbackFor=RuntimeException.class)
public interface SubCategoryService {

    /**
     * @return
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    List<SubCategory> findAll();

    /**
     * 分页,不带查询条件
     * @param start
     * @param size
     * @return
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    Page<SubCategory> findSubCategoryNoCriteria(Integer start, Integer size);

    /**
     * 分页,带查询条件
     * @param start
     * @param size
     * @param subCategoryQuery
     * @return
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    Page<SubCategory> findSubCategoryCriteria(Integer start, Integer size, SubCategoryQuery subCategoryQuery);

    /**
     * @param subCategory
     */
    SubCategory save(SubCategory subCategory);

    /**
     * @param subCategory
     */
    void update(SubCategory subCategory);

    /**
     * 删除小类时同时删除对应的公式
     * @param id
     */
    void delete(Long id);

    /**
     *
     * @param id
     * @return
     */
    SubCategory findById(Long id);
}
