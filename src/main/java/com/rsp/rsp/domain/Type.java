package com.rsp.rsp.domain;

import javax.persistence.*;

/**
 * 类型表
 * @author sjb
 */
@Entity
@Table(name = "t_type", schema = "rsp", catalog = "")
public class Type {
    private Long id;
    /**类型名称*/
    private String name;
    /**类型*/
    private String group;
    /**
     * 城市
     */
    private String city;
    /**
     * 城市
     */
    private String cityName;

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
    @Column(name = "type_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "type_group")
    public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	@Basic
	@Column(name = "type_city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Basic
	@Column(name = "city_name")
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Type that = (Type) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (group != null ? !group.equals(that.group) : that.group != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (cityName != null ? !cityName.equals(that.cityName) : that.cityName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
        return result;
    }
}
