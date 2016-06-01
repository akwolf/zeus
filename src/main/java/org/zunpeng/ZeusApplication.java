package org.zunpeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import java.util.Collections;

@SpringBootApplication
@EnableScheduling
public class ZeusApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ZeusApplication.class);
		app.setAddCommandLineProperties(false);
		app.run(args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ZeusApplication.class);
	}

	//TODO 禁止进行url重写 防止通过拼接JSESSIONID方式登录应用, 没有生效
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
		sessionCookieConfig.setHttpOnly(true);
		servletContext.setSessionTrackingModes(Collections.singleton(SessionTrackingMode.COOKIE));
		super.onStartup(servletContext);
	}
}
