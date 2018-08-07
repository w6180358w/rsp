package com.rsp.rsp.service;

import com.rsp.rsp.domain.query.OrgQuery;
import com.rsp.rsp.domain.Org;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 机构
 * @author sjb
 */
@Transactional(rollbackFor=RuntimeException.class)
public interface OrgService {

    /**
     * @return
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    List<Org> findAll();

    /**
     * 分页,不带查询条件
     * @param start 0 10
     * @param size 10
     * @return
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    Page<Org> findOrgNoCriteria(Integer start, Integer size);

    /**
     * 分页,带查询条件
     * @param start
     * @param size
     * @param orgQuery
     * @return
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    Page<Org> findOrgCriteria(Integer start,Integer size,OrgQuery orgQuery);

    /**
     * @param org
     */
    void save(Org org);

    /**
     * @param org
     */
    void update(Org org);

    /**
     * @param id
     */
    void delete(Long id);
}
