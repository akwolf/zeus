package org.zunpeng.web.controller.admin.product;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by dapeng on 16/8/7.
 */
public class ProductFormBean {

	private String slug;

	private String name;

	private String brief;

	private String description;

	private double price;

	private MultipartFile upload;

	private boolean deleted;

	private boolean published;

	private int sequence = 0;

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

	public MultipartFile getUpload() {
		return upload;
	}

	public void setUpload(MultipartFile upload) {
		this.upload = upload;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
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
}
