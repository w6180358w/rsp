package com.rsp.rsp.service;

import com.rsp.rsp.domain.Category;
import com.rsp.rsp.domain.Formula;
import com.rsp.rsp.domain.Org;
import com.rsp.rsp.domain.SubCategory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTest {

    @Autowired
    protected CategoryService categoryService;
    @Autowired
    protected SubCategoryService subCategoryService;
    @Autowired
    protected FormulaService formulaService;
    @Autowired
    protected OrgService orgService;

    public Map<String,Long> addTest(){
        Map<String,Long> idMap = new HashMap<>();
        Category category = new Category();
        category.setName("testDelete");
        category.setType("testDeleteType");
        category = categoryService.save(category);
        idMap.put("categoryId",category.getId());
        SubCategory subCategory = new SubCategory();
        subCategory.setCategoryId(category.getId());
        subCategory.setParamKey("testKey2");
        subCategory.setName("testDelete");
        subCategory = subCategoryService.save(subCategory);
        idMap.put("subCategoryId",subCategory.getId());
        Org org = new Org();
        org.setLimitMax(100L);
        org.setLimitMin(1L);
        org.setName("testDelete");
        org.setTerm(10L);
        org = orgService.save(org);
        idMap.put("orgId",org.getId());
        Formula formula = new Formula();
        formula.setFormula("testFormula");
        formula.setOrgId(org.getId());
        formula.setSubCategoryKey(subCategory.getParamKey());
        formula = formulaService.save(formula);
        idMap.put("formulaId",formula.getId());
        return idMap;
    }
}
