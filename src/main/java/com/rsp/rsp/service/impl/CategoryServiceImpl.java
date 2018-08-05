package com.rsp.rsp.service.impl;

import com.rsp.rsp.dao.CategoryRepository;
import com.rsp.rsp.domain.query.CategoryQuery;
import com.rsp.rsp.domain.Category;
import com.rsp.rsp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.List;

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
    public Page<Category> findCategoryNoCriteria(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Page<Category> findCategoryCriteria(Integer page, Integer size, CategoryQuery categoryQuery) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        Page<Category> bookPage = categoryRepository.findAll((Specification<Category>) (root, query, criteriaBuilder) -> {
            Predicate p1 = criteriaBuilder.equal(root.get("name").as(String.class), categoryQuery.getName());
//            Predicate p2 = criteriaBuilder.equal(root.get("isbn").as(String.class), orgQuery.getIsbn());
//            Predicate p3 = criteriaBuilder.equal(root.get("author").as(String.class), orgQuery.getAuthor());
//            query.where(criteriaBuilder.and(p1,p2,p3));
            query.where(criteriaBuilder.and(p1));
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
        Category org = categoryRepository.findById(newCategory.getId());
        //org set value
        categoryRepository.save(org);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
