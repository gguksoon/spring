package kr.or.ddit.mvc.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.exception.NotFileException;
import kr.or.ddit.mvc.model.Main;
import kr.or.ddit.user.model.User;
import kr.or.ddit.user.model.UserValidator;
import kr.or.ddit.util.FileUtil;
import kr.or.ddit.util.model.FileInfo;

@SessionAttributes
@RequestMapping("mvc")
@Controller
public class SpringMvcController {

	private static final Logger logger = LoggerFactory.getLogger(SpringMvcController.class);
	
	// @RequestMapping이 붙은 메소드가 실행되기 전에 @ModelAttribute메소드가 먼저 실행되고
	// 해당 메소드가 리턴하는 값을 Model객체에 자동으로 넣어준다.
	@ModelAttribute("rangers")
	public List<String> rangers() {
		logger.debug("rangers()");
		List<String> rangers = new ArrayList<String>();
		rangers.add("brown");
		rangers.add("sally");
		rangers.add("cony");
		
		return rangers;
	}
	
	/**
	* Method : view
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @return
	* Method 설명 : mvc 어노테이션 테스트 view
	*/
	@RequestMapping("view")
	public String view(Model model, 
						@ModelAttribute("rangers") List<String> rangers2,
						@SessionAttribute(name="S_USERVO", required = false) User user) {
		List<String> rangers = (List<String>) model.asMap().get("rangers");
		logger.debug("rangers: {}", rangers); // model에 주입된 것
		logger.debug("rangers2: {}", rangers2); // ModelAttribute
		logger.debug("S_USERVO: {}", user); // SessionAttribute
		return "mvc/view";
	}
	
	/**
	* Method : requestParam
	* 작성자 : JO MIN SOO
	* 변경이력 :
	* @param user
	* @param page
	* @return
	* Method 설명 : @RequestParam 어노테이션 테스트
	*/
	@RequestMapping("requestParam")
	public String requestParam( @RequestParam(name = "userId") String user,
								@RequestParam(defaultValue = "10") int page) {
		logger.debug("userId: {}", user);
		logger.debug("page: {}", page);
		
		return "mvc/view";
	}
	
	@RequestMapping("/path/{subpath}")
	public String requestPath( @PathVariable(name = "subpath") String subpath) {
		logger.debug("subPath: {}", subpath);
		
		return "mvc/view";
	}
	
	@RequestMapping("upload")
	public String upload(String userId, @RequestPart("picture") MultipartFile partFile) {
		logger.debug("userId: {}", userId);
		logger.debug("partFile.getName(): {}", partFile.getName());
		logger.debug("partFile.getOriginalFilename(): {}", partFile.getOriginalFilename());
		
		// 업로드 되는 시점의 년/월 폴더를 생성해주고, 파일 경로와 파일 정보를 FileInfo객체에 담아 리턴
		FileInfo fileInfo = FileUtil.getFileInfo(partFile.getOriginalFilename());
		
		try { partFile.transferTo(fileInfo.getFile()); }
		catch (IllegalStateException | IOException e) { e.printStackTrace(); }
		
		return "mvc/view";
	}
	
	@RequestMapping("multiParameter")
	public String multiString(String userId,
							  @RequestParam("userId") List<String> userIdList, 
							  Main main) {
		logger.debug("userId: {}", userId); // string
		logger.debug("userIdList: {}", userIdList); // list
		logger.debug("main: {}", main); // vo에 저장된 list
		
		return "mvc/view";
	}
	
	@RequestMapping("redirect")
	public String redirect(String userId, Model model, HttpSession session, RedirectAttributes reAttr) {
//		model.addAttribute("userId", userId);
//		session.setAttribute("userId", userId);
		
		// redirect시 최초 1회에 한해서 해당 속성값을 유지하고, 읽혀진 뒤 세션에서 해당 속성을 제거함.
		reAttr.addFlashAttribute("userId", userId);
		
		// 리다이렉트시 파라미터로 전달
		reAttr.addAttribute("alias", "bear");
		
		return "redirect:/login";
		// redierct: "redirect:url주소";
		// forward : "forward:url주소"; ==> 다른 컨트롤러로 forward (http method)에 대해 고려해야함
		// 원본 요청 get이면 forward 메소드 get (http method에 대해 고려해야함)
	}
	
	@RequestMapping("validator")
	public String validator(User user, BindingResult result) {
		// form객체(command, vo)의 검증 결과를 담는 BindingResult객체는
		// 반드시 메소드 인자 순서에서 form객체 바로 뒤에 위치해야 된다.
		
		// validator 실행
		new UserValidator().validate(user, result);
		if(result.hasErrors()) 
			logger.debug("has Error");
		else 
			logger.debug("no Error");
		
		logger.debug("user: {}", user);
		
		return "mvc/view";
	}
	
	@RequestMapping("jsr303")
	public String jsr303(@Valid User user, BindingResult result) {
		if(result.hasErrors()) 
			logger.debug("has Error");
		else 
			logger.debug("no Error");
		
		return "mvc/view";
	}
	
	@RequestMapping("throwException")
	public String throwException() {
		int a = 10 / 0;
		
		return "mvc/view";
	}
	
	@RequestMapping("responseStatus")
	public String responseStatus() throws NotFileException {
		// 인위적으로 존재하지 않는 파일에 접근하여 IOException이 발생하도록 작성
		// IOException을 catch하여 우리가 작성한 NoFileException으로 새롭게 예외 throw
		// NoFileException에 설정한 @ResponseStatus에 의해 500상태코드가 아닌 404상태코드로 응답 생성
		Resource resource = 
				new ClassPathResource("kr/or/ddit/config/mybatis/mybatis-config-nonono.xml");

		try {
			resource.getInputStream();
		} catch (IOException e) {
			throw new NotFileException();
		}
		return "mvc/view";
	}
	
	@RequestMapping("oback")
	public String oback() {
		Integer itg = null;
		int num = itg.intValue();
		
		return "mvc/view";
	}
}
