package org.zunpeng.web.controller.portal.aboutus;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dapeng on 16/7/24.
 */
@Controller
public class AboutUsController {

	@RequestMapping("/about_us")
	public String index(){

		return "portal/about_us/about_us";
	}
}
