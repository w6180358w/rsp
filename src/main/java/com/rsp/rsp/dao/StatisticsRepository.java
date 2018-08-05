package com.rsp.rsp.dao;

import com.rsp.rsp.domain.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 统计
 * @author sjb
 */
public interface StatisticsRepository extends JpaRepository<Statistics,String>,JpaSpecificationExecutor<Statistics> {
}
