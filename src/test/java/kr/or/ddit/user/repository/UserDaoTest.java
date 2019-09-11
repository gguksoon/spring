package kr.or.ddit.user.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.or.ddit.common.model.Page;
import kr.or.ddit.user.dao.IUserDao;
import kr.or.ddit.user.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:kr/or/ddit/config/spring/context-root.xml",
		"classpath:kr/or/ddit/config/spring/context-datasource.xml",
		"classpath:kr/or/ddit/config/spring/context-transaction.xml"})
public class UserDaoTest {

	private static final Logger logger = LoggerFactory.getLogger(UserDaoTest.class);

	// userDao를 테스트하기 위해 필요한 것
	// db연결, 트랜잭션, dao
	@Resource(name="userDao")
	private IUserDao userDao;
	private String userId = "brownTest";
	
	@Before
	public void setup() { // 주로 지어지는 이름
		userDao.deleteUser(userId);
	}
	
	/**
	* Method : getUserListTest
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* Method 설명 : 
	*/
	@Test
	public void getUserListTest() {
		/***Given***/
		
		/***When***/
		List<User> userList = userDao.getUserList();
		logger.debug("userList: {}", userList);

		/***Then***/
		assertTrue(userList.size() > 104);
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
		List<User> userList = userDao.getUserListOnlyHalf();

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
		User userVo = userDao.getUser(userId);
		
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
		List<User> userList = userDao.getUserPagingList(page);

		/***Then***/
		assertEquals(10, userList.size());
		assertEquals("xuserid22", userList.get(0).getUserId());
	}

	/**
	* Method : getUserTotalCntTest
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* Method 설명 : 사용자 전체 건수 조회
	*/
	@Test
	public void getUserTotalCntTest() {
		/***Given***/
		

		/***When***/
		int totalCnt = userDao.getUserTotalCnt();
		
		/***Then***/
		assertTrue(totalCnt > 104);
	}
	
	/**
	* Method : insertUserTest
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* Method 설명 : 사용자 등록 테스트 
	 * @throws ParseException 
	*/
	@Test
	public void insertUserTest() throws ParseException {
		/***Given***/
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2019-08-08");
		User user = new User(userId, "brownTest1234", "브라운테스트", "곰테스트", date,
							 "대전광역시 중구 중앙로 76", "영민빌딩 2층 DDIT", "34940", "", "", "");
		
		/***When***/
		int insertCnt = userDao.insertUser(user);
//		sqlSession.commit();

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
		User user = new User(userId, "test_pass", "test_nm", "test_alias", date);
		
		/***When***/
		int updateCnt = userDao.updateUser(user);
//		sqlSession.commit();

		/***Then***/
		assertEquals(1, updateCnt);
	}
}
