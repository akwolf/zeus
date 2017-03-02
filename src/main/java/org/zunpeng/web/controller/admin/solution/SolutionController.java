package org.zunpeng.web.controller.admin.solution;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zunpeng.core.page.PageWrapper;
import org.zunpeng.service.solution.SimpleSolutionInfo;
import org.zunpeng.service.solution.SolutionService;

import javax.validation.Valid;
import java.util.Map;

/**
 * Created by dapeng on 16/7/24.
 */
@Controller("adminSolutionController")
@RequestMapping("/admin")
public class SolutionController {

	private static Logger logger = LoggerFactory.getLogger(SolutionController.class);

	@Autowired
	private SolutionService solutionService;

	@RequestMapping("/solution")
	public String index(@PageableDefault(size = 20) Pageable pageable, Model model){
		PageWrapper<SimpleSolutionInfo> page = solutionService.page(pageable);
		model.addAttribute("page", page);
		return "admin/solution/solution_list";
	}

	@RequestMapping(value = "/solution/add", method = RequestMethod.GET)
	public String editNew(Model model){
		SimpleSolutionInfo solutionInfo = new SimpleSolutionInfo();
		model.addAttribute("solutionInfo", solutionInfo)
				.addAttribute("type", 1);

		return "admin/solution/solution_edit";
	}

	@RequestMapping(value = "/solution/add", method = RequestMethod.POST)
	public String add(@ModelAttribute @Valid SolutionFormBean formBean, BindingResult result, RedirectAttributes redirectAttributes, Model model){
		if(result.hasErrors()){
			logger.info(JSONObject.toJSONString(result.getAllErrors()));
			model.addAttribute("solutionInfo", formBean);
			return "admin/solution/solution_edit";
		}

		try {
			SimpleSolutionInfo simpleSolutionInfo = solutionService.add(formBean);
			redirectAttributes.addAttribute("slug", simpleSolutionInfo.getSlug());
			return "redirect:/admin/solution/edit";
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
			model.addAttribute("solutionInfo", formBean);
			return "admin/solution/solution_edit";
		}
	}

	@RequestMapping(value = "/solution/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String slug, Model model){
		SimpleSolutionInfo simpleSolutionInfo = solutionService.getBySlug(slug);
		model.addAttribute("solutionInfo", simpleSolutionInfo);
		return "admin/solution/solution_edit";
	}

	@RequestMapping(value = "/solution/edit", method = RequestMethod.POST)
	public String update(@ModelAttribute @Valid SolutionFormBean formBean, BindingResult result, RedirectAttributes redirectAttributes, Model model){
		if(result.hasErrors()){
			logger.info(JSONObject.toJSONString(result.getAllErrors()));
			model.addAttribute("solutionInfo", formBean);
			return "admin/solution/solution_edit";
		}

		try {
			SimpleSolutionInfo simpleSolutionInfo = solutionService.edit(formBean);
			redirectAttributes.addAttribute("slug", simpleSolutionInfo.getSlug());
			return "redirect:/admin/solution/edit";
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
			model.addAttribute("solutionInfo", formBean);
			return "admin/solution/solution_edit";
		}
	}

	@RequestMapping(value = "/ajax/solution/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ajaxUpdate(@ModelAttribute @Valid SolutionFormBean formBean, BindingResult result){
		Map<String, Object> map = Maps.newHashMap();

		if(result.hasErrors()){
			logger.info(JSONObject.toJSONString(result.getAllErrors()));
			map.put("success", false);
			return map;
		}

		try {
			SimpleSolutionInfo simpleSolutionInfo = solutionService.edit(formBean);
			map.put("success", true);
			map.put("solutionInfo", simpleSolutionInfo);
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
			map.put("success", false);
			map.put("message", t.getMessage());
		}

		return map;
	}
}
