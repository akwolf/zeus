package org.zunpeng.domain;

/**
 * 产品
 * Created by dapeng on 6/7/16.
 */
public class ProductInfo extends AbstractEntity {

	private static final long serialVersionUID = -6921862457432765953L;

	private int type = 1;

	public enum Type {

		//货物
		GOODS(1),

		//打包的解决方案
		SOLUTION(2);

		private int id;

		public int getId() {
			return id;
		}

		Type(int id){
			this.id = id;
		}

	}
}
