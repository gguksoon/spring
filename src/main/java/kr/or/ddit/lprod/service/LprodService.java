package kr.or.ddit.lprod.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import kr.or.ddit.common.model.Page;
import kr.or.ddit.lprod.model.Lprod;
import kr.or.ddit.lprod.repository.ILprodDao;
import kr.or.ddit.lprod.repository.LprodDao;

@Service
public class LprodService implements ILprodService {

	@Resource(name="lprodDao")
	private ILprodDao lprodDao;
	
	public LprodService(ILprodDao lprodDao) {
		lprodDao = new LprodDao();
	}

	/**
	* Method : getLprodList
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @return
	* Method 설명 : 전체 상품 분류 리스트 조회
	*/
	@Override
	public List<Lprod> getLprodList() {
		return lprodDao.getLprodList();
	}

	/**
	* Method : getLprod
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @return
	* Method 설명 : 전체 상품 리스트 조회
	*/
	@Override
	public List<Map> getProdList(String lprod_gu) {
		return lprodDao.getProdList(lprod_gu);
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
	public Map<String, Object> getLprodPagingList(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Lprod> lprodList = lprodDao.getLprodPagingList(page);
		
		int totalCnt = lprodDao.getLprodTotalCnt();
		
		map.put("lprodList", lprodList);
		map.put("paginationSize", (int)Math.ceil((double)totalCnt / page.getPagesize()));
		
		return map;
	}
		
}
