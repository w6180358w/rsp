package com.rsp.rsp.service.impl;

import com.rsp.rsp.dao.SubCategoryRepository;
import com.rsp.rsp.domain.SubCategory;
import com.rsp.rsp.domain.query.SubCategoryQuery;
import com.rsp.rsp.service.SubCategoryService;
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
 * 机构
 * @author sjb
 */
@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    /**
     * 查询全部
     * @return
     */
    @Override
    public List<SubCategory> findAll() {
        return subCategoryRepository.findAll();
    }

    /**
     * 只有分页没有查询条件
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<SubCategory> findSubCategoryNoCriteria(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        return subCategoryRepository.findAll(pageable);
    }

    /**
     * 有查询条件的分页
     * @param page
     * @param size
     * @param subCategoryQuery
     * @return
     */
    @Override
    public Page<SubCategory> findSubCategoryCriteria(Integer page, Integer size, SubCategoryQuery subCategoryQuery) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        Page<SubCategory> bookPage = subCategoryRepository.findAll((Specification<SubCategory>) (root, query, criteriaBuilder) -> {
            Predicate p1 = criteriaBuilder.equal(root.get("name").as(String.class), subCategoryQuery.getName());
//            Predicate p2 = criteriaBuilder.equal(root.get("isbn").as(String.class), subCategoryQuery.getIsbn());
//            Predicate p3 = criteriaBuilder.equal(root.get("author").as(String.class), subCategoryQuery.getAuthor());
//            query.where(criteriaBuilder.and(p1,p2,p3));
            query.where(criteriaBuilder.and(p1));
            return query.getRestriction();
        },pageable);
        return bookPage;
    }

    @Override
    public void save(SubCategory subCategory) {
        subCategoryRepository.save(subCategory);
    }

    @Override
    public void update(SubCategory newSubCategory) {
        SubCategory subCategory = subCategoryRepository.findById(newSubCategory.getId());
        subCategory.setCategoryId(newSubCategory.getCategoryId());
        subCategory.setKey(newSubCategory.getKey());
        subCategory.setName(newSubCategory.getName());
        subCategoryRepository.save(subCategory);
    }

    @Override
    public void delete(Long id) {
        subCategoryRepository.deleteById(id);
    }
}
