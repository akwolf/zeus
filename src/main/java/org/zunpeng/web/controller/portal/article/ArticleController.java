package org.zunpeng.web.controller.portal.article;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dapeng on 16/7/24.
 */
@Controller
public class ArticleController {

	@RequestMapping("article")
	public String index(){

		return "portal/article/article_list";
	}
}
