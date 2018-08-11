package com.rsp.rsp.domain;

import javax.persistence.*;

/**
 * 小类表
 * @author sjb
 */
@Entity
@Table(name = "t_sub_category", schema = "rsp", catalog = "")
public class SubCategory {
    private long id;
    /**名称*/
    private String name;
    /**大类id*/
    private Long categoryId;
    /**小类的公式参数key，唯一*/
    private String paramKey;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "category_id")
    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "param_key")
    public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubCategory that = (SubCategory) o;

        if (id != that.id) return false;
        if (categoryId != that.categoryId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (paramKey != null ? !paramKey.equals(that.paramKey) : that.paramKey != null) return false;

        return true;
    }

	@Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) (categoryId ^ (categoryId >>> 32));
        result = 31 * result + (paramKey != null ? paramKey.hashCode() : 0);
        return result;
    }
}
