package org.zunpeng.mybatis;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dapeng on 7/16/16.
 */
public class MyBatisMapperGenerator {

	private static Logger logger = LoggerFactory.getLogger(MyBatisMapperGenerator.class);

	public static void main(String[] args) throws Throwable {
		List<String> warnings = new ArrayList<>();
		boolean overwrite = true;
		InputStream configFileInputStream = MyBatisMapperGenerator.class.getClassLoader().getResourceAsStream("mybatis_generator_config.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFileInputStream);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(new ProgressCallback() {
			@Override
			public void introspectionStarted(int totalTasks) {
				logger.info("introspectionStarted: " + totalTasks);
			}

			@Override
			public void generationStarted(int totalTasks) {
				logger.info("generationStarted: " + totalTasks);
			}

			@Override
			public void saveStarted(int totalTasks) {
				logger.info("generationStarted: " + totalTasks);
			}

			@Override
			public void startTask(String taskName) {
				logger.info("startTask: " + taskName);
			}

			@Override
			public void done() {
				logger.info("done done done");
			}

			@Override
			public void checkCancel() throws InterruptedException {
				logger.info("checkCancel checkCancel checkCancel");
			}
		});
	}

}
