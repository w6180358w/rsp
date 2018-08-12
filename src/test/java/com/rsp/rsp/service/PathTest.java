package com.rsp.rsp.service;

import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class PathTest {

    @Test
    public void classPath() throws FileNotFoundException {
        File path=new File(ResourceUtils.getURL("classpath:").getPath());
        File upload=new File(path.getAbsolutePath(),"static/images/uplaod/");
        if(!upload.exists()) {
            upload.mkdirs();
        }
    }
}
