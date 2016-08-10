package org.zunpeng.web.controller.admin.product;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

/**
 * Created by dapeng on 16/7/24.
 */
@Controller("adminProductController")
@RequestMapping("/admin")
public class ProductController {

	private static Logger logger = LoggerFactory.getLogger(ProductController.class);

	@RequestMapping({"/product", ""})
	public String index(@PageableDefault(size = 20) Pageable pageable, Model model){

		return "admin/product/product_list";
	}

	@RequestMapping(value = "/product/add", method = RequestMethod.GET)
	public String editNew(){

		return "admin/product/product_edit";
	}

	@RequestMapping(value = "/product/add", method = RequestMethod.POST)
	public String add(@ModelAttribute @Valid ProductFormBean formBean, BindingResult result, RedirectAttributes redirectAttributes){

		return "redirect:/admin/product/edit";
	}

	@RequestMapping(value = "/product/edit", method = RequestMethod.GET)
	public String edit(Model model){

		return "admin/product/product_edit";
	}

	@RequestMapping(value = "/product/edit", method = RequestMethod.POST)
	public String update(@ModelAttribute @Valid ProductFormBean formBean, BindingResult result, RedirectAttributes redirectAttributes){

		return "redirect:/admin/product/edit";
	}

	@RequestMapping(value = "/ajax/product/edit", method = RequestMethod.POST)
	public Map<String, Object> ajaxUpdate(@ModelAttribute ProductFormBean formBean){
		Map<String, Object> map = Maps.newHashMap();

		try {

			map.put("success", true);
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
			map.put("success", false);
			map.put("message", t.getMessage());
		}

		return map;
	}
}
