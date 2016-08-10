package org.zunpeng.service.editor;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by dapeng on 16/8/7.
 */
@Service
public class UEditorServiceImpl implements UEditorService {

	@Override
	public JSONObject config() throws IOException {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("ueditor_config.json");
		String configString = IOUtils.toString(inputStream, "UTF-8");
		configString = configString.replaceAll("/\\*[\\s\\S]*?\\*/", "");
		return JSONObject.parseObject(configString);
	}
}
