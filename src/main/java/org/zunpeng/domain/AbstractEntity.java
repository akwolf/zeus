package org.zunpeng.domain;

import java.io.Serializable;
import java.util.Date;

public class AbstractEntity implements Serializable {

	private static final long serialVersionUID = -5922132851379196468L;

	private Long id;

	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	void createdAt(){
		this.createTime = new Date();
	}
}
