package com.rsp.rsp.dao;

import com.rsp.rsp.domain.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 统计
 * @author sjb
 */
public interface StatisticsRepository extends JpaRepository<Statistics,String> {
}
