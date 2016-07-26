package org.zunpeng.web.controller.portal.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dapeng on 16/7/24.
 */
@Controller
public class ProductController {

	@RequestMapping("/product")
	public String index(){

		return "portal/product/product_list";
	}
}
