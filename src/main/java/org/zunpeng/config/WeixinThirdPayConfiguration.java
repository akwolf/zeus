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
//public class WeixinThirdPayConfiguration {
//
//	@Value("${pay.weixin.third.key}")
//	private String thirdKey;
//
//	@Value("${pay.weixin.third.appid}")
//	private String thirdAppid;
//
//	@Value("${pay.weixin.third.app_secret}")
//	private String thirdAppSecret;
//
//	@Value("${pay.weixin.third.mchid}")
//	private String thirdAppMchid;
//
//	@Value("${pay.weixin.third.notify_uri}")
//	private String thirdNotifyUrl;
//
//	@Value("${pay.weixin.third.authorize_redirect_uri}")
//	private String thirdAuthorizeRedirectUri;
//
//	@Value("${pay.weixin.third.cert_path}")
//	private String thirdCertLocalPath;
//
//	@Value("${pay.weixin.third.cert_password}")
//	private String thirdCertPassword;
//
//	@Bean(name = "weixinThirdPayEngine")
//	public WeixinPayEngine weixinThirdPayEngine(){
//		WeixinPayEngine weixinThirdPayEngine = new WeixinPayEngine();
//		weixinThirdPayEngine.setKey(thirdKey);
//		weixinThirdPayEngine.setAppid(thirdAppid);
//		weixinThirdPayEngine.setAppSecret(thirdAppSecret);
//		weixinThirdPayEngine.setAppMchid(thirdAppMchid);
//		weixinThirdPayEngine.setNotifyUrl(thirdNotifyUrl);
//		weixinThirdPayEngine.setAuthorizeRedirectUri(thirdAuthorizeRedirectUri);
//		weixinThirdPayEngine.setCertLocalPath(thirdCertLocalPath);
//		weixinThirdPayEngine.setCertPassword(thirdCertPassword);
//
//		return weixinThirdPayEngine;
//	}
//
//}
