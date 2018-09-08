package com.rsp.rsp.domain;

import javax.persistence.*;

/**
 * 大类表
 * @author sjb
 */
@Entity
@Table(name = "t_category", schema = "rsp", catalog = "")
public class Category {
    private Long id;
    /**大类名称*/
    private String name;
    /**类型*/
    private Long typeId;
    /**类型名称*/
    private String typeName;

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
    @Column(name = "type_id")
    public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
    @Basic
    @Column(name = "type_name")
    public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category that = (Category) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (typeId != null ? !typeId.equals(that.typeId) : that.typeId != null) return false;
        if (typeName != null ? !typeName.equals(that.typeName) : that.typeName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (typeId != null ? typeId.hashCode() : 0); 
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0); 
        return result;
    }
}
