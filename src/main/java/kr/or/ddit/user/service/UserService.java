package kr.or.ddit.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.or.ddit.common.model.Page;
import kr.or.ddit.user.model.User;
import kr.or.ddit.user.repository.IUserDao;
import kr.or.ddit.user.repository.UserDao;

@Service
public class UserService implements IUserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	// UserDao클래스의 이름 첫글자를 소문자로 변경한 것이 자동으로 설정됨
	@Resource(name="userDao")
	private IUserDao userDao;
	
	public UserService(IUserDao userDao) {
		userDao = new UserDao();
	}
	
	/**
	* Method : getUserList
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @return
	* Method 설명 : 사용자 전체 리스트 조회
	*/
	@Override
	public List<User> getUserList() {
		return userDao.getUserList();
	}

	/**
	* Method : getUserListOnlyHalf
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @return
	* Method 설명 : 사용자 50명 리스트 조회
	*/
	@Override
	public List<User> getUserListOnlyHalf() {
		return userDao.getUserListOnlyHalf();
	}

	/**
	* Method : getUser
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param userId
	* @return
	* Method 설명 : 사용자 상세 조회
	*/
	@Override
	public User getUser(String userId) {
		return userDao.getUser(userId);
	}

	/**
	* Method : getUserPagingList
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param page
	* @return
	* Method 설명 : 사용자 페이징 리스트 조회
	*/
	@Override
	public Map<String, Object> getUserPagingList(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<User> userList = userDao.getUserPagingList(page);
		int totalCnt = userDao.getUserTotalCnt();
		
		map.put("userList", userList);
		map.put("paginationSize", (int)Math.ceil((double)totalCnt / page.getPagesize()));
		
		return map;
	}

	/**
	* Method : insertUser
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param user
	* @return
	* Method 설명 : 사용자 등록
	*/
	@Override
	public int insertUser(User user) {
		return userDao.insertUser(user);
	}

	/**
	* Method : updateUser
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param user
	* @return
	* Method 설명 : 사용자 수정
	*/
	@Override
	public int updateUser(User user) {
		return userDao.updateUser(user);
	}

	/**
	* Method : deleteUser
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param userId
	* @return
	* Method 설명 : 사용자 삭제
	*/
	@Override
	public int deleteUser(String userId) {
		return userDao.deleteUser(userId);
	}

}
