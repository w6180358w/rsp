package com.rsp.rsp.dao;

import com.rsp.rsp.domain.Org;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 机构
 * @author sjb
 */
public interface OrgRepository extends JpaRepository<Org,String>,JpaSpecificationExecutor<Org> {
    /**
     * 继承的findById参数是string,覆盖一下
     * @param id
     * @return
     */
    Org findById(Long id);

    /**
     * 继承的deleteById参数是string,覆盖一下
     * @param id
     */
    void deleteById(Long id);
}
