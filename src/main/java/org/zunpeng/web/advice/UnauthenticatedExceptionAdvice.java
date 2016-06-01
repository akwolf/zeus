package org.zunpeng.web.advice;

import com.google.common.collect.Maps;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@ControllerAdvice
public class UnauthenticatedExceptionAdvice {

	@ExceptionHandler(value  = {UnauthenticatedException.class, UnauthorizedException.class})
	@ResponseBody
	public Map<String, Object> handleException(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = Maps.newHashMap();

		map.put("success", false);
		map.put("message", "access denied");

		return map;
	}
}
