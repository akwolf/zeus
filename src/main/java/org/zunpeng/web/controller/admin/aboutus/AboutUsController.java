package org.zunpeng.web.controller.admin.aboutus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by dapeng on 16/7/24.
 */
@Controller("adminAboutUsController")
@RequestMapping("/admin")
public class AboutUsController {

	private static Logger logger = LoggerFactory.getLogger(AboutUsController.class);

	@RequestMapping("/about_us")
	public String index(){

		return "admin/about_us/about_us";
	}

	@RequestMapping(value = "/about_us/edit", method = RequestMethod.GET)
	public String edit(Model model){

		return "admin/about_us/about_us_edit";
	}

	@RequestMapping(value = "/about_us/edit", method = RequestMethod.POST)
	public String update(@ModelAttribute @Valid AboutUsFormBean formBean, BindingResult result, RedirectAttributes redirectAttributes){

		return "redirect:/admin/about_us/edit";
	}
}
