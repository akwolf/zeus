package org.zunpeng.domain;

/**
 * Created by dapeng on 6/7/16.
 */
public class BannerInfo extends AbstractEntity {

	private static final long serialVersionUID = -255796195356877695L;

	private String coverImg;

	private int sequence = 0;

	private boolean deleted = false;

	private boolean disabled = false;

	private String recommendUrl;

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getRecommendUrl() {
		return recommendUrl;
	}

	public void setRecommendUrl(String recommendUrl) {
		this.recommendUrl = recommendUrl;
	}
}
