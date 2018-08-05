package com.rsp.rsp.dao;

import com.rsp.rsp.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category,String> {
}
