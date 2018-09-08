package com.rsp.rsp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rsp.rsp.domain.Type;
import com.rsp.rsp.domain.query.TypeQuery;

/**
 * 大类
 * @author sjb
 */
@Transactional(rollbackFor=RuntimeException.class)
public interface TypeService {
    /**
     * @return
     */
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    List<Type> findAll();

    /**
     * 分页,不带查询条件
     * @param start
     * @param size
     * @return
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    Page<Type> findTypeNoCriteria(Integer start, Integer size);

    /**
     * 分页,带查询条件
     * @param start
     * @param size
     * @param TypeQuery
     * @return
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    Page<Type> findTypeCriteria(Integer start,Integer size,TypeQuery TypeQuery);

    /**
     * @param Type
     */
    Type save(Type Type);

    /**
     * @param Type
     */
    void update(Type Type);

    /**
     * 删除类型
     * @param id
     */
    void delete(Long id);

    /**
     *
     * @return
     */
    Type findById(Long id);

    
    /**
     * 根据城市和分组查
     * @return
     */
    List<Type> city(String city,String group);
    /**
     * 根据唯一标识符查询
     */
    List<Type> inId(List<Long> idList);
}
