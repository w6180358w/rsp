package com.rsp.rsp.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 统计表
 * @author sjb
 */
@Entity
@Table(name = "t_statistics", schema = "rsp", catalog = "")
public class Statistics {
    private Long id;
    /**机构id*/
    private Long orgId;
    /**统计时间*/
    private Long countTime;
    /**类型*/
    private String type;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "org_id")
    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    @Basic
    @Column(name = "count_time")
    public Long getCountTime() {
        return countTime;
    }

    public void setCountTime(Long countTime) {
        this.countTime = countTime;
    }
    @Basic
    @Column(name = "type")
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Statistics that = (Statistics) o;

        if (id != that.id) return false;
        if (orgId != that.orgId) return false;
        if (countTime != null ? !countTime.equals(that.countTime) : that.countTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (orgId ^ (orgId >>> 32));
        result = 31 * result + (countTime != null ? countTime.hashCode() : 0);
        return result;
    }
}
