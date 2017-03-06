package org.zunpeng.web.controller.portal.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zunpeng.core.page.PageWrapper;
import org.zunpeng.service.product.ProductService;
import org.zunpeng.service.product.SimpleProductInfo;

/**
 * Created by dapeng on 16/7/24.
 */
@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	@RequestMapping("/product")
	public String index(@PageableDefault(size = 20) Pageable pageable, Model model){
		PageWrapper<SimpleProductInfo> page = productService.pageByPublished(pageable);
		model.addAttribute("page", page);
		return "portal/product/product_list";
	}

	@RequestMapping("/product/{slug}")
	public String detail(@PathVariable String slug, Model model){
		SimpleProductInfo productInfo = productService.getBySlug(slug);
		model.addAttribute("productInfo", productInfo);
		return "portal/product/product_detail";
	}
}
