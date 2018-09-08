package com.rsp.rsp.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.rsp.rsp.dao.TypeRepository;
import com.rsp.rsp.domain.Type;
import com.rsp.rsp.domain.query.TypeQuery;
import com.rsp.rsp.service.TypeService;

/**
 * 大类
 * @author sjb
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Override
    public List<Type> findAll() {
        return typeRepository.findAll();
    }

    @Override
    public Page<Type> findTypeNoCriteria(Integer start, Integer size) {
        Pageable pageable = PageRequest.of(start/size, size, Sort.Direction.ASC, "id");
        return typeRepository.findAll(pageable);
    }

    @Override
    public Page<Type> findTypeCriteria(Integer start, Integer size, TypeQuery typeQuery) {
        Pageable pageable = PageRequest.of(start/size, size, Sort.Direction.DESC, "id");
        Page<Type> bookPage = typeRepository.findAll((Specification<Type>) (root, query, criteriaBuilder) -> {
            Predicate p1 = criteriaBuilder.like(root.get("name").as(String.class), "%"+typeQuery.getsSearch()+"%");
            Predicate p2 = criteriaBuilder.like(root.get("cityName").as(String.class), "%"+typeQuery.getsSearch()+"%");
            Predicate p3 = criteriaBuilder.like(root.get("key").as(String.class), "%"+typeQuery.getsSearch()+"%");
            query.where(criteriaBuilder.or(p1,p2,p3));
            return query.getRestriction();
        },pageable);
        return bookPage;
    }

    @Override
    public Type save(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public void update(Type newType) {
        Type type = typeRepository.findById(newType.getId());
        type.setName(newType.getName());
        type.setKey(newType.getKey());
        type.setGroup(newType.getGroup());
        type.setCity(newType.getCity());
        type.setCityName(newType.getCityName());
        typeRepository.save(type);
    }

    /**
     * 删除大类时删除小类和小类对应的公式
     * @param id
     */
    @Override
    public void delete(Long id) {
        typeRepository.deleteById(id);
    }

    @Override
    public Type findById(Long id) {
        return typeRepository.findById(id);
    }

	@Override
	public List<Type> city(String city,String group) {
		List<Type> list = typeRepository.findAll((Specification<Type>) (root, query, criteriaBuilder) -> {
            Predicate p1 = criteriaBuilder.equal(root.get("city").as(String.class), city);
            Predicate p2 = criteriaBuilder.equal(root.get("group").as(String.class), group);
            query.where(criteriaBuilder.and(p1,p2));
            return query.getRestriction();
        });
		return list;
	}

	@Override
	public List<Type> key(String key) {
		Type type = typeRepository.findByKey(key);
		if(type==null) {
			return new ArrayList<>();
		}
		return this.city(type.getCity(), type.getGroup());
	}
}
