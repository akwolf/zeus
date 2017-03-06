package org.zunpeng.web.controller.portal.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zunpeng.service.account.AccountService;

import javax.validation.Valid;

/**
 * Created by dapeng on 2017/3/4.
 */
@Controller
public class RegisterController {

	private static Logger logger = LoggerFactory.getLogger(RegisterController.class);

	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String index(Model model){
		RegisterFormBean registerFormBean = new RegisterFormBean();
		model.addAttribute("account", registerFormBean);
		return "portal/account/register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@ModelAttribute @Valid RegisterFormBean formBean, BindingResult result, Model model, RedirectAttributes redirectAttributes){
		if(result.hasErrors()){
			model.addAttribute("account", formBean);
			model.addAttribute("message", "必须使用手机号注册，且密码不能为空");
			return "portal/account/register";
		}

		try {
			accountService.add(formBean);
			redirectAttributes.addAttribute("type", 1);
			return "redirect:/register/success";
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
			model.addAttribute("account", formBean);
			model.addAttribute("message", t.getMessage());
			return "portal/account/register";
		}
	}

	@RequestMapping("/register/success")
	public String registerSuccess(@RequestParam(value = "type", required = false) Integer type, Model model){
		if(type != null){
			return "portal/account/register_success";
		} else {
			return "redirect:/login";
		}
	}
}
