package com.rsp.rsp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.rsp.rsp.dao.CategoryRepository;
import com.rsp.rsp.dao.FormulaRepository;
import com.rsp.rsp.dao.SubCategoryRepository;
import com.rsp.rsp.domain.Category;
import com.rsp.rsp.domain.SubCategory;
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
    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Autowired
    private FormulaRepository formulaRepository;

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
        Pageable pageable = PageRequest.of(start/size, size, Sort.Direction.DESC, "id");
        Page<Category> bookPage = categoryRepository.findAll((Specification<Category>) (root, query, criteriaBuilder) -> {
            Predicate p1 = criteriaBuilder.like(root.get("name").as(String.class), "%"+categoryQuery.getsSearch()+"%");
//            Predicate p2 = criteriaBuilder.equal(root.get("id").as(String.class), categoryQuery.getsSearch());
            query.where(criteriaBuilder.or(p1));
            return query.getRestriction();
        },pageable);
        return bookPage;
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void update(Category newCategory) {
        Category category = categoryRepository.findById(newCategory.getId());
        category.setName(newCategory.getName());
        category.setType(newCategory.getType());
        category.setTypeName(newCategory.getTypeName());
        categoryRepository.save(category);
    }

    /**
     * 删除大类时删除小类和小类对应的公式
     * @param id
     */
    @Override
    public void delete(Long id) {
        //根据大类查询小类
        List<SubCategory> subCategories = subCategoryRepository.findByCategoryId(id);
        //删除小类对应的公式
        formulaRepository.deleteBySubCategoryIdIn(subCategories.stream().map(SubCategory::getId).collect(Collectors.toList()));
        //删除小类
        subCategoryRepository.deleteByIdIn(subCategories.stream().map(SubCategory::getId).collect(Collectors.toList()));
        //删除大类
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

	@Override
	public List<Category> type(String type) {
		List<Category> list = categoryRepository.findAll((Specification<Category>) (root, query, criteriaBuilder) -> {
            Predicate p1 = criteriaBuilder.equal(root.get("type").as(String.class), type);
            query.where(criteriaBuilder.and(p1));
            return query.getRestriction();
        });
		return list;
	}

	@Override
	public List<Category> inId(Set<Long> idList) {
		List<Category> result = categoryRepository.findAll((Specification<Category>) (root, query, criteriaBuilder) -> {
			//根据类型ID筛选
	    	In<Object> in = criteriaBuilder.in(root.get("id"));
			List<Predicate> list = new ArrayList<>();
			in.value(-1l);//防止参数为空
			for (Long id : idList) {
				in.value(id);
			}
			list.add(in);
			Predicate[] p = new Predicate[list.size()];
            query.where(criteriaBuilder.and(list.toArray(p)));
            return query.getRestriction();
        });
		return result;
	}
}
