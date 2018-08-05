package com.rsp.rsp.dao;

import com.rsp.rsp.domain.Formula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 公式
 * @author sjb
 */
public interface FormulaRepository extends JpaRepository<Formula,String>,JpaSpecificationExecutor<Formula> {

}
