package kr.or.ddit.user.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import kr.or.ddit.common.model.Page;
import kr.or.ddit.config.test.RootTestConfig;
import kr.or.ddit.user.model.User;

public class UserServiceTest extends RootTestConfig {

	private String userId = "brownTest";

	@Resource(name="userService")
	private IUserService userService;
	
	/**
	* Method : getUserListTest
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* Method 설명 : getUserList 테스트
	*/
	@Test
	public void getUserListTest() {
		/***Given***/
		
		/***When***/
		List<User> userList = userService.getUserList();
		
		/***Then***/
		assertTrue(userList.size() >= 105);
	}
	
	/**
	* Method : getUserListOnlyHalfTest
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* Method 설명 : getUserListOnlyHalf 테스트
	*/
	@Test
	public void getUserListOnlyHalfTest() {
		/***Given***/

		/***When***/
		List<User> userList = userService.getUserListOnlyHalf();

		/***Then***/
		assertEquals(50, userList.size());
	}
	
	/**
	* Method : getUserTest
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* Method 설명 : 사용자 정보 조회 테스트
	*/
	@Test
	public void getUserTest() {
		/***Given***/
		String userId = "brown";

		/***When***/
		User userVo = userService.getUser(userId);
		
		/***Then***/
		assertEquals("브라운", userVo.getUserNm());
		assertTrue(userVo.checkLoginValidate("brown", "brown1234"));
	}

	/**
	* Method : getUserPagingListTest
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* Method 설명 : 사용자 페이징 리스트 조회 
	*/
	@Test
	public void getUserPagingListTest() {
		/***Given***/
		Page page = new Page();
		page.setPage(3);
		page.setPagesize(10);
		
		/***When***/
		Map<String, Object> resultMap = userService.getUserPagingList(page);
		List<User> userList = (List<User>) resultMap.get("userList");
		int paginationSize = (Integer) resultMap.get("paginationSize");

		/***Then***/
		assertEquals(10, userList.size());
		assertEquals("xuserid22", userList.get(0).getUserId());
		assertEquals(11, paginationSize);
	}
	
	/**
	* Method : ceilingTest
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* Method 설명 : ceiling 테스트
	*/
	@Test
	public void ceilingTest() {
		/***Given***/
		int totalCnt = 105;
		int pagesize = 10;

		/***When***/
		double paginationSize = Math.ceil((double)totalCnt / pagesize);

		/***Then***/
		assertEquals(11, (int)paginationSize);
	}
	
	/**
	* Method : insertUserTest
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @throws ParseException
	* Method 설명 : 사용자 등록 테스트
	*/
	@Test
	public void insertUserTest() throws ParseException {
		/***Given***/
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2019-08-08");
		User user = new User(userId, "brownTest1234", "브라운테스트", "곰테스트", date,
							 "대전광역시 중구 중앙로 76", "영민빌딩 2층 DDIT", "34940", "", "", "");
		
		/***When***/
		int insertCnt = userService.insertUser(user);

		/***Then***/
		assertEquals(1, insertCnt);	
	}
	
	/**
	* Method : updateUserTest
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @throws ParseException
	* Method 설명 : 사용자 수정 테스트 
	*/
	@Test
	public void updateUserTest() throws ParseException {
		/***Given***/
		insertUserTest();
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2019-08-08");
		User user = new User(userId, "수정테스트", "수정테스트", "수정테스트", date);

		/***When***/
		int updateCnt = userService.updateUser(user);

		/***Then***/
		assertEquals(1, updateCnt);
	}

}
