package org.zunpeng.web.advice;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ThrowableHandlerControllerAdvice {

	private static Logger logger = LoggerFactory.getLogger(ThrowableHandlerControllerAdvice.class);

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleNoSuchElementException(Throwable e) {
		logger.info(e.getMessage(), e);
		return "error";
	}

	@ExceptionHandler(value  = {UnauthenticatedException.class, UnauthorizedException.class})
//	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleAccessDeniedException(AuthorizationException e){
		logger.info(e.getMessage(), e);
		return "redirect:/login";
	}
}
