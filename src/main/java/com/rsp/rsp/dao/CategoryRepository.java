package com.rsp.rsp.dao;

import com.rsp.rsp.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 大类
 * @author sjb
 */
public interface CategoryRepository extends JpaRepository<Category,String>,JpaSpecificationExecutor<Category> {
    /**
     * 继承的findById参数是string,覆盖一下
     * @param id
     * @return
     */
    Category findById(Long id);

    /**
     * 继承的deleteById参数是string,覆盖一下
     * @param id
     */
    void deleteById(Long id);
}
