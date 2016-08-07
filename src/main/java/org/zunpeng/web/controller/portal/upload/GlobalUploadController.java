package org.zunpeng.web.controller.portal.upload;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zunpeng.service.upload.SimpleUploadFileInfo;
import org.zunpeng.service.upload.UploadService;

import java.util.Map;

/**
 * Created by dapeng on 16/8/6.
 */
@Controller
public class GlobalUploadController {

	private static Logger logger = LoggerFactory.getLogger(GlobalUploadController.class);

	@Autowired
	private UploadService uploadService;

	@RequestMapping("/common/upload")
	@ResponseBody
	public Map<String, Object> index(@RequestPart("uploadFile") MultipartFile uploadFile){
		Map<String, Object> map = Maps.newHashMap();

		try {
			SimpleUploadFileInfo simpleUploadFileInfo = uploadService.upload(uploadFile);
			map.put("success", true);
			map.put("fileInfo", simpleUploadFileInfo);
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
			map.put("success", false);
			map.put("message", t.getMessage());
		}

		return map;
	}
}
