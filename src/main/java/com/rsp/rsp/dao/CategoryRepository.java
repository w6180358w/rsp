package com.rsp.rsp.dao;

import com.rsp.rsp.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 大类
 * @author sjb
 */
public interface CategoryRepository extends JpaRepository<Category,String> {
}
