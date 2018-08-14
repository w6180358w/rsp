package com.rsp.rsp.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class OrgServiceTest extends BaseTest {
    @Autowired
    private OrgService orgService;

    @Test
    public void testDelete(){
        Map<String,Long> idMap = addTest();
        orgService.delete(idMap.get("orgId"));
    }
}
