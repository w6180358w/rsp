package com.rsp.rsp.service;

import com.rsp.rsp.domain.query.CategoryQuery;
import com.rsp.rsp.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 大类
 * @author sjb
 */
@Transactional(rollbackFor=RuntimeException.class)
public interface CategoryService {
    /**
     * @return
     */
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    List<Category> findAll();

    /**
     * 分页,不带查询条件
     * @param start
     * @param size
     * @return
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    Page<Category> findCategoryNoCriteria(Integer start, Integer size);

    /**
     * 分页,带查询条件
     * @param start
     * @param size
     * @param categoryQuery
     * @return
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    Page<Category> findCategoryCriteria(Integer start,Integer size,CategoryQuery categoryQuery);

    /**
     * @param category
     */
    void save(Category category);

    /**
     * @param category
     */
    void update(Category category);

    /**
     * @param id
     */
    void delete(Long id);

    /**
     *
     * @return
     */
    Category findById(Long id);

    /**
     * 查询全部category,返回id和name的map
     * @return
     */
    Map<Long,String> findIdAndNameMap();
}
