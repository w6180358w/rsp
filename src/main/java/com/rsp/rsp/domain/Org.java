package com.rsp.rsp.domain;

import javax.persistence.*;

import net.sf.json.JSONObject;

/**
 * 机构表
 * @author sjb
 */
@Entity
@Table(name = "t_org", schema = "rsp", catalog = "")
public class Org {
    private Long id;
    /**机构名称*/
    private String name;
    /**额度 小*/
    private Long limitMin;
    /**额度 大*/
    private Long limitMax;
    /**期限*/
    private Long term;
    /**利率小*/
    private String interestRateMin;
    /**利率大*/
    private String interestRateMax;
    /**申请条件*/
    private String requirements;
    /**申请材料*/
    private String material;
    /**logo*/
    private String logo;
    /**描述*/
    private String desc;
    /**优势*/
    private String strengths;
    /**排序*/
    private Long order;

    /**列表显示时额度拼成一个字符串*/
    private String limitString;
    /**列表显示时利率拼成一个字符串*/
    private String interestRateString;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "loan_limit_min")
    public Long getLimitMin() {
        return limitMin;
    }

    public void setLimitMin(Long limitMin) {
        this.limitMin = limitMin;
    }

    @Basic
    @Column(name = "loan_limit_max")
    public Long getLimitMax() {
        return limitMax;
    }

    public void setLimitMax(Long limitMax) {
        this.limitMax = limitMax;
    }

    @Basic
    @Column(name = "term")
    public Long getTerm() {
        return term;
    }

    public void setTerm(Long term) {
        this.term = term;
    }

    @Basic
    @Column(name = "interest_rate_min")
    public String getInterestRateMin() {
        return interestRateMin;
    }

    public void setInterestRateMin(String interestRateMin) {
        this.interestRateMin = interestRateMin;
    }

    @Basic
    @Column(name = "interest_rate_max")
    public String getInterestRateMax() {
        return interestRateMax;
    }

    public void setInterestRateMax(String interestRateMax) {
        this.interestRateMax = interestRateMax;
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
    @Column(name = "description")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Basic
    @Column(name = "strengths")
    public String getStrengths() {
        return strengths;
    }

    public void setStrengths(String strengths) {
        this.strengths = strengths;
    }
    @Basic
    @Column(name = "order_by")
    public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	@Transient
    public String getLimitString() {
        return limitString;
    }

    public void setLimitString(String limitString) {
        this.limitString = limitString;
    }

    @Transient
    public String getInterestRateString() {
        return interestRateString;
    }

    public void setInterestRateString(String interestRateString) {
        this.interestRateString = interestRateString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Org that = (Org) o;

        if (id != that.id) return false;
        if (limitMin != that.limitMin) return false;
        if (limitMax != that.limitMax) return false;
        if (term != that.term) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (interestRateMin != null ? !interestRateMin.equals(that.interestRateMin) : that.interestRateMin != null) return false;
        if (interestRateMax != null ? !interestRateMax.equals(that.interestRateMax) : that.interestRateMax != null) return false;
        if (requirements != null ? !requirements.equals(that.requirements) : that.requirements != null) return false;
        if (material != null ? !material.equals(that.material) : that.material != null) return false;
        if (logo != null ? !logo.equals(that.logo) : that.logo != null) return false;
        if (desc != null ? !desc.equals(that.desc) : that.desc != null) return false;
        if (strengths != null ? !strengths.equals(that.strengths) : that.strengths != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) (limitMin ^ (limitMin >>> 32));
        result = 31 * result + (int) (limitMax ^ (limitMax >>> 32));
        result = 31 * result + (int) (term ^ (term >>> 32));
        result = 31 * result + (interestRateMin != null ? interestRateMin.hashCode() : 0);
        result = 31 * result + (interestRateMax != null ? interestRateMax.hashCode() : 0);
        result = 31 * result + (requirements != null ? requirements.hashCode() : 0);
        result = 31 * result + (material != null ? material.hashCode() : 0);
        result = 31 * result + (logo != null ? logo.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (strengths != null ? strengths.hashCode() : 0);
        return result;
    }
    
    public JSONObject toJSON(){
    	JSONObject json = new JSONObject();
		json.put("name", this.getName());
		json.put("orgId", this.getId());
		json.put("id", this.getId());
    	return json;
    }
}
