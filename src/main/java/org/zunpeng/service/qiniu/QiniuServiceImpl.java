package org.zunpeng.service.qiniu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.oldpeng.core.utils.RequestUtils;
import com.oldpeng.core.utils.UuidUtils;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.BucketManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zunpeng.domain.LessonVideoInfo;
import org.zunpeng.mapper.LessonVideoInfoMapper;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
 * Created by dapeng on 16/8/6.
 */
@Component
public class QiniuServiceImpl implements QiniuService {

	private static Logger logger = LoggerFactory.getLogger(QiniuServiceImpl.class);

	@Value("${qiniu.access_token}")
	private String accessToken;

	@Value("${qiniu.secret_key}")
	private String secretKey;

	@Value("${qiniu.bucket}")
	private String bucket;

	@Value("${qiniu.bucket.static}")
	private String staticBucket;

	@Value("${qiniu.fops}")
	private String fops;

	@Value("${qiniu.hls_key}")
	private String hlsKey;

	@Value("${qiniu.hls_key.encrypt}")
	private String hlsKeyEncrypt;

	@Value("${qiniu.hls_key_url}")
	private String hlsKeyUrl;

	@Value("${qiniu.fops.vframe}")
	private String vframeFops;

	@Value("${qiniu.pipeline}")
	private String pipeline;

	@Value("${qiniu.callback.upload.url}")
	private String uploadCallbackUrl;

	@Value("${qiniu.callback.fops.url}")
	private String fopsCallbackUrl;

	@Value("${qiniu.callback.fops.mobile.url}")
	private String fopsMobileCallbackUrl;

	@Value("${qiniu.callback.fops.vframe.url}")
	private String vframeCallbackUrl;

	@Value("${qiniu.video.url}")
	private String videoDomain;

	@Autowired
	private LessonVideoInfoMapper lessonVideoInfoMapper;

	private Auth auth;

	private OperationManager operationManager;

	private BucketManager bucketManager;

	@PostConstruct
	public void init(){
		auth = Auth.create(accessToken, secretKey);
		operationManager = new OperationManager(auth);
		bucketManager = new BucketManager(auth);
	}

	@Override
	public String generateToken() {
		String key = UuidUtils.generate();
		StringMap paramMap = new StringMap().putNotEmpty("callbackUrl", uploadCallbackUrl)
				.putNotEmpty("callbackBody", "key=$(key)&hash=$(etag)&name=$(fname)&size=$(fsize)")
				.putNotEmpty("saveKey", key);
		return auth.uploadToken(bucket, null, 3600, paramMap);
	}

	@Override
	public SimpleQiniuFileInfo uploadCallback(QiniuUploadFormBean formBean) {
		//保存视频信息
		LessonVideoInfo lessonVideoInfo = new LessonVideoInfo();
		lessonVideoInfo.setOriginalFileName(formBean.getName());
		lessonVideoInfo.setFkey(formBean.getKey());
		lessonVideoInfo.setHash(formBean.getHash());
		lessonVideoInfo.setSize(formBean.getSize());
		lessonVideoInfo.setCreateTime(new Date());
		lessonVideoInfo.setLastModifyTime(new Date());
		lessonVideoInfoMapper.save(lessonVideoInfo);

		//获取视频分辨率信息
		List<Integer> videoInfo = retrieveVideoInfo(lessonVideoInfo.getFkey());

		//对视频进行转码处理
		if(videoInfo != null){
			int originalWidth = videoInfo.get(0);
			int originalHeight = videoInfo.get(1);
			int seconds = videoInfo.get(2);

			//更新时长信息
			lessonVideoInfo.setDuration(seconds);
			lessonVideoInfoMapper.update(lessonVideoInfo);

			String pcFops = fops;
			String mobileFops = fops;

			if(originalWidth > 1280){
				long height = Math.round(Double.valueOf(originalHeight) / Double.valueOf(originalWidth) * Double.valueOf(1280));
				pcFops = pcFops + "/s/1280x" + height;
			}

			if(originalWidth > 640){
				long height = Math.round(Double.valueOf(originalHeight) / Double.valueOf(originalWidth) * Double.valueOf(640));
				mobileFops = mobileFops + "/s/640x" + height;
			}

			fops(lessonVideoInfo.getFkey(), false, buildFinalM3u8Fops(pcFops));
			fops(lessonVideoInfo.getFkey(), true, buildFinalM3u8Fops(mobileFops));
		}

		//截取视频第五秒的画面,作为封面
		vframeFops(lessonVideoInfo.getFkey());

		SimpleQiniuFileInfo simpleQiniuFileInfo = new SimpleQiniuFileInfo();
		simpleQiniuFileInfo.setFileName(lessonVideoInfo.getFkey());
		simpleQiniuFileInfo.setOriginalFileName(lessonVideoInfo.getOriginalFileName());
		simpleQiniuFileInfo.setSize(lessonVideoInfo.getSize());

		return simpleQiniuFileInfo;
	}

	@Override
	public void fopsCallback(String qiniuRequestBody, boolean mobile) {
		QiniuFopsCallbackBean qiniuFopsCallbackBean = JSONObject.parseObject(qiniuRequestBody, QiniuFopsCallbackBean.class);

		String fkey = qiniuFopsCallbackBean.getInputKey();
		int code = qiniuFopsCallbackBean.getItems().get(0).getCode();
		String m3u8Key = qiniuFopsCallbackBean.getItems().get(0).getKey();

		if(code == 0){
			LessonVideoInfo lessonVideoInfo = lessonVideoInfoMapper.getByFkey(fkey);
			if(mobile){
				lessonVideoInfo.setMobileM3u8Key(m3u8Key);
			} else {
				lessonVideoInfo.setM3u8Key(m3u8Key);
				lessonVideoInfo.setStatus(LessonVideoInfo.Status.SUCCESS.getId());
			}

			lessonVideoInfoMapper.update(lessonVideoInfo);
		}
	}

	@Override
	public void vframeCallback(String qiniuRequestBody) {
		QiniuFopsCallbackBean qiniuFopsCallbackBean = JSONObject.parseObject(qiniuRequestBody, QiniuFopsCallbackBean.class);

		String fkey = qiniuFopsCallbackBean.getInputKey();
		int code = qiniuFopsCallbackBean.getItems().get(0).getCode();
		String coverKey = qiniuFopsCallbackBean.getItems().get(0).getKey();

		if(code == 0){
			LessonVideoInfo lessonVideoInfo = lessonVideoInfoMapper.getByFkey(fkey);
			lessonVideoInfo.setCoverImg(coverKey);
			lessonVideoInfoMapper.update(lessonVideoInfo);
		}
	}

	@Override
	public String getHlsKey() {
		return hlsKey;
	}

	private List<Integer> retrieveVideoInfo(String key){
		String privateUrl = auth.privateDownloadUrl(videoDomain + key + "?avinfo", 60 * 5);
		JSONObject videoInfoJson = null;

		for(int i = 0; i < 3; i ++){
			try {
				String videoInfoString = RequestUtils.getJson(privateUrl);
				videoInfoJson = JSONObject.parseObject(videoInfoString);
				break;
			} catch(Throwable t){
				logger.info(t.getMessage(), t);
			}
		}

		if(videoInfoJson == null){
			return null;
		} else {
			JSONArray streamsArray = videoInfoJson.getJSONArray("streams");
			JSONObject videoInfoJsonObject = null;
			for(int i = 0; i < streamsArray.size(); i ++){
				JSONObject jsonObject = streamsArray.getJSONObject(i);
				if("video".equals(jsonObject.getString("codec_type"))){
					videoInfoJsonObject = jsonObject;
				}
			}
			int originalWidth = videoInfoJsonObject.getInteger("width");
			int originalHeight = videoInfoJsonObject.getInteger("height");

			int seconds = Double.valueOf(videoInfoJson.getJSONObject("format").getString("duration")).intValue();

			List<Integer> list = Lists.newArrayList();
			list.add(originalWidth);
			list.add(originalHeight);
			list.add(seconds);
			return list;
		}
	}

	private void vframeFops(String key){
		String fops = vframeFops + "|saveas/" + UrlSafeBase64.encodeToString(staticBucket + ":" + getSnapshotKey());
		StringMap params = new StringMap().putNotEmpty("pipeline", pipeline).put("notifyURL", vframeCallbackUrl);
		for(int i = 0; i < 3; i ++){
			try {
				String persistId = operationManager.pfop(bucket, key, fops, params);
				logger.info("\t" + fops + "\t" + key + "\t" + persistId);
				break;
			} catch(Throwable t){
				logger.info(t.getMessage(), t);
			}
		}
	}

	private void fops(String key, boolean mobile, String videoFops){
		StringMap params = new StringMap().putNotEmpty("pipeline", pipeline);
		if(mobile){
			params.putNotEmpty("notifyURL", fopsMobileCallbackUrl);
		} else {
			params.putNotEmpty("notifyURL", fopsCallbackUrl);
		}

		for(int i = 0; i < 3; i ++){
			try {
				String persistId = operationManager.pfop(bucket, key, videoFops, params);
				logger.info("\t" + videoFops + "\t" + mobile + "\t" + key + "\t" + persistId);
				break;
			} catch(Throwable t){
				logger.info(t.getMessage(), t);
			}
		}
	}

	private String getSnapshotKey(){
		return UuidUtils.generate() + ".jpg";
	}

	private String buildFinalM3u8Fops(String fops){
		fops = fops + "/hlsKey/" + hlsKeyEncrypt + "/hlsKeyType/1.0/hlsKeyUrl/" + UrlSafeBase64.encodeToString(hlsKeyUrl);
		String m3u8Key = UuidUtils.generate() + ".m3u8";
		return fops + "|saveas/" + UrlSafeBase64.encodeToString(bucket + ":" + m3u8Key);
	}
}
