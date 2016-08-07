package org.zunpeng.service.qiniu;

import java.io.Serializable;

/**
 * Created by dapeng on 16/8/6.
 */
public class SimpleQiniuFileInfo implements Serializable {

	private static final long serialVersionUID = -3074710394726538558L;

	private String fileName;

	private String originalFileName;

	private long size;

	private String url;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
