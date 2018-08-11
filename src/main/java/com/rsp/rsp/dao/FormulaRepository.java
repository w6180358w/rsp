package com.rsp.rsp.dao;

import com.rsp.rsp.domain.Formula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 公式
 * @author sjb
 */
public interface FormulaRepository extends JpaRepository<Formula,String>,JpaSpecificationExecutor<Formula> {
	/**
     * 继承的findById参数是string,覆盖一下
     * @param id
     * @return
     */
	Formula findById(Long id);

    /**
     * 继承的deleteById参数是string,覆盖一下
     * @param id
     */
    void deleteById(Long id);
}
