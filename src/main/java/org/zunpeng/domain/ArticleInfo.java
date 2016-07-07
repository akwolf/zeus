package org.zunpeng.domain;

import java.util.Date;

/**
 * 新闻中心
 * Created by dapeng on 6/7/16.
 */
public class ArticleInfo extends AbstractEntity {

	private static final long serialVersionUID = 8368755101897251261L;

	private String slug;

	private String title;

	private String description;

	private String content;

	private Date lastModifyTime;

	private boolean disabled = false;

	private boolean deleted = false;

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
