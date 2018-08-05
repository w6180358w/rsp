package com.rsp.rsp.dao;

import com.rsp.rsp.domain.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 小类
 * @author sjb
 */
public interface SubCategoryRepository extends JpaRepository<SubCategory,String>,JpaSpecificationExecutor<SubCategory> {
    /**
     * 继承的findById参数是string,覆盖一下
     * @param id
     * @return
     */
    SubCategory findById(Long id);

    /**
     * 继承的deleteById参数是string,覆盖一下
     * @param id
     */
    void deleteById(Long id);
}
