package org.zunpeng;

import com.alibaba.fastjson.JSONObject;
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
}
