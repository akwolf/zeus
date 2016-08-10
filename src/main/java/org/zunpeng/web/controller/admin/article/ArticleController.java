package org.zunpeng.web.controller.admin.article;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

/**
 * Created by dapeng on 16/7/24.
 */
@Controller("adminArticleController")
@RequestMapping("/admin")
public class ArticleController {

	private static Logger logger = LoggerFactory.getLogger(ArticleController.class);

	@RequestMapping("/article")
	public String index(@PageableDefault(size = 20) Pageable pageable, Model model){

		return "admin/article/article_list";
	}

	@RequestMapping(value = "/article/add", method = RequestMethod.GET)
	public String editNew(){

		return "admin/article/article_edit";
	}

	@RequestMapping(value = "/article/add", method = RequestMethod.POST)
	public String add(@ModelAttribute @Valid ArticleFormBean formBean, BindingResult result, RedirectAttributes redirectAttributes){

		return "redirect:/admin/article/edit";
	}

	@RequestMapping(value = "/article/edit", method = RequestMethod.GET)
	public String edit(Model model){

		return "admin/article/article_edit";
	}

	@RequestMapping(value = "/article/edit", method = RequestMethod.POST)
	public String update(@ModelAttribute @Valid ArticleFormBean formBean, BindingResult result, RedirectAttributes redirectAttributes){

		return "redirect:/admin/article/edit";
	}

	@RequestMapping(value = "/ajax/article/edit", method = RequestMethod.POST)
	public Map<String, Object> update(@ModelAttribute ArticleFormBean formBean){
		Map<String, Object> map = Maps.newHashMap();

		try {

			map.put("success", true);
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
			map.put("success", false);
			map.put("message", t.getMessage());
		}

		return map;
	}
}
