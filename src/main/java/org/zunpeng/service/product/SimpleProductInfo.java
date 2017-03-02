package org.zunpeng.service.product;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dapeng on 2016/10/12.
 */
public class SimpleProductInfo implements Serializable {

	private static final long serialVersionUID = 5710601228864463093L;

	private String slug;

	private String name;

	private String brief;

	private String description;

	private double price;

	private Date lastModifyTime;

	private Date createTime;

	private int sequence = 0;

	private boolean published;

	private String coverUrl;

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}
}
