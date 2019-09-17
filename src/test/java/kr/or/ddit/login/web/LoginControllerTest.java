package kr.or.ddit.login.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.config.test.WebTestConfig;
import kr.or.ddit.user.model.User;

public class LoginControllerTest extends WebTestConfig {

	private static final Logger logger = LoggerFactory.getLogger(LoginControllerTest.class);
	
	/**
	* Method : loginViewTest
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* Method 설명 : 로그인 화면 요청 테스트
	 * @throws Exception 
	*/
	@Test
	public void loginViewTest() throws Exception {
		/***Given***/
		
		/***When***/
		MvcResult mvcResult = mockMvc.perform(get("/login")).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();

		logger.debug("{}", mav.getViewName());
		/***Then***/
		assertEquals("login/login", mav.getViewName());
	}

	// 내가 한거
	@Test
	public void loginProcessTest() throws Exception {
		/***Given***/
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("userId", "brown");
		params.add("pass", "brown1234");
		
		/***When***/
		MvcResult mvcResult = mockMvc.perform(post("/login").params(params)).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();

		/***Then***/
		assertEquals("main", mav.getViewName());
	}
	
	// 슨생님이 한거
	@Test
	public void loginProcessTest2() throws Exception {
		MockHttpSession session = new MockHttpSession();
		
		mockMvc.perform(post("/login")
						.param("userId", "brown")
						.param("pass", "brown1234")
						.session(session))
				.andExpect(status().isOk())
				.andExpect(view().name("main"));
					
		User user = (User)session.getAttribute("S_USERVO");
		logger.debug("user: {}", user);
		
		assertNotNull(user);
	}
}
