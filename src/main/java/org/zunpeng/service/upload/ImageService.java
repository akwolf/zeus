package org.zunpeng.service.upload;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by dapeng on 16/8/6.
 */
public interface ImageService {

	SimpleUploadFileInfo upload(MultipartFile uploadFile) throws IOException;

	String buildUrl(String fileName);
}
