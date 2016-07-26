//package org.zunpeng;
//
//import com.alibaba.fastjson.JSONObject;
//import org.joda.time.DateTime;
//import org.junit.Test;
//import org.springframework.data.domain.PageRequest;
//import org.zunpeng.core.mybatis.Criterion;
//import org.zunpeng.core.page.PageWrapper;
//
//import java.util.UUID;
//
///**
// * Created by dapeng on 7/6/16.
// */
//public class HelloWorld {
//
//	@Test
//	public void demo(){
//		PageRequest pageRequest = new PageRequest(20, 20);
//		PageWrapper pageWrapper = new PageWrapper(pageRequest, null, 1000);
//		System.out.println(JSONObject.toJSONString(pageWrapper, true));
//	}
//
//	@Test
//	public void demo2(){
//		System.out.println(new DateTime(1467852957000l).toString("yyyy-MM-dd HH:mm:ss"));
//	}
//
//	@Test
//	public void demo3(){
//		Criterion criterion = new Criterion("id", "gt", 1);
//		System.out.println(JSONObject.toJSONString(criterion));
//	}
//
//	@Test
//	public void demo4(){
//		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
//	}
//}
