package org.zunpeng.service.qiniu;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dapeng on 16/8/6.
 */
public class QiniuFopsCallbackBean implements Serializable {

	private static final long serialVersionUID = 4491694434041420094L;

	private String id;

	private int code;

	private String desc;

	private String inputKey;

	private String inputBucket;

	private String pipeline;

	private String reqid;

	private List<PersistentOps> items;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getInputKey() {
		return inputKey;
	}

	public void setInputKey(String inputKey) {
		this.inputKey = inputKey;
	}

	public String getInputBucket() {
		return inputBucket;
	}

	public void setInputBucket(String inputBucket) {
		this.inputBucket = inputBucket;
	}

	public List<PersistentOps> getItems() {
		return items;
	}

	public void setItems(List<PersistentOps> items) {
		this.items = items;
	}

	public String getPipeline() {
		return pipeline;
	}

	public void setPipeline(String pipeline) {
		this.pipeline = pipeline;
	}

	public String getReqid() {
		return reqid;
	}

	public void setReqid(String reqid) {
		this.reqid = reqid;
	}

	public static class PersistentOps {

		private String cmd;

		private int code;

		private String desc;

		private String error;

		private String hash;

		private String key;

		private int returnOld;

		public String getCmd() {
			return cmd;
		}

		public void setCmd(String cmd) {
			this.cmd = cmd;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

		public String getHash() {
			return hash;
		}

		public void setHash(String hash) {
			this.hash = hash;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public int getReturnOld() {
			return returnOld;
		}

		public void setReturnOld(int returnOld) {
			this.returnOld = returnOld;
		}
	}
}
