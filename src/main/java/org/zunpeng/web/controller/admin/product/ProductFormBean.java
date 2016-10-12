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
}
