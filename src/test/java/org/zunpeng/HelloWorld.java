package org.zunpeng;

import com.alibaba.fastjson.JSONObject;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.zunpeng.core.page.PageWrapper;

/**
 * Created by dapeng on 7/6/16.
 */
public class HelloWorld {

	@Test
	public void demo(){
		PageRequest pageRequest = new PageRequest(20, 20);
		PageWrapper pageWrapper = new PageWrapper(null, pageRequest, 1000);
		System.out.println(JSONObject.toJSONString(pageWrapper, true));
	}

	@Test
	public void demo2(){
		System.out.println(new DateTime(1467852957000l).toString("yyyy-MM-dd HH:mm:ss"));
	}
}
