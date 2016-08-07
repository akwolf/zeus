package org.zunpeng.service.qiniu;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * Created by dapeng on 16/8/6.
 */
public class QiniuUploadFormBean implements Serializable {

	private static final long serialVersionUID = 9219665707957617770L;

	@NotEmpty
	private String key;

	@NotEmpty
	private String hash;

	@NotEmpty
	private String name;

	private long size;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
}
