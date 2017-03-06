package org.zunpeng.web.controller.portal.article;

import com.oldpeng.core.alipay.AlipayPaymentBean;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zunpeng.core.page.PageWrapper;
import org.zunpeng.core.shiro.config.EnhanceSecurityUtils;
import org.zunpeng.service.account.AccountService;
import org.zunpeng.service.account.SimpleAccountInfo;
import org.zunpeng.service.article.ArticleService;
import org.zunpeng.service.article.SimpleArticleInfo;
import org.zunpeng.service.payment.PaymentService;

/**
 * Created by dapeng on 16/7/24.
 */
@Controller
public class ArticleController {

	private static Logger logger = LoggerFactory.getLogger(ArticleController.class);

	@Autowired
	private ArticleService articleService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private PaymentService paymentService;

	@RequestMapping("/article")
	@RequiresUser
	public String index(@PageableDefault(size = 20) Pageable pageable, Model model){
		Long accountId = EnhanceSecurityUtils.retrieveEnhanceUser().getAccountId();

		SimpleAccountInfo accountInfo = accountService.getById(accountId);
		if(!"hello".equalsIgnoreCase(accountInfo.getBrief())){
			//跳转到购买页面
			AlipayPaymentBean alipayPaymentBean = paymentService.getPaymentUrl(accountId);
			model.addAttribute("paymentBean", alipayPaymentBean);
			return "zhifubao";
		}

		PageWrapper<SimpleArticleInfo> page = articleService.pageByPublished(pageable);
		model.addAttribute("page", page);
		return "portal/article/article_list";
	}

	@RequestMapping("/article/{slug}")
	public String detail(@PathVariable String slug, Model model){
		SimpleArticleInfo simpleArticleInfo = articleService.getBySlug(slug);
		model.addAttribute("articleInfo", simpleArticleInfo);
		return "portal/article/article_detail";
	}
}
