package org.zunpeng.web.controller.portal.solution;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dapeng on 16/7/24.
 */
@Controller
public class SolutionController {

	@RequestMapping("/solution")
	public String index(@PageableDefault(size = 20) Pageable pageable, Model model){

		return "portal/solution/solution_list";
	}

	@RequestMapping("/solution/{slug}")
	public String detail(@PathVariable String slug, Model model){

		return "portal/solution/solution_detail";
	}
}
