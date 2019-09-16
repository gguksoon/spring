package kr.or.ddit.config.test;

import javax.annotation.Resource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/** dao, service에 겹치는 부분을 따로 빼서 상속받는 형태로 사용함 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:kr/or/ddit/config/spring/context-root.xml",
		"classpath:kr/or/ddit/config/spring/context-datasource-test.xml",
		"classpath:kr/or/ddit/config/spring/context-transaction.xml"})
public class RootTestConfig {

	@Resource(name="datasource")
	private BasicDataSource dataSource;
	
	@Before
	public void setup() {
		// init.sql에 있는 모든 sql문장을 테스트 메소드 실행전에 실행
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("/kr/or/ddit/db/init.sql"));
		populator.setContinueOnError(false); // init.sql을 실행하다 에러가 발생할 경우 중지(운영DB에서는 사용하면 안됨)
		DatabasePopulatorUtils.execute(populator, dataSource);
	}

	// junit에서 테스트클래스로 인식해서 실행할 test가 없다는 에러가 발생한다. 따라서 기능이 없는 test코드를 하나 생성해준다.
	@Ignore
	@Test
	public void dummy() { }
}
