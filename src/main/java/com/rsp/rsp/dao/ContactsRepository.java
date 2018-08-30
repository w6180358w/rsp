package com.rsp.rsp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.rsp.rsp.domain.Contacts;

/**
 * 
 * @author sjb
 */
public interface ContactsRepository extends JpaRepository<Contacts,String>,JpaSpecificationExecutor<Contacts> {
    /**
     * 继承的findById参数是string,覆盖一下
     * @param id
     * @return
     */
	Contacts findById(Long id);

    /**
     * 继承的deleteById参数是string,覆盖一下
     * @param id
     */
    void deleteById(Long id);
    
    void deleteByOrgId(Long id);
}
