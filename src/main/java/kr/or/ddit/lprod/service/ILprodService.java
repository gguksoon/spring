package kr.or.ddit.lprod.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.common.model.Page;
import kr.or.ddit.lprod.model.Lprod;

public interface ILprodService {

	/**
	* Method : getLprodList
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @return
	* Method 설명 : 전체 상품 분류 리스트 조회
	*/
	List<Lprod> getLprodList();
	
	/**
	* Method : getLprod
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @return
	* Method 설명 : 전체 상품 리스트 조회
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
	Map<String, Object> getLprodPagingList(Page page);
}
