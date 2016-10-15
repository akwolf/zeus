package org.zunpeng.web.controller.portal.solution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zunpeng.core.page.PageWrapper;
import org.zunpeng.service.solution.SimpleSolutionInfo;
import org.zunpeng.service.solution.SolutionService;

/**
 * Created by dapeng on 16/7/24.
 */
@Controller
public class SolutionController {

	@Autowired
	private SolutionService solutionService;

	@RequestMapping("/solution")
	public String index(@PageableDefault(size = 20) Pageable pageable, Model model){
		PageWrapper<SimpleSolutionInfo> page = solutionService.page(pageable);
		model.addAttribute("page", page);
		return "portal/solution/solution_list";
	}

	@RequestMapping("/solution/{slug}")
	public String detail(@PathVariable String slug, Model model){
		SimpleSolutionInfo simpleSolutionInfo = solutionService.getBySlug(slug);
		model.addAttribute("solutionInfo", simpleSolutionInfo);
		return "portal/solution/solution_detail";
	}
}
