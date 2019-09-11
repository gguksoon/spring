package kr.or.ddit.user.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.common.model.Page;
import kr.or.ddit.user.model.User;

// class명에서 맨 첫글자를 소문자로 변경한 문자열이 스프링 빈 이름으로 기본 설정됨
// 다른 이름의 스프링 빈이름으로 등록을 하려면 속성 설정
// ==> ex) @Repository("userDaooooo") <- 일반적으로는 사용하지 않는편
@Repository
public class UserDao implements IUserDao {

	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	/**
	* Method : getUserList
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @return
	* Method 설명 : 전체 사용자 리스트 조회
	*/
	@Override
	public List<User> getUserList() {
		return sqlSession.selectList("user.getUserList");
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
		return sqlSession.selectList("user.getUserListOnlyHalf");
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
		return sqlSession.selectOne("user.getUser", userId);
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
	public List<User> getUserPagingList(Page page) {
		return sqlSession.selectList("user.getUserPagingList", page);
	}

	/**
	* Method : getUserTotalCnt
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @return
	* Method 설명 : 사용자 전체 건수 조회
	*/
	@Override
	public int getUserTotalCnt() {
		return sqlSession.selectOne("user.getUserTotalCnt");
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
		return sqlSession.insert("user.insertUser", user);
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
		return sqlSession.update("user.updateUser", user);
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
		return sqlSession.delete("user.deleteUser", userId);
	}

}
