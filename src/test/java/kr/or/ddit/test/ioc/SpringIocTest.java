package kr.or.ddit.test.ioc;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:kr/or/ddit/spring/ioc/application-context-ioc-test.xml")
public class SpringIocTest {

	@Resource(name="dbProperty")
	private DbProperty dbProperty;
	
	@Resource(name="dbPropertySetter")
	private DbProperty dbPropertySetter;
	
	/**
	* Method : propertyPlaceHolderTest
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* Method 설명 : <context:property-placeholder> 테스트 
	*/
	@Test
	public void propertyPlaceHolderTest() {
		/***Given***/
		
		/***When***/

		/***Then***/
		assertNotNull(dbProperty);
		assertEquals("JMS", dbProperty.getUser());
		assertEquals("java", dbProperty.getPass());
		assertEquals("jdbc:oracle:thin:@localhost:1521:XE", dbProperty.getUrl());
		assertEquals("oracle.jdbc.driver.OracleDriver", dbProperty.getDriver());
		
		assertNotNull(dbPropertySetter);
		assertEquals("JMS", dbPropertySetter.getUser());
		assertEquals("java", dbPropertySetter.getPass());
		assertEquals("jdbc:oracle:thin:@localhost:1521:XE", dbPropertySetter.getUrl());
		assertEquals("oracle.jdbc.driver.OracleDriver", dbPropertySetter.getDriver());
	}
}
