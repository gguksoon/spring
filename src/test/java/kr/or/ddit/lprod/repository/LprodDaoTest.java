package kr.or.ddit.lprod.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import kr.or.ddit.common.model.Page;
import kr.or.ddit.config.test.RootTestConfig;
import kr.or.ddit.lprod.model.Lprod;

public class LprodDaoTest extends RootTestConfig {

	@Resource(name="lprodDao")
	private ILprodDao lprodDao;
	
	/**
	* Method : getLprodListTest
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* Method 설명 : getLprodList 테스트
	*/
	@Test
	public void getLprodListTest() {
		/***Given***/

		/***When***/
		List<Lprod> lprodList = lprodDao.getLprodList();
		
		/***Then***/
		assertEquals(9, lprodList.size());
	}
	
	/**
	* Method : getLprodTest
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* Method 설명 : getLprod 테스트
	*/
	@Test
	public void getLprodTest() {
		/***Given***/

		/***When***/
		List<Map> prodList = lprodDao.getProdList("P101");
		
		/***Then***/
		assertEquals(6, prodList.size());
	}
	
	/**
	* Method : getLprodPagingListTest
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* Method 설명 : getLprodPagingList 테스트
	*/
	@Test
	public void getLprodPagingListTest() {
		/***Given***/
		Page page = new Page();
		page.setPage(2);
		page.setPagesize(5);

		/***When***/
		List<Lprod> lprodList = lprodDao.getLprodPagingList(page);

		/***Then***/
		assertEquals(4, lprodList.size());
		assertEquals("P302", lprodList.get(0).getLprod_gu());
	}
	
	/**
	* Method : getLprodTotalCntTest
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* Method 설명 : getLprodTotalCnt 테스트
	*/
	@Test
	public void getLprodTotalCntTest() {
		/***Given***/

		/***When***/
		int totalCnt = lprodDao.getLprodTotalCnt();
		
		/***Then***/
		assertEquals(9, totalCnt);
	}

}
