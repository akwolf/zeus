package org.zunpeng.core.shiro;

import org.apache.shiro.web.servlet.ShiroFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by dapeng on 16/8/10.
 */
public class ZeusShiroFilter extends ShiroFilter {

	private static Logger logger = LoggerFactory.getLogger(ZeusShiroFilter.class);

	@Override
	protected ServletResponse wrapServletResponse(HttpServletResponse response, ShiroHttpServletRequest request) {
		logger.info("------------------------ zeus shiro filter");
		return new ZeusShiroHttpServletResponse(response, getServletContext(), request);
	}



}
