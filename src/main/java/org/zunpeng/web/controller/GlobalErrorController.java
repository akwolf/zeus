package org.zunpeng.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dapeng on 16/7/26.
 */
@Controller
public class GlobalErrorController {

	@RequestMapping("/error")
	public String index(){
		return "error";
	}
}
