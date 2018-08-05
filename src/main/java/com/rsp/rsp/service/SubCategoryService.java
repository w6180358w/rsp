package com.rsp.rsp.service;

import com.rsp.rsp.domain.query.SubCategoryQuery;
import com.rsp.rsp.domain.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
     * @param page
     * @param size
     * @return
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    Page<SubCategory> findSubCategoryNoCriteria(Integer page, Integer size);

    /**
     * 分页,带查询条件
     * @param page
     * @param size
     * @param subCategoryQuery
     * @return
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    Page<SubCategory> findSubCategoryCriteria(Integer page, Integer size, SubCategoryQuery subCategoryQuery);

    /**
     * @param subCategory
     */
    void save(SubCategory subCategory);

    /**
     * @param subCategory
     */
    void update(SubCategory subCategory);

    /**
     * @param id
     */
    void delete(Long id);
}
