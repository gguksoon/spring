package kr.or.ddit.test.ioc;

import static org.junit.Assert.assertNotNull;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.or.ddit.user.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:kr/or/ddit/spring/ioc/application-context-type-test.xml")
public class TypeConvertTest {

	private static final Logger logger = LoggerFactory.getLogger(TypeConvertTest.class);
	
	@Resource(name="user")
	private User user;
	
	/**
	* Method : dataTypeInjectionTest
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* Method 설명 : 기본 컨버팅이 설정되지 않은 날짜 타입에 대한 주입 테스트
	*/
	@Test
	public void dataTypeInjectionTest() {
		/***Given***/
		
		/***When***/
		logger.debug("user: {}", user);

		/***Then***/
		assertNotNull(user);
	}

}
