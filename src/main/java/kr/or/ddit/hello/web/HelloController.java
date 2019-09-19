package kr.or.ddit.hello.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

	@RequestMapping("hello/hello.do")
	public String hello(Model model, HttpServletRequest request) {
		
		// 기존에 request에 넣었던 속성을 스프링에서는 Model객체에 넣는다.
		//   Request   ->    Model
		//   Session   ->   Session
		// Application -> Application
		model.addAttribute("nowDt", new Date());
		model.addAttribute("msg", "Hello, World!");
		model.addAttribute("userId", request.getParameter("userId") + "_helloControl");
		
		return "hello/hello";
	}
	
	@RequestMapping("hello/helloMav.do")
	public ModelAndView helloMav(String userId) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("hello/hello"); // ModelAndView객체를 통한 viewName 설정
		
		mav.addObject("nowDt", new Date());
		mav.addObject("msg", "hello, world");
		mav.addObject("userId", userId + "_helloControl");
		return mav;
	}
}
