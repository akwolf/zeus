package org.zunpeng.service.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by dapeng on 16/8/6.
 */
@Component
public class StaticBasicConfig {

	@Value("${static.image.download.path}")
	private String staticImgDownloadPath;

	@Value("${static.image.url}")
	private String staticImgDomain;

	public String getStaticImgDownloadPath() {
		return staticImgDownloadPath;
	}

	public String getStaticImgDomain() {
		return staticImgDomain;
	}
}
