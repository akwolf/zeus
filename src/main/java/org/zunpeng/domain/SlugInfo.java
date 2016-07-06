package org.zunpeng.domain;

import java.io.Serializable;

/**
 * Created by dapeng on 6/7/16.
 */
public class SlugInfo implements Serializable {

	private static final long serialVersionUID = 1837784052647697475L;

	private Long id;

	private String slug;

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
