package org.zunpeng.web.controller.portal.article;

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
import org.zunpeng.service.article.ArticleService;
import org.zunpeng.service.article.SimpleArticleInfo;

/**
 * Created by dapeng on 16/7/24.
 */
@Controller
public class ArticleController {

	private static Logger logger = LoggerFactory.getLogger(ArticleController.class);

	@Autowired
	private ArticleService articleService;

	@RequestMapping("/article")
	public String index(@PageableDefault(size = 20) Pageable pageable, Model model){
		PageWrapper<SimpleArticleInfo> page = articleService.page(pageable);
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
