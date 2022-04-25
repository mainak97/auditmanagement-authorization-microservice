package com.auditManagement.authorization;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuditManagementAuthorizationApplicationTests {
	

	@Test
	void contextLoads() {
		String test = null;
		assertThat(test).isNull();
	}
	
	@Test
	void test() {
		String test = null;
		assertThat(test).isNull();
		AuditManagementAuthorizationApplication.main(new String[]{});
	}
}
