package org.zunpeng.domain;

import java.util.Date;

/**
 * Created by dapeng on 16/8/6.
 */
public class LessonVideoInfo extends AbstractEntity {

	private static final long serialVersionUID = 3761009039301158L;

	private String originalFileName;

	private long size;

	private String fkey;

	private String hash;

	private String mobileM3u8Key;

	private String m3u8Key;

	private long duration;

	private String coverImg;

	private Date lastModifyTime;

	private int status = 0;

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

	public String getFkey() {
		return fkey;
	}

	public void setFkey(String fkey) {
		this.fkey = fkey;
	}

	public String getMobileM3u8Key() {
		return mobileM3u8Key;
	}

	public void setMobileM3u8Key(String mobileM3u8Key) {
		this.mobileM3u8Key = mobileM3u8Key;
	}

	public String getM3u8Key() {
		return m3u8Key;
	}

	public void setM3u8Key(String m3u8Key) {
		this.m3u8Key = m3u8Key;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public enum Status {
		DEFAULT(0),

		SUCCESS(1),

		FAIL(2);

		private int id;

		Status(int id){
			this.id = id;
		}

		public int getId() {
			return id;
		}
	}
}
