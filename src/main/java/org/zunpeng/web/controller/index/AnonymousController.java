package org.zunpeng.web.controller.index;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
	@ResponseBody
	public String index(){
		long startTimeMillis = System.currentTimeMillis();

		try {
			logger.info("result: " + accountService.test());
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
		}

		logger.info("get times: " + (System.currentTimeMillis() - startTimeMillis));

		return "hello world";
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

	@RequestMapping("/page")
	@ResponseBody
	public Map<String, Object> page(@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
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

		return map;
	}
}
