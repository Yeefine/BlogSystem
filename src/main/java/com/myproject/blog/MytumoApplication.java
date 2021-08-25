package com.myproject.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.myproject.blog.biz.mapper")
public class MytumoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MytumoApplication.class, args);
	}

}
