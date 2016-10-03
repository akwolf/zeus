package org.zunpeng.core.annotation;

import java.lang.annotation.*;

/**
 * Created by dapeng on 2016/10/1.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Caching {

	/**
	 * 执行的操作是什么 插入还是删除
	 * @return
	 */
	OperatorType operator();

	/**
	 * 依赖的类其他Mapper类型
	 * @return
	 */
	Class<?>[] deps() default {};

	enum OperatorType {

		/**
		 * 增加单个对象是，清除部分缓存
		 */
		SAVE(1),

		/**
		 * 更新单个对象数据是，清除部分缓存
		 */
		UPDATE(2),

		/**
		 * 清除所有缓存，暂时未使用
		 */
		CLEAR(3),

		/**
		 * getById or getBySlug 方法上使用
		 */
		GET_SINGLE(4);

		private int id;

		OperatorType(int id){
			this.id = id;
		}

		public int getId() {
			return id;
		}
	}
}
