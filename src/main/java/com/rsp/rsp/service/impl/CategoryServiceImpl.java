package com.rsp.rsp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.rsp.rsp.dao.CategoryRepository;
import com.rsp.rsp.domain.Category;
import com.rsp.rsp.domain.query.CategoryQuery;
import com.rsp.rsp.service.CategoryService;

/**
 * 大类
 * @author sjb
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> findCategoryNoCriteria(Integer start, Integer size) {
        Pageable pageable = PageRequest.of(start/size, size, Sort.Direction.ASC, "id");
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Page<Category> findCategoryCriteria(Integer start, Integer size, CategoryQuery categoryQuery) {
        Pageable pageable = PageRequest.of(start/size, size, Sort.Direction.ASC, "id");
        Page<Category> bookPage = categoryRepository.findAll((Specification<Category>) (root, query, criteriaBuilder) -> {
            Predicate p1 = criteriaBuilder.like(root.get("name").as(String.class), "%"+categoryQuery.getsSearch()+"%");
//            Predicate p2 = criteriaBuilder.equal(root.get("id").as(String.class), categoryQuery.getsSearch());
            query.where(criteriaBuilder.or(p1));
            return query.getRestriction();
        },pageable);
        return bookPage;
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void update(Category newCategory) {
        Category category = categoryRepository.findById(newCategory.getId());
        category.setName(newCategory.getName());
        category.setType(newCategory.getType());
        categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Map<Long, String> findIdAndNameMap() {
        Map<Long,String> result = new HashMap<>(16);
        categoryRepository.findAll().forEach(x->result.put(x.getId(),x.getName()));
        return result;
    }
}
