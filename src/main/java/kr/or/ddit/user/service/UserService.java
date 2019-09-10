package kr.or.ddit.user.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.user.dao.IUserDao;
import kr.or.ddit.user.dao.UserDao;
import kr.or.ddit.user.model.User;

@Service
public class UserService implements IUserService {

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

}
