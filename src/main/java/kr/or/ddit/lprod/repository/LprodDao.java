package kr.or.ddit.lprod.repository;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.common.model.Page;
import kr.or.ddit.lprod.model.Lprod;

@Repository
public class LprodDao implements ILprodDao {

	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	/**
	* Method : getLprodList
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @return
	* Method 설명 : 
	*/
	@Override
	public List<Lprod> getLprodList() {
		return sqlSession.selectList("lprod.getLprodList");
	}

	/**
	* Method : getProdList
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param lprod_gu
	* @return
	* Method 설명 : 
	*/
	@Override
	public List<Map> getProdList(String lprod_gu) {
		return sqlSession.selectList("lprod.getLprod", lprod_gu);
	}

	/**
	* Method : getLprodPagingList
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param page
	* @return
	* Method 설명 : 제품 분류 페이징 리스트 조회
	*/
	@Override
	public List<Lprod> getLprodPagingList(Page page) {
		return sqlSession.selectList("lprod.getLprodPagingList", page);
	}
	
	/**
	* Method : getLprodTotalCnt
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @return
	* Method 설명 : 제품 분류 전체 건수 조회
	*/
	@Override
	public int getLprodTotalCnt() {
		return sqlSession.selectOne("lprod.getLprodTotalCnt");
	}

}
