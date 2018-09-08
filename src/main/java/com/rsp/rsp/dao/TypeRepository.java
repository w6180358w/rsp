package com.rsp.rsp.dao;

import com.rsp.rsp.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 大类
 * @author sjb
 */
public interface TypeRepository extends JpaRepository<Type,String>,JpaSpecificationExecutor<Type> {
    /**
     * 继承的findById参数是string,覆盖一下
     * @param id
     * @return
     */
    Type findById(Long id);

    /**
     * 继承的deleteById参数是string,覆盖一下
     * @param id
     */
    void deleteById(Long id);
}
