package org.zunpeng.web.controller.portal.solution;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dapeng on 16/7/24.
 */
@Controller
public class SolutionController {

	@RequestMapping("/solution")
	public String index(){

		return "portal/solution/solution_list";
	}
}
