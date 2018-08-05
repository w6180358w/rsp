package com.rsp.rsp.dao;

import com.rsp.rsp.domain.Org;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 机构
 * @author sjb
 */
public interface OrgRepository extends JpaRepository<Org,String> {
}
