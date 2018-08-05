package com.rsp.rsp.domain;

import javax.persistence.*;

/**
 * 机构表
 * @author sjb
 */
@Entity
@Table(name = "t_org", schema = "rsp", catalog = "")
public class Org {
    private long id;
    /**机构名称*/
    private String name;
    /**额度*/
    private long limit;
    /**期限*/
    private long term;
    /**利率*/
    private String interestRate;
    /**申请条件*/
    private String requirements;
    /**申请材料*/
    private String material;
    /**logo*/
    private String logo;
    /**描述*/
    private String desc;
    /**联系人*/
    private String contacts;
    /**联系电话*/
    private String phone;
    /**优势*/
    private String strengths;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "limit")
    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    @Basic
    @Column(name = "term")
    public long getTerm() {
        return term;
    }

    public void setTerm(long term) {
        this.term = term;
    }

    @Basic
    @Column(name = "interest_rate")
    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    @Basic
    @Column(name = "requirements")
    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    @Basic
    @Column(name = "material")
    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Basic
    @Column(name = "logo")
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Basic
    @Column(name = "desc")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Basic
    @Column(name = "contacts")
    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "strengths")
    public String getStrengths() {
        return strengths;
    }

    public void setStrengths(String strengths) {
        this.strengths = strengths;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Org that = (Org) o;

        if (id != that.id) return false;
        if (limit != that.limit) return false;
        if (term != that.term) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (interestRate != null ? !interestRate.equals(that.interestRate) : that.interestRate != null) return false;
        if (requirements != null ? !requirements.equals(that.requirements) : that.requirements != null) return false;
        if (material != null ? !material.equals(that.material) : that.material != null) return false;
        if (logo != null ? !logo.equals(that.logo) : that.logo != null) return false;
        if (desc != null ? !desc.equals(that.desc) : that.desc != null) return false;
        if (contacts != null ? !contacts.equals(that.contacts) : that.contacts != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (strengths != null ? !strengths.equals(that.strengths) : that.strengths != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) (limit ^ (limit >>> 32));
        result = 31 * result + (int) (term ^ (term >>> 32));
        result = 31 * result + (interestRate != null ? interestRate.hashCode() : 0);
        result = 31 * result + (requirements != null ? requirements.hashCode() : 0);
        result = 31 * result + (material != null ? material.hashCode() : 0);
        result = 31 * result + (logo != null ? logo.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (strengths != null ? strengths.hashCode() : 0);
        return result;
    }
}
