package org.zunpeng.web.controller.portal.index;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zunpeng.service.article.ArticleService;
import org.zunpeng.service.article.SimpleArticleInfo;
import org.zunpeng.service.product.ProductService;
import org.zunpeng.service.product.SimpleProductInfo;
import org.zunpeng.service.solution.SimpleSolutionInfo;
import org.zunpeng.service.solution.SolutionService;

import java.util.List;

/**
 * Created by dapeng on 7/4/16.
 */
@Controller
public class AnonymousController {

	private static Logger logger = LoggerFactory.getLogger(AnonymousController.class);

	@Autowired
	private SolutionService solutionService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ArticleService articleService;

	@RequestMapping({"/", ""})
	public String index(Model model){
		List<SimpleArticleInfo> articleInfoList = articleService.getAllRecommend();
		List<SimpleSolutionInfo> solutionInfoList = solutionService.getAllRecommend();
		List<SimpleProductInfo> productInfoList = productService.getAllRecommend();
		model.addAttribute("articleInfoList", articleInfoList);
		model.addAttribute("solutionInfoList", solutionInfoList);
		model.addAttribute("productInfoList", productInfoList);
		return "portal/index/index";
	}

	@RequestMapping("/test")
	public String test(Model model){
		model.addAttribute("url", "http://dby-vod-src.duobeiyun.com/7f91631893354479ae74290f3fb22dd1.m3u8");
		return "portal/index/test";
	}

	@RequestMapping("/fd5c69055541424cad5925ef3c1c58ec")
	public String test2(Model model){
		model.addAttribute("url", "http://zeus-video.zunpeng.org/bea6b4ef365c4eaead113a24a7794376.m3u8?pm3u8/0/deadline/1476563206&e=1476539206&token=HLSXTk-f9pJDJhDFX-a6gcVcxuKDKvqaUmmhkQwE:DNGxqhI7N5dOskizbCjqsOzY1_8=");
		return "portal/index/test";
	}

	@RequestMapping("/test_upload")
	public String uploadTest(){

		return "portal/index/test_upload";
	}

	@RequestMapping("/test_qiniu_upload")
	public String uploadQiniuTest(){

		return "portal/index/test_qiniu_upload";
	}
}
