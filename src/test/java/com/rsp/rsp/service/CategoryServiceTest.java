package com.rsp.rsp.service;

import com.rsp.rsp.domain.Category;
import com.rsp.rsp.domain.query.CategoryQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 测试大类service
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

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
        category.setType("2");
        categoryService.save(category);
    }

    @Test
    public void testUpdate(){
        Category category = new Category();
        category.setId(1);
        category.setType("type");
        category.setName("name");
        categoryService.update(category);
    }

    @Test
    public void testDelete(){
        categoryService.delete(2L);
    }
}
