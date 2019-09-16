package kr.or.ddit.lprod.repository;

import java.util.List;
import java.util.Map;

import kr.or.ddit.common.model.Page;
import kr.or.ddit.lprod.model.Lprod;

public interface ILprodDao {

	/**
	* Method : getLprodList
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @return
	* Method 설명 : 
	*/
	List<Lprod> getLprodList();

	/**
	* Method : getProdList
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param lprod_gu
	* @return
	* Method 설명 : 
	*/
	List<Map> getProdList(String lprod_gu);
	
	/**
	* Method : getLprodPagingList
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param page
	* @return
	* Method 설명 : 제품 분류 페이징 리스트 조회
	*/
	List<Lprod> getLprodPagingList(Page page);

	/**
	* Method : getLprodTotalCnt
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @return
	* Method 설명 : 제품 분류 전체 건수 조회
	*/
	int getLprodTotalCnt();
}
