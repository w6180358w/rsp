package com.rsp.rsp.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 统计表
 * @author sjb
 */
@Entity
@Table(name = "t_statistics", schema = "rsp", catalog = "")
public class Statistics {
    private long id;
    /**机构id*/
    private long orgId;
    /**统计时间*/
    private Timestamp countTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "org_id")
    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    @Basic
    @Column(name = "count_time")
    public Timestamp getCountTime() {
        return countTime;
    }

    public void setCountTime(Timestamp countTime) {
        this.countTime = countTime;
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
