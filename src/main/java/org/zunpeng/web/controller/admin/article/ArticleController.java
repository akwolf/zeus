package org.zunpeng.web.controller.admin.article;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zunpeng.core.page.PageWrapper;
import org.zunpeng.service.article.ArticleService;
import org.zunpeng.service.article.SimpleArticleInfo;

import javax.validation.Valid;
import java.util.Map;

/**
 * Created by dapeng on 16/7/24.
 */
@Controller("adminArticleController")
@RequestMapping("/admin")
public class ArticleController {

	private static Logger logger = LoggerFactory.getLogger(ArticleController.class);

	@Autowired
	private ArticleService articleService;

	@RequestMapping("/article")
	public String index(@PageableDefault(size = 20) Pageable pageable, Model model){
		PageWrapper<SimpleArticleInfo> page = articleService.page(pageable);
		model.addAttribute("page", page);
		return "admin/article/article_list";
	}

	@RequestMapping(value = "/article/add", method = RequestMethod.GET)
	public String editNew(){

		return "admin/article/article_edit";
	}

	@RequestMapping(value = "/article/add", method = RequestMethod.POST)
	public String add(@ModelAttribute @Valid ArticleFormBean formBean, BindingResult result, RedirectAttributes redirectAttributes, Model model){
		if(result.hasErrors()){
			logger.info(JSONObject.toJSONString(result.getAllErrors()));
			model.addAttribute("articleInfo", formBean);
			return "admin/article/article_edit";
		}

		SimpleArticleInfo simpleArticleInfo = articleService.add(formBean);
		redirectAttributes.addAttribute("slug", simpleArticleInfo.getSlug());

		return "redirect:/admin/article/edit";
	}

	@RequestMapping(value = "/article/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String slug, Model model){
		SimpleArticleInfo simpleArticleInfo = articleService.getBySlug(slug);
		model.addAttribute("article", simpleArticleInfo);
		return "admin/article/article_edit";
	}

	@RequestMapping(value = "/article/edit", method = RequestMethod.POST)
	public String update(@ModelAttribute @Valid ArticleFormBean formBean, BindingResult result, RedirectAttributes redirectAttributes, Model model){
		if(result.hasErrors()){
			logger.info(JSONObject.toJSONString(result.getAllErrors()));
			model.addAttribute("articleInfo", formBean);
			return "admin/article/article_edit";
		}

		SimpleArticleInfo simpleArticleInfo = articleService.edit(formBean);
		redirectAttributes.addAttribute("slug", simpleArticleInfo.getSlug());
		return "redirect:/admin/article/edit";
	}

	@RequestMapping(value = "/ajax/article/edit", method = RequestMethod.POST)
	public Map<String, Object> update(@ModelAttribute @Valid ArticleFormBean formBean, BindingResult result){
		Map<String, Object> map = Maps.newHashMap();

		if(result.hasErrors()){
			logger.info(JSONObject.toJSONString(result.getAllErrors()));
			map.put("success", false);
			map.put("message", "field error");
			return map;
		}

		try {
			SimpleArticleInfo simpleArticleInfo = articleService.edit(formBean);
			map.put("success", true);
			map.put("articleInfo", simpleArticleInfo);
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
			map.put("success", false);
			map.put("message", t.getMessage());
		}

		return map;
	}
}
