package org.zunpeng.service.upload;

import com.oldpeng.core.utils.UuidUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by dapeng on 16/8/6.
 */
@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private StaticBasicConfig staticBasicConfig;

	@Override
	public SimpleUploadFileInfo upload(MultipartFile uploadFile) throws IOException {
		SimpleUploadFileInfo simpleUploadFileInfo = new SimpleUploadFileInfo();
		simpleUploadFileInfo.setOriginalFileName(uploadFile.getOriginalFilename());
		simpleUploadFileInfo.setFileName(UuidUtils.generate() + "." + FilenameUtils.getExtension(simpleUploadFileInfo.getOriginalFileName()));
		simpleUploadFileInfo.setSize(uploadFile.getSize());
		simpleUploadFileInfo.setUrl(buildUrl(simpleUploadFileInfo.getFileName()));

		//保存文件
		uploadFile.transferTo(new File(staticBasicConfig.getStaticImgDownloadPath() + simpleUploadFileInfo.getFileName()));

		return simpleUploadFileInfo;
	}

	@Override
	public String buildUrl(String fileName) {
		return staticBasicConfig.getStaticImgDomain() + fileName;
	}
}
