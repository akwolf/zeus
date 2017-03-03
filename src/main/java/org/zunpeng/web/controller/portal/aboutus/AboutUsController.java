package org.zunpeng.web.controller.portal.aboutus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zunpeng.service.company.CompanyService;
import org.zunpeng.service.company.SimpleCompanyInfo;

/**
 * Created by dapeng on 16/7/24.
 */
@Controller
public class AboutUsController {

	@Autowired
	private CompanyService companyService;

	@RequestMapping("/about_us")
	public String index(Model model){
		SimpleCompanyInfo simpleCompanyInfo = companyService.getInfo(1L);
		model.addAttribute("companyInfo", simpleCompanyInfo);
		return "portal/about_us/about_us";
	}
}
