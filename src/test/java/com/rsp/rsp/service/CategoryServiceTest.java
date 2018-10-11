package com.rsp.rsp.service;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.data.domain.Page;

import com.rsp.rsp.domain.Category;
import com.rsp.rsp.domain.query.CategoryQuery;

/**
 * 测试大类service
 */
public class CategoryServiceTest extends BaseTest {


    @Test
    public void testFindAll(){
        List<Category> list1 = categoryService.findAll();
        Page<Category> page1 = categoryService.findCategoryNoCriteria(0,10);
        CategoryQuery categoryQuery = new CategoryQuery();
        categoryQuery.setName("name");
        Page<Category> page2 = categoryService.findCategoryCriteria(0,10,categoryQuery);
        System.out.println(list1.size() + " " + page1.getContent().size() + " " + page2.getContent().size());
    }

    @Test
    public void testSave() {
        Category category = new Category();
        category.setName("1");
        categoryService.save(category);
    }

    @Test
    public void testUpdate(){
        Category category = new Category();
        category.setId(1L);
        category.setName("name");
        categoryService.update(category);
    }

    @Test
    public void testDelete(){
        Map<String,Long> idMap = addTest();
        categoryService.delete(idMap.get("categoryId"));
    }

    @Test
    public void testFindMap(){
        Map<Long,String> result = categoryService.findIdAndNameMap();
        System.out.println(result.size());
    }
}
