package kr.or.ddit.user.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.common.model.Page;
import kr.or.ddit.user.model.User;
import kr.or.ddit.user.service.IUserService;

@RequestMapping("user/")
@Controller
public class UserController {

	@Resource(name = "userService")
	private IUserService userService;
	
	/**
	* Method : userView
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @return
	* Method 설명 : 사용자 상세화면 요청
	*/
	// 사용자가 localhost:8081/user/view.do를 요청할 때 실행
	@RequestMapping("view.do")
	public String userView() {
		return "user/view";
		
		// prefix + viewName + suffix
		// WEB-INF/views/user/view.jsp
	}
	
	/**
	* Method : userList
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param model
	* @return
	* Method 설명 : 사용자 전체 리스트 조회
	*/
	@RequestMapping(path = "userList", method = RequestMethod.GET) 
	public String userList(Model model) {
		model.addAttribute("userList", userService.getUserList());
		
		return "user/userList";
	}
	
	/**
	* Method : userListOnlyHalf
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param model
	* @return
	* Method 설명 : 사용자 리스트 50명 조회
	*/
	@RequestMapping(path = "userListOnlyHalf", method = RequestMethod.GET) 
	public String userListOnlyHalf(Model model) {
		model.addAttribute("userList", userService.getUserListOnlyHalf());
		
		return "user/userListOnlyHalf";
	}
	
	/**
	* Method : userPagingList
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param page
	* @param pagesize
	* @param model
	* @return
	* Method 설명 : 사용자 페이징 리스트 조회
	*/
	@RequestMapping(path = "userPagingList", method = RequestMethod.GET) 
	public String userPagingList(Page page, Model model) {
		model.addAttribute("pageVo", page);
		
		Map<String, Object> resultMap = userService.getUserPagingList(page);
		
//		model.addAttribute("userList", (List<User>) resultMap.get("userList"));
//		model.addAttribute("paginationSize", (Integer) resultMap.get("paginationSize"));
		model.addAllAttributes(resultMap); // 위 두가지 내용을 한번에 넣기
		
		return "user/userPagingList";
	}
	
}
