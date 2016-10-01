package org.zunpeng.core.annotation;

import java.lang.annotation.*;

/**
 * Created by dapeng on 2016/10/1.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Caching {

	CacheType value();

	enum CacheType{

		READ(1),

		REMOVE(2);

		private int id;

		CacheType(int id){
			this.id = id;
		}

		public int getId() {
			return id;
		}
	}
}
