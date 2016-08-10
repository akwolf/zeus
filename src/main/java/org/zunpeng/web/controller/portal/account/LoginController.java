package org.zunpeng.web.controller.portal.account;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by dapeng on 16/8/8.
 */
@Controller
public class LoginController {

	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String index(){

		return "portal/account/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute @Valid LoginFormBean formBean, BindingResult result, Model model){

		if(result.hasErrors()){
			model.addAttribute("account", formBean);
			return "portal/account/login";
		}

		try {
			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(formBean.getMobile(), formBean.getPassword());
			SecurityUtils.getSubject().login(usernamePasswordToken);
			return "redirect:/admin";
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
			model.addAttribute("account", formBean);
			return "portal/account/login";
		}
	}
}
