package org.zunpeng.web.controller.portal.index;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zunpeng.core.page.PageWrapper;
import org.zunpeng.domain.SlugInfo;
import org.zunpeng.service.account.AccountService;

import java.util.Map;

/**
 * Created by dapeng on 7/4/16.
 */
@Controller
public class AnonymousController {

	private static Logger logger = LoggerFactory.getLogger(AnonymousController.class);

	@Autowired
	private AccountService accountService;

	@RequestMapping({"/", ""})
	public String index(){
		return "portal/index/index";
	}

	@RequestMapping({"/add"})
	@ResponseBody
	public String add(){
		long startTimeMillis = System.currentTimeMillis();

		try {
			accountService.testAdd();
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
		}

		logger.info("add times: " + (System.currentTimeMillis() - startTimeMillis));

		return "hello world";
	}

	@RequestMapping({"/add-one"})
	@ResponseBody
	public String addOne(){
		long startTimeMillis = System.currentTimeMillis();

		try {
			accountService.testAddOne();
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
		}

		logger.info("add times: " + (System.currentTimeMillis() - startTimeMillis));

		return "hello world";
	}

	@RequestMapping("/he")
	public String he(){
		throw new RuntimeException("hello world hello world");
	}

	@RequestMapping("/page")
	@ResponseBody
	public Map<String, Object> page(@PageableDefault(size = 200, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
		long startTimeMillis = System.currentTimeMillis();
		Map<String, Object> map = Maps.newHashMap();

		try {
			PageWrapper<SlugInfo> page = accountService.page(pageable);
			map.put("success", true);
			map.put("page", page);
		} catch(Throwable t){
			map.put("success", false);
			map.put("message", t.getMessage());
			logger.info(t.getMessage(), t);
		}
		logger.info("add times: " + (System.currentTimeMillis() - startTimeMillis));

		return map;
	}

	@RequestMapping("/test")
	public String test(Model model){
		model.addAttribute("url", "http://zeus-video.zunpeng.org/5da6854c6d7f42c5829aa1717a5fe07a.m3u8?pm3u8/0/deadline/1470562186&e=1470538186&token=HLSXTk-f9pJDJhDFX-a6gcVcxuKDKvqaUmmhkQwE:d9HeNu6J6HBiZost6qE79KSTN7c=");
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

	@RequestMapping("/id")
	@ResponseBody
	public String testFind(@RequestParam(required = false, value = "id") Long id){
		accountService.testFind(id);
		return "test find by id";
	}

	@RequestMapping("/slug")
	@ResponseBody
	public String testFindBySlug(@RequestParam(required = false, value = "slug") String slug){
		accountService.testFindBySlug(slug);
		return "test find by slug";
	}

	@RequestMapping("/id/update")
	@ResponseBody
	public String updateSlug(){
		accountService.updateSlug();
		return "test update slug";
	}
}
