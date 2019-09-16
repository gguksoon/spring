package kr.or.ddit.hello.web;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:kr/or/ddit/config/spring/servlet-context.xml") // controller scan : servlet-context.xml
@WebAppConfiguration // 스프링 컨테이너를 구성할 web기반 application context로 구성
public class HelloControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(HelloControllerTest.class);
	
	// controller를 테스트하기 위해 필요한 것 2가지
	// applicationContext: 스프링 컨테이너
	// MockMvc: dispatcherServlet(applicationContext객체를 통해 생성)
	
	// 주입하려고 하는 필드의 타입과 일치할 경우 이름과 관계없이 주입
	// 만약 주입하려고 하는 필드의 타입과 스프링 빈 중에 타입이 일치하는 빈이 2개 이상 존재할 경우 에러발생
	// * 구현체가 1개일 때 사용(하나의 인터페이스를 구현하는 클래스가 1개)
	@Autowired 
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	// server(tomcat)가 없는 환경에서 테스트 가능하다.
	@Test
	public void helloTest() throws Exception {
		/***Given***/
		
		/***When***/
		// .param을 붙이면 해당 request에 parameter를 추가해줌(지워도 됨)
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/hello/hello.do").param("userId", "brown")).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		
		// controller viewName(String)을 리턴하지만, 
		// 스프링 프레임워크 내부에서는 viewName을 ModelAndView객체로 변환해서 사용한다.
		// 또한, 컨트롤러 메소드에서는 viewName뿐만 아니라 ModelAndView 객체, View객체 리턴하는게 가능하다.
		// 리턴타입이 void인 경우도 존재한다.(response객체를 통해 개발자가 직접 응답을 생성하는 경우)
		
		logger.debug("mav.getViewName(): {}", mav.getViewName());
		logger.debug("mav.getModel().get(\"msg\"): {}", mav.getModel().get("msg"));
		logger.debug("mav.getModel().get(\"userId\"): {}", mav.getModel().get("userId"));
		
		/***Then***/
		assertEquals("hello/hello", mav.getViewName());
		assertEquals("Hello, World!", mav.getModel().get("msg"));
		assertEquals("brown_helloControl", mav.getModel().get("userId"));
		
	}

}
