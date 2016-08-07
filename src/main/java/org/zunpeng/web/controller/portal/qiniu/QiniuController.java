package org.zunpeng.web.controller.portal.qiniu;

import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zunpeng.service.qiniu.QiniuService;
import org.zunpeng.service.qiniu.QiniuUploadFormBean;
import org.zunpeng.service.qiniu.SimpleQiniuFileInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by dapeng on 16/8/6.
 */
@Controller
@ResponseBody
public class QiniuController {

	private static Logger logger = LoggerFactory.getLogger(QiniuController.class);

	@Autowired
	private QiniuService qiniuService;

	@RequestMapping(value = "/qn/token", method = RequestMethod.POST)
	public Map<String, Object> token(){
		Map<String, Object> map = Maps.newHashMap();

		try {
			String token = qiniuService.generateToken();
			map.put("success", true);
			map.put("token", token);
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
			map.put("success", false);
			map.put("message", t.getMessage());
		}

		return map;
	}

	@RequestMapping("/qn/fops/callback")
	public String fopsCallback(HttpServletRequest request){
		try {
			String qiniuRequestBody = IOUtils.toString(request.getInputStream(), "UTF-8");
			qiniuService.fopsCallback(qiniuRequestBody, false);
			return "success";
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
			return "fail";
		}
	}

	@RequestMapping("/qn/fops/mobile/callback")
	public String fopsMobileCallback(HttpServletRequest request){
		try {
			String qiniuRequestBody = IOUtils.toString(request.getInputStream(), "UTF-8");
			qiniuService.fopsCallback(qiniuRequestBody, true);
			return "success";
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
			return "fail";
		}
	}

	@RequestMapping("/qn/transcoding/callback")
	public String vframeCallback(HttpServletRequest request){
		try {
			String qiniuRequestBody = IOUtils.toString(request.getInputStream(), "UTF-8");
			qiniuService.vframeCallback(qiniuRequestBody);
			return "success";
		} catch(Throwable t){
			logger.info(t.getMessage(), t);
			return "fail";
		}
	}

	@RequestMapping("/qn/callback")
	public Map<String, Object> uploadCallback(QiniuUploadFormBean formBean){
		Map<String, Object> map = Maps.newHashMap();

		try {
			SimpleQiniuFileInfo simpleQiniuFileInfo = qiniuService.uploadCallback(formBean);
			map.put("success", true);
			map.put("fileInfo", simpleQiniuFileInfo);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			map.put("success", false);
			map.put("message", e.getMessage());
		}

		return map;
	}

	@RequestMapping("/ac/key")
	public String hlsKey(){

		return qiniuService.getHlsKey();
	}
}
