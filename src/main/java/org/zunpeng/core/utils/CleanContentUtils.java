package org.zunpeng.core.utils;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class CleanContentUtils {

	public static String clean(String content){
		return content == null ? null : Jsoup.clean(content, Whitelist.none());
	}

//	public static String cleanWithoutHref(String content){
//		return content == null ? null : UrlToHrefUtils.convertUrlToHref(content);
//	}

	public static String cleanHtml(String content){
		return content == null ? null : Jsoup.clean(content, JsoupWhiteListEnhance.relaxed());
	}

//	public static String cleanHtmlWithoutHref(String content){
//		return content == null ? null : UrlToHrefUtils.convertUrlToHref(Jsoup.clean(content, JsoupWhiteListEnhance.relaxed()));
//	}

	/**
	 * 把文本中得\r\n替换成<br/>
	 * @param content
	 * @return
	 */
	public static String replaceLineBreaks(String content){
		return content == null ? null : Jsoup.clean(content.replaceAll("\\r\\n", "<br/>"), Whitelist.basic());
	}

	/**
	 * 把<br/>替换成\r\n
	 * @param content
	 * @return
	 */
	public static String replaceBR(String content){
		return content == null ? null : content.replaceAll("\\n", "").replaceAll("<br/>", "\r\n").replaceAll("<br />", "\r\n").replaceAll("<br>", "\r\n").replaceAll("<br >", "\r\n");
	}
}
