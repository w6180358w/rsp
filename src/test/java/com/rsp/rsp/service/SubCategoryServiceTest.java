package com.rsp.rsp.service;

import org.junit.Test;

import java.util.Map;

public class SubCategoryServiceTest extends BaseTest {

    @Test
    public void testDelete(){
        Map<String,Long> idMap = addTest();
        subCategoryService.delete(idMap.get("subCategoryId"));
    }
}
