package kr.or.ddit.user.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;

import kr.or.ddit.config.test.WebTestConfig;

public class UserControllerTest extends WebTestConfig {

	/**
	* Method : userListTest
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* Method 설명 : 사용자 전체 리스트 조회 
	 * @throws Exception 
	*/
	@Test
	public void userListTest() throws Exception {
		/***Given***/
		
		/***When***/
		mockMvc.perform(get("/user/userList"))
				.andExpect(model().attributeExists("userList"))
				.andExpect(view().name("user/userList"));

		/***Then***/
	}
	
	/**
	* Method : userListOnlyHalf
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @throws Exception
	* Method 설명 : 사용자 리스트 50명 조회
	*/
	@Test
	public void userListOnlyHalf() throws Exception {
		/***Given***/
		
		/***When***/
		mockMvc.perform(get("/user/userListOnlyHalf"))
				.andExpect(model().attributeExists("userList"))
				.andExpect(view().name("user/userListOnlyHalf"));

		/***Then***/
	}
	
	/**
	* Method : userPagingList
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @throws Exception
	* Method 설명 : 사용자 페이징 리스트 조회
	*/
	@Test
	public void userPagingList() throws Exception {
		/***Given***/
		
		/***When***/
		mockMvc.perform(get("/user/userPagingList"))
				.andExpect(model().attributeExists("userList"))
				.andExpect(view().name("user/userPagingList"));

		/***Then***/
	}

}
