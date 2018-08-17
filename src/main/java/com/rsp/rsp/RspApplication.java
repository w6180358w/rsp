package com.rsp.rsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
public class RspApplication extends SpringBootServletInitializer{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    	return application.sources(RspApplication.class);
	}
    
	public static void main(String[] args) {
		try {
			ConfigurableApplicationContext context = SpringApplication.run(RspApplication.class, args);
//			DataSource ds = context.getBean(DataSource.class);
			//默认的使用的是tomcat的数据源
//			System.out.println(ds.getClass().getName());
//			Connection connection = ds.getConnection();
			//test
//			System.out.println(connection.getCatalog());
//			System.out.println(context.getBean(JdbcTemplate.class));
//			connection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
