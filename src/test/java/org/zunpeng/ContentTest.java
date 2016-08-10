package org.zunpeng;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.zunpeng.core.utils.CleanContentUtils;

import java.io.File;

/**
 * Created by dapeng on 16/8/8.
 */
public class ContentTest {

	@Test
	public void demo() throws Exception {
		System.out.println(CleanContentUtils.cleanHtml(FileUtils.readFileToString(new File("/Users/dapeng/workspace/zeus/src/main/webapp/static/javascript/plugin/ueditor/index.html"))));
	}
}
