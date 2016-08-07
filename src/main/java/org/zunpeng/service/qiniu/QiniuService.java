package org.zunpeng.service.qiniu;

/**
 * Created by dapeng on 16/8/6.
 */
public interface QiniuService {

	String generateToken();

	SimpleQiniuFileInfo uploadCallback(QiniuUploadFormBean formBean);

	void fopsCallback(String qiniuRequestBody, boolean mobile);

	void vframeCallback(String qiniuRequestBody);

	String getHlsKey();
}
