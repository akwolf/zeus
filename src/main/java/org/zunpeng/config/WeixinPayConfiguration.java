//package org.zunpeng.config;
//
//import com.duobeiyun.core.weixin.WeixinPayEngine;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Created by dapeng on 16/3/22.
// */
//@Configuration
//public class WeixinPayConfiguration {
//
//	@Value("${pay.weixin.key}")
//	private String key;
//
//	@Value("${pay.weixin.appid}")
//	private String appid;
//
//	@Value("${pay.weixin.app_secret}")
//	private String appSecret;
//
//	@Value("${pay.weixin.mchid}")
//	private String appMchid;
//
//	@Value("${pay.weixin.notify_uri}")
//	private String notifyUrl;
//
//	@Value("${pay.weixin.authorize_redirect_uri}")
//	private String authorizeRedirectUri;
//
//	@Value("${pay.weixin.cert_path}")
//	private String certLocalPath;
//
//	@Value("${pay.weixin.cert_password}")
//	private String certPassword;
//
//	@Bean(name = "weixinPayEngine")
//	public WeixinPayEngine weixinPayEngine(){
//		WeixinPayEngine weixinPayEngine = new WeixinPayEngine();
//		weixinPayEngine.setKey(key);
//		weixinPayEngine.setAppid(appid);
//		weixinPayEngine.setAppSecret(appSecret);
//		weixinPayEngine.setAppMchid(appMchid);
//		weixinPayEngine.setNotifyUrl(notifyUrl);
//		weixinPayEngine.setAuthorizeRedirectUri(authorizeRedirectUri);
//		weixinPayEngine.setCertLocalPath(certLocalPath);
//		weixinPayEngine.setCertPassword(certPassword);
//
//		return weixinPayEngine;
//	}
//
//}
