package org.zunpeng.web.controller.admin.product;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zunpeng.core.page.PageWrapper;
import org.zunpeng.service.product.ProductService;
import org.zunpeng.service.product.SimpleProductInfo;

import javax.validation.Valid;
import java.util.Map;

/**
 * Created by dapeng on 16/7/24.
 */
@Controller("adminProductController")
@RequestMapping("/admin")
public class ProductController {

	private static Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@RequestMapping({"/product", ""})
	public String index(@PageableDefault(size = 20) Pageable pageable, Model model){
		PageWrapper<SimpleProductInfo> page = productService.page(pageable);
		model.addAttribute("page", page);
		return "admin/product/product_list";
	}

	@RequestMapping(value = "/product/add", method = RequestMethod.GET)
	public String editNew(){

		return "admin/product/product_edit";
	}

	@RequestMapping(value = "/product/add", method = RequestMethod.POST)
	public String add(@ModelAttribute @Valid ProductFormBean formBean, BindingResult result, RedirectAttributes redirectAttributes, Model model){
		if(result.hasErrors()){
			logger.info(JSONObject.toJSONString(result.getAllErrors()));
			model.addAttribute("productInfo", formBean);
			return "admin/product/product_edit";
		}

		try {
			SimpleProductInfo simpleProductInfo = productService.add(formBean);
			redirectAttributes.addAttribute("slug", simpleProductInfo.getSlug());
			return "redirect:/admin/product/edit";
		} catch (Throwable t){
			logger.info(t.getMessage(), t);
			model.addAttribute("productInfo", formBean);
			return "admin/product/product_edit";
		}
	}

	@RequestMapping(value = "/product/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String slug, Model model){
		SimpleProductInfo simpleProductInfo = productService.getBySlug(slug);
		model.addAttribute("productInfo", simpleProductInfo);
		return "admin/product/product_edit";
	}

	@RequestMapping(value = "/product/edit", method = RequestMethod.POST)
	public String update(@ModelAttribute @Valid ProductFormBean formBean, BindingResult result, RedirectAttributes redirectAttributes, Model model){
		if(result.hasErrors()){
			logger.info(JSONObject.toJSONString(result.getAllErrors()));
			model.addAttribute("productInfo", formBean);
			return "admin/product/product_edit";
		}

		try {
			SimpleProductInfo simpleProductInfo = productService.edit(formBean);
			redirectAttributes.addAttribute("slug", simpleProductInfo.getSlug());
			return "redirect:/admin/product/edit";
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
			model.addAttribute("productInfo", formBean);
			return "admin/product/product_edit";
		}
	}

	@RequestMapping(value = "/ajax/product/edit", method = RequestMethod.POST)
	public Map<String, Object> ajaxUpdate(@ModelAttribute @Valid ProductFormBean formBean, BindingResult result){
		Map<String, Object> map = Maps.newHashMap();

		if(result.hasErrors()){
			map.put("success", false);
			map.put("message", "field error");
			return map;
		}

		try {
			SimpleProductInfo simpleProductInfo = productService.edit(formBean);
			map.put("success", true);
			map.put("productInfo", simpleProductInfo);
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
			map.put("success", false);
			map.put("message", t.getMessage());
		}

		return map;
	}
}
