package org.zunpeng.web.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.zunpeng.core.shiro.config.EnhanceSecurityUtils;
import org.zunpeng.service.account.AccountService;
import org.zunpeng.service.account.SimpleAccountInfo;
import org.zunpeng.service.company.CompanyService;
import org.zunpeng.service.company.SimpleCompanyInfo;

@ControllerAdvice
public class CustomUserControllerAdvice {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private AccountService accountService;

	@ModelAttribute("customUser")
	public SimpleAccountInfo getCurrentUser() {
		Long accountId = EnhanceSecurityUtils.retrieveEnhanceUser().getAccountId();
		return accountService.getById(accountId);
	}

	@ModelAttribute("companyInfo")
	public SimpleCompanyInfo getSimpleCompanyInfo(){
		return companyService.getInfo();
	}
}
