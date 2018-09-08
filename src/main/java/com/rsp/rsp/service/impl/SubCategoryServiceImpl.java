package com.rsp.rsp.service.impl;

import com.rsp.rsp.dao.FormulaRepository;
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
    @Autowired
    private FormulaRepository formulaRepository;

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
     * @param start
     * @param size
     * @return
     */
    @Override
    public Page<SubCategory> findSubCategoryNoCriteria(Integer start, Integer size) {
        Pageable pageable = PageRequest.of(start/size, size, Sort.Direction.ASC, "id");
        return subCategoryRepository.findAll(pageable);
    }

    /**
     * 有查询条件的分页
     * @param start
     * @param size
     * @param subCategoryQuery
     * @return
     */
    @Override
    public Page<SubCategory> findSubCategoryCriteria(Integer start, Integer size, SubCategoryQuery subCategoryQuery) {
        Pageable pageable = PageRequest.of(start/size, size, Sort.Direction.DESC, "id");
        Page<SubCategory> bookPage = subCategoryRepository.findAll((Specification<SubCategory>) (root, query, criteriaBuilder) -> {
            Predicate p1 = criteriaBuilder.like(root.get("name").as(String.class), "%"+subCategoryQuery.getsSearch()+"%");
            Predicate p2 = criteriaBuilder.equal(root.get("paramKey").as(String.class), subCategoryQuery.getsSearch());
            Predicate p3 = criteriaBuilder.like(root.get("categoryName").as(String.class), "%"+subCategoryQuery.getsSearch()+"%");
            query.where(criteriaBuilder.or(p1,p2,p3));
            return query.getRestriction();
        },pageable);
        return bookPage;
    }

    @Override
    public SubCategory save(SubCategory subCategory) {
        return subCategoryRepository.save(subCategory);
    }

    @Override
    public void update(SubCategory newSubCategory) {
        SubCategory subCategory = subCategoryRepository.findById(newSubCategory.getId());
        subCategory.setCategoryId(newSubCategory.getCategoryId());
        subCategory.setParamKey(newSubCategory.getParamKey());
        subCategory.setName(newSubCategory.getName());
        subCategory.setDefaultValue(newSubCategory.getDefaultValue());
        subCategory.setCategoryName(newSubCategory.getCategoryName());
        subCategoryRepository.save(subCategory);
    }

    /**
     * 删除小类时同时删除对应的公式
     * @param id
     */
    @Override
    public void delete(Long id) {
        //查询小类
        SubCategory subCategory = subCategoryRepository.findById(id);
        //根据key删除公式
        formulaRepository.deleteBySubCategoryId(subCategory.getId());
        //删除小类
        subCategoryRepository.deleteById(id);
    }

    @Override
    public SubCategory findById(Long id) {
        return subCategoryRepository.findById(id);
    }
}
