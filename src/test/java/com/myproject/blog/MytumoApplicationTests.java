package com.myproject.blog;

import com.myproject.blog.common.utils.Md5Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MytumoApplicationTests {

	@Autowired
	private Md5Util md5Util;

	@Test
	void MD5test() {
		String password = "123456";
		String s = md5Util.encryptPassword(password);
		System.out.println(s);
	}

	@Test
	void contextLoads() {
	}

}
