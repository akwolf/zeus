package org.zunpeng.web.controller.admin.edit;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zunpeng.service.editor.UEditorService;
import org.zunpeng.service.upload.ImageService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dapeng on 16/8/7.
 */
@Controller("adminUEditorController")
@RequestMapping("/admin")
public class UEditorControllerm {

	private static Logger logger = LoggerFactory.getLogger(UEditorControllerm.class);

	@Autowired
	private ImageService imageService;

	@Autowired
	private UEditorService uEditorService;

	@RequestMapping("/demo")
	public String demo(){

		return "/admin/index/ueditor";
	}

	@RequestMapping("/ueditor")
	@ResponseBody
	public String config(@RequestParam(value = "action", required = false) String action,
	                     @RequestParam(value = "uploadFile", required = false)MultipartFile imageFile,
	                     HttpServletRequest request){
		logger.info(JSONObject.toJSONString(request.getParameterMap()));

		Object object;

		try {
			switch (action){
				case "config":
					object = uEditorService.config();
					break;
				case "uploadimage":
					object = imageService.upload(imageFile);
					break;
				default:
					object = "faile";
					break;
			}

		} catch(Throwable t){
			logger.info(t.getMessage(), t);
			object = t.getMessage();
		}

		return JSONObject.toJSONString(object);
	}
}
