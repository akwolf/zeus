package org.zunpeng.service.qiniu;

import org.joda.time.DateTime;

/**
 * Created by dapeng on 16/8/6.
 */
public interface QiniuService {

	String generateToken();

	SimpleQiniuFileInfo uploadCallback(QiniuUploadFormBean formBean);

	void fopsCallback(String qiniuRequestBody, boolean mobile);

	void vframeCallback(String qiniuRequestBody);

	String generateHlsUrl(String m3u8Key, DateTime deadlineTime, long expires);

	String getHlsKey();
}
