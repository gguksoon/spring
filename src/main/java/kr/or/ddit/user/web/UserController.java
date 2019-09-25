package kr.or.ddit.user.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.common.model.Page;
import kr.or.ddit.user.model.User;
import kr.or.ddit.user.model.UserValidator;
import kr.or.ddit.user.service.IUserService;
import kr.or.ddit.util.FileUtil;
import kr.or.ddit.util.model.FileInfo;

@RequestMapping("user/")
@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
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
		
		model.addAllAttributes(resultMap); // 위 두가지 내용을 한번에 넣기
		
		return "user/userPagingList";
	}
	
	/**
	* Method : user
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param model
	* @param userId
	* @return
	* Method 설명 : 유저 정보 출력
	*/
	@RequestMapping("user")
	public String user(Model model, String userId) {
		model.addAttribute("user", userService.getUser(userId));
		
		return "user/user";
	}
	
	/**
	* Method : userPicture
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param userId
	* @param response
	* @throws IOException
	* Method 설명 : 유저 사진 출
	*/
	@RequestMapping("userPicture")
	public void userPicture(String userId, HttpServletResponse response) throws IOException {
		User user = userService.getUser(userId);
		
		ServletOutputStream sos = response.getOutputStream();
		
		File picture = new File(user.getRealfilename());
		FileInputStream fis = new FileInputStream(picture);
		
		byte[] buff = new byte[512];
		int len = 0;
		
		while( (len = fis.read(buff, 0, 512)) != -1 ) {
			sos.write(buff, 0, len);
		}
		
		fis.close();
	}

	/**
	* Method : userFormView
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @return
	* Method 설명 : 사용자 등록 화면 요청
	*/
	@GetMapping("userForm")
	public String userFormView() {
		
		return "user/userForm";
	}
	
	/**
	* Method : userForm
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param user
	* @param result
	* @param picture
	* @return
	* Method 설명 : 사용자 등록 요청
	*/
	@PostMapping("userForm")
	public String userForm(User user, BindingResult result,
						   @RequestPart("picture") MultipartFile picture) {
		
		new UserValidator().validate(user, result);
		
		if(result.hasErrors()) { // validate 실패
			return "user/userForm";
		} else { // validate 통과
			FileInfo fileInfo = FileUtil.getFileInfo(picture.getOriginalFilename());
			
			// 첨부된 파일이 있을 경우
			if(picture.getSize() > 0) {
				try {
					picture.transferTo(fileInfo.getFile());
					user.setFilename(fileInfo.getOriginalFileName()); // originalFileName
					user.setRealfilename(fileInfo.getFile().getPath());
					
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			
			int insertCnt = userService.insertUser(user);
			
			if(insertCnt == 1) { // redirect
				return "redirect:/user/user?userId=" + user.getUserId();
			
			} else { // forward 
				return "user/userForm";
			}
		}
	}

	@GetMapping("userUpdate")
	public String updateUserView(Model model, String userId) {
		model.addAttribute("user", userService.getUser(userId));
		
		return "user/userUpdate";
	}
	
	@PostMapping("userUpdate")
	public String updateUser(User user, @RequestPart("picture") MultipartFile picture) {
		User dbUser = userService.getUser(user.getUserId());
		FileInfo fileInfo = FileUtil.getFileInfo(picture.getOriginalFilename());
		logger.debug("{}", user);
		
		if(picture.getSize() > 0) {	// 첨부된 파일이 있을 경우
			try {
				picture.transferTo(fileInfo.getFile());
				user.setFilename(fileInfo.getOriginalFileName()); // originalFileName
				user.setRealfilename(fileInfo.getFile().getPath());
				
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		} else { // 첨부된 파일이 없는 경우
			user.setFilename(dbUser.getFilename());
			user.setRealfilename(dbUser.getRealfilename());
			user.setRealfilename2(dbUser.getRealfilename2());
		}
		
		int updateCnt = userService.updateUser(user);
		
		if(updateCnt == 1) { // redirect
			return "redirect:/user/user?userId=" + user.getUserId();
		
		} else { // forward 
			return "user/userForm";
		}
	}
	
	// Ajax 사용자 페이징 리스트 조회
	@GetMapping("userPagingListAjax")
	public String userPagingListAjax(Page page, Model model) {
		model.addAttribute("pageVo", page);
		
		Map<String, Object> resultMap = userService.getUserPagingList(page);
		
		model.addAllAttributes(resultMap); // 위 두가지 내용을 한번에 넣기
		
		return "jsonView";
	}
	
	// Ajax 사용자 페이징 리스트 조회
	@PostMapping("userPagingListAjaxRequestBody")
	@ResponseBody // 응답을 resultMap으로 해주기 위해 어노테이션 적용
	public Map<String, Object> userPagingListAjaxRequestBody(@RequestBody Page page, Model model) {
		Map<String, Object> resultMap = userService.getUserPagingList(page);
		
//		model.addAllAttributes(resultMap); // 위 두가지 내용을 한번에 넣기
//		model.addAttribute("pageVo", page);
		
		resultMap.put("pageVo", page);
		
		// model객체에 넣지 않고 collection객체를 직접 return
		return resultMap;
	}
	
	@RequestMapping("userPagingListAjaxView")
	public String userPagingListStringAjaxView() {
		
		return "user/userPagingListAjaxView";
	}
	
	/**
	* Method : userPagingListHtmlAjax
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @return
	* Method 설명 : 사용자 페이징 리스트의 결과를 html로 생성한다.
	*/
	@RequestMapping("userPagingListHtmlAjax")
	public String userPagingListHtmlAjax(@RequestParam(defaultValue = "1") int page,
										 @RequestParam(defaultValue = "10") int pagesize, 
										 Model model) {
		
		Page pageVo = new Page(page, pagesize);
		model.addAllAttributes(userService.getUserPagingList(pageVo));
		model.addAttribute("pageVo", pageVo);
		
		return "user/userPagingListHtmlAjax";
	}
}
