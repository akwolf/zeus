package org.zunpeng.web.controller.admin.company;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.zunpeng.service.company.CompanyService;
import org.zunpeng.service.company.SimpleCompanyInfo;

import javax.validation.Valid;

/**
 * Created by dapeng on 2016/10/14.
 */
@Controller("adminCompanyController")
@RequestMapping("/admin")
public class CompanyController {

	private static Logger logger = LoggerFactory.getLogger(CompanyController.class);

	@Autowired
	private CompanyService companyService;

	//	@RequestMapping("/about_us")
	public String index(){

		return "admin/about_us/about_us";
	}

	@RequestMapping(value = {"/about_us/edit", "/about_us"}, method = RequestMethod.GET)
	public String edit(Model model){
		SimpleCompanyInfo simpleCompanyInfo = companyService.getInfo(1L);
		simpleCompanyInfo = simpleCompanyInfo == null ? new SimpleCompanyInfo() : simpleCompanyInfo;

		model.addAttribute("companyInfo", simpleCompanyInfo)
				.addAttribute("type", 2);

		return "admin/about_us/about_us_edit";
	}

	@RequestMapping(value = "/about_us/edit", method = RequestMethod.POST)
	public String update(@ModelAttribute @Valid CompanyFormBean formBean, BindingResult result, Model model){
		if(result.hasErrors()){
			logger.info(JSONObject.toJSONString(result.getAllErrors()));
			model.addAttribute("companyInfo", formBean)
					.addAttribute("type", 2);
			return "admin/about_us/about_us_edit";
		}

		try {
			if(formBean.getId() == null){
				companyService.add(formBean);
			} else {
				companyService.updateInfo(formBean);
			}
			return "redirect:/admin/about_us/edit";
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
			model.addAttribute("companyInfo", formBean)
					.addAttribute("type", 2);
			return "admin/product/about_us_edit";
		}
	}
}
