package org.zunpeng.web.advice;

import com.google.common.collect.Maps;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice
public class ThrowableHandlerControllerAdvice {

	private static Logger logger = LoggerFactory.getLogger(ThrowableHandlerControllerAdvice.class);

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public Map<String, Object> handleNoSuchElementException(Throwable e) {
		logger.info(e.getMessage(), e);

		Map<String, Object> map = Maps.newHashMap();
		map.put("message", e.getMessage());
		map.put("success", false);
		return map;
	}

	@ExceptionHandler(AuthorizationException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public Map<String, Object> handleAccessDeniedException(AuthorizationException e, HttpServletRequest request){
		logger.info(e.getMessage(), e);
		Map<String, Object> map = Maps.newHashMap();
		map.put("message", "access denied");
		map.put("success", false);
		return map;
	}
}
