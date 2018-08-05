package com.rsp.rsp.dao;

import com.rsp.rsp.domain.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 小类
 * @author sjb
 */
public interface SubCategoryRepository extends JpaRepository<SubCategory,String>,JpaSpecificationExecutor<SubCategory> {
}
