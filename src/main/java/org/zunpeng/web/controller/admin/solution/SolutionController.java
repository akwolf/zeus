package org.zunpeng.web.controller.admin.solution;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

/**
 * Created by dapeng on 16/7/24.
 */
@Controller("adminSolutionController")
@RequestMapping("/admin")
public class SolutionController {

	private static Logger logger = LoggerFactory.getLogger(SolutionController.class);

	@RequestMapping("/solution")
	public String index(@PageableDefault(size = 20) Pageable pageable, Model model){

		return "admin/solution/solution_list";
	}

	@RequestMapping(value = "/solution/add", method = RequestMethod.GET)
	public String editNew(){

		return "admin/solution/solution_detail";
	}

	@RequestMapping(value = "/solution/add", method = RequestMethod.POST)
	public String add(@ModelAttribute @Valid SolutionFormBean formBean, BindingResult result, RedirectAttributes redirectAttributes){

		return "redirect:/admin/solution/edit";
	}

	@RequestMapping(value = "/solution/edit", method = RequestMethod.GET)
	public String edit(Model model){

		return "admin/solution/solution_detail";
	}

	@RequestMapping(value = "/solution/edit", method = RequestMethod.POST)
	public String update(@ModelAttribute @Valid SolutionFormBean formBean, BindingResult result, RedirectAttributes redirectAttributes){

		return "redirect:/admin/solution/edit";
	}

	@RequestMapping(value = "/ajax/solution/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ajaxUpdate(@ModelAttribute SolutionFormBean formBean){
		Map<String, Object> map = Maps.newHashMap();

		try {

			map.put("success", true);
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
			map.put("success", false);
			map.put("message", t.getMessage());
		}

		return map;
	}
}
