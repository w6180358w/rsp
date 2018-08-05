package com.rsp.rsp.service;

import com.rsp.rsp.dao.query.OrgQuery;
import com.rsp.rsp.domain.Org;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
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
     * @param page
     * @param size
     * @return
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    Page<Org> findOrgNoCriteria(Integer page, Integer size);

    /**
     * 分页,带查询条件
     * @param page
     * @param size
     * @return
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    Page<Org> findOrgCriteria(Integer page,Integer size,OrgQuery orgQuery);

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
