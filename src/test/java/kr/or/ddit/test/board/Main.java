package kr.or.ddit.test.board;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import kr.or.ddit.test.board.dao.BoardDao;
import kr.or.ddit.test.board.dao.IBoardDao;
import kr.or.ddit.test.board.service.BoardService;
import kr.or.ddit.test.board.service.IBoardService;

public class Main {

	public static void main(String[] args) {
		// 기존 객체 생성 방법
		IBoardDao dao = new BoardDao();
		IBoardService boardService = new BoardService();
		boardService.setBoardDao(dao);
		
		// 1. spring ioc 컨테이너를 이용한 객체 생성(객체를 만드는 설명서를 spring한테 위임)
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:kr/or/ddit/spring/ioc/application-context-test.xml");
		
		// 2. 스프링 컨테이너로(==ioc 컨테이너)부터 원하는 객체를 요청: DL(Dependency Lookup)
		IBoardDao cDao = (IBoardDao)context.getBean("boardDao");
		cDao.getBoardList();
		
		// setter
		IBoardService cService = context.getBean("boardService", IBoardService.class); // getBean을 하며 동시에 형변환하기
		cService.getBoardList();
		
		// constructor
		IBoardService cServiceCo = context.getBean("boardServiceCo", IBoardService.class);
		cServiceCo.getBoardList();
	}
}
