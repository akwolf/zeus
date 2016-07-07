package org.zunpeng.domain;

import java.util.Date;

/**
 * 产品
 * Created by dapeng on 6/7/16.
 */
public class ProductInfo extends AbstractEntity {

	private static final long serialVersionUID = -6921862457432765953L;

	private String slug;

	private String name;

	private String brief;

	private String description;

	private long amount = 0;

	private Date lastModifyTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}
}
