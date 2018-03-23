package com.mashuq.athenaeum.test.jooq;

import static org.junit.Assert.assertEquals;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ImportAutoConfiguration(JooqAutoConfiguration.class)
@SpringBootTest(properties = "scheduling.enabled=false")
@ContextConfiguration
public class ConfigurationTest {

	@Autowired
	private DSLContext dSLContext;

	@Test
	public void dialectTest() {
		assertEquals(dSLContext.configuration().dialect(),
				SQLDialect.MYSQL_5_7);
	}
}