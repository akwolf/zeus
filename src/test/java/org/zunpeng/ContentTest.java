package org.zunpeng;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.zunpeng.core.utils.CleanContentUtils;
import org.zunpeng.domain.SlugInfo;

import java.io.File;

/**
 * Created by dapeng on 16/8/8.
 */
public class ContentTest {

	@Test
	public void demo() throws Exception {
		System.out.println(CleanContentUtils.cleanHtml(FileUtils.readFileToString(new File("/Users/dapeng/workspace/zeus/src/main/webapp/static/javascript/plugin/ueditor/index.html"))));
	}

	@Test
	public void demo2(){
		SlugInfo slugInfo = new SlugInfo();
		slugInfo.setId(1L);
		slugInfo.setSlug("hello world");
		SlugInfo slugInfo2 = new SlugInfo();
		slugInfo2.setId(1L);
		slugInfo2.setSlug("hello world");
		System.out.println(JSONObject.toJSONString(slugInfo));
		System.out.println(JSONObject.toJSONString(slugInfo2));
	}
}
