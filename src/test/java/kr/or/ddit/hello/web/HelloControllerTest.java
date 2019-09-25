package kr.or.ddit.hello.web;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.config.test.WebTestConfig;

public class HelloControllerTest extends WebTestConfig {

	private static final Logger logger = LoggerFactory.getLogger(HelloControllerTest.class);
	
	// server(tomcat)가 없는 환경에서 테스트 가능하다.
	@Test
	public void helloTest() throws Exception {
		/***Given***/
		
		/***When***/
		// .param을 붙이면 해당 request에 parameter를 추가해줌(지워도 됨)
		MvcResult mvcResult = mockMvc.perform(get("/hello/hello.do").param("userId", "brown")).andReturn();
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

	@Test
	public void helloTest2() throws Exception {
		mockMvc.perform(get("/hello/hello.do").param("userId", "sally"))
			.andExpect(status().isOk())
			.andExpect(view().name("hello/hello"))
			.andExpect(model().attributeExists("msg"))
			.andExpect(model().attributeExists("userId"))
			.andExpect(model().attribute("msg", "Hello, World!"))
			.andExpect(model().attribute("userId", "sally_helloControl"));
		
	}
}
