package kr.or.ddit.lprod.web;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.common.model.Page;
import kr.or.ddit.lprod.service.ILprodService;

@RequestMapping("lprod/")
@Controller
public class LprodController {

	@Resource(name = "lprodService")
	private ILprodService lprodService;
	
	@RequestMapping("lprodList")
	public String lprodList(Model model) {
		model.addAttribute("lprodList", lprodService.getLprodList());
		
		return "lprod/lprodList";
	}
	
	@RequestMapping("lprodPagingList")
	public String lprodPagingList(Page page, Model model) {
		page.setPagesize(5);
		model.addAttribute("pageVo", page);
		
		Map<String, Object> resultMap = lprodService.getLprodPagingList(page);
		
		model.addAllAttributes(resultMap); // 위 두가지 내용을 한번에 넣기
		
		return "lprod/lprodPagingList";
	}
	
}
