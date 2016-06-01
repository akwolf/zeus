package org.zunpeng.web.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GlobalErrorController implements ErrorController {

	private static final String ERROR_PATH = "/error";

	@RequestMapping(value = {"/404", "/500", "/data/404", "/data/500"})
	public String index() {
		return "error";
	}

	@RequestMapping(ERROR_PATH)
	@ResponseBody
	public String error() {
		return index();
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}
}

