package com.rsp.rsp.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rsp.rsp.domain.Contacts;
import com.rsp.rsp.domain.query.ContactsQuery;

/**
 * 联系人
 * @author sjb
 */
@Transactional(rollbackFor=RuntimeException.class)
public interface ContactsService {
    /**
     * @return
     */
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    List<Contacts> findAll();

    /**
     * 分页,不带查询条件
     * @param start
     * @param size
     * @return
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    Page<Contacts> findContactsNoCriteria(Integer start, Integer size);

    /**
     * 分页,带查询条件
     * @param start
     * @param size
     * @param ContactsQuery
     * @return
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    Page<Contacts> findContactsCriteria(Integer start,Integer size,ContactsQuery ContactsQuery);

    /**
     * 新增
     * @param Contacts
     */
    Contacts save(Contacts Contacts);

    /**
     * 更新
     * @param Contacts
     */
    void update(Contacts Contacts);

    /**
     * 删除
     * @param id
     */
    void delete(Long id);

    /**
     *根据ID查
     * @return
     */
    Contacts findById(Long id);
    
    /**
     * 根据机构ID查  是否轮播分组
     * @param orgId
     * @return
     */
    Map<String, List<Contacts>> findByOrg(Long orgId);
}
