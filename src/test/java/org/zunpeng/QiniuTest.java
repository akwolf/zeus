package org.zunpeng;

import com.alibaba.fastjson.JSONObject;
import com.oldpeng.core.utils.UuidUtils;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.BucketManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import org.joda.time.DateTime;
import org.junit.Test;
import org.zunpeng.core.utils.SlugGenerateUtils;

/**
 * Created by dapeng on 16/8/6.
 */
public class QiniuTest {

	Auth auth = Auth.create("HLSXTk-f9pJDJhDFX-a6gcVcxuKDKvqaUmmhkQwE", "Gs9_cC96ttqseb50gpqe0sHNE34PCoKmvHzjy2pW");
	String fops = "avthumb/m3u8/noDomain/1/segtime/10/ab/64k/ar/44100/acodec/libfaac/r/25/vcodec/libx264/stripmeta/0";
	String key = "achaijing.mp4";
	String bucket = "zeus-video";
	String pipeline = "zeus-video";
	String callbackUrl = "http://www.caikuaibang.com/admin/qiniu/video/transcoding/callback";

	@Test
	public void test() throws Exception {
		String uuid = UuidUtils.generate();
		//+ "/pattern/" + UrlSafeBase64.encodeToString(uuid + "_${count}")
		String videoFops = fops + "/pattern/" + UrlSafeBase64.encodeToString(uuid + "_$(count).ts")
				+ "/wmText/" + UrlSafeBase64.encodeToString("鑫顺门窗") + "/wmFont/" + UrlSafeBase64.encodeToString("黑体")
				+ "/wmFontColor/" + UrlSafeBase64.encodeToString("FFFFFF") + "/wmFontSize/18"
				+ "/hlsKey/ePVFF-md__AB32Lx1LHP_FFqbUlbhqNoxaaURVABqorZIXvyJyeBXzNh3hdOa29twdF6nyyxVSXbBg36BFQtpbPYImYHqSCH64GXcc9RGeYMiSRKMMYUkZGC_cvzjOBBeg2xqcc-WxxGQJIvTn785PbIyKrfooKP8z3hD55XuQQ="
				+ "/hlsKeyUrl/" + UrlSafeBase64.encodeToString("http://o83kjuoml.bkt.clouddn.com/key2.htm")
				+ "/hlsKeyType/1.0"
				+ "|saveas/" + UrlSafeBase64.encodeToString(bucket + ":" + uuid + ".m3u8");
		StringMap params = new StringMap().putNotEmpty("pipeline", pipeline).put("notifyURL", callbackUrl);

		BucketManager bucketManager = new BucketManager(auth);
		System.out.println(JSONObject.toJSONString(bucketManager.stat(bucket, key)));

		OperationManager operationManager = new OperationManager(auth);
		String pid = operationManager.pfop(bucket, key, videoFops, params);
		System.out.println(pid);
	}

	@Test
	public void demo2(){
		//http://zeus-video.zunpeng.org/5da6854c6d7f42c5829aa1717a5fe07a.m3u8
		System.out.println(auth.privateDownloadUrl("http://zeus-video.zunpeng.org/5da6854c6d7f42c5829aa1717a5fe07a.m3u8" + "?pm3u8/0/deadline/" + (new DateTime().plusHours(3).toDate().getTime() / 1000), 1200));
	}

	@Test
	public void demo3(){
		//http://zeus-video.zunpeng.org/5da6854c6d7f42c5829aa1717a5fe07a.m3u8
		System.out.println(auth.privateDownloadUrl("http://zeus-video.zunpeng.org/94485420ba524bfe97ce23bb23e9708e.m3u8" + "?pm3u8/0/deadline/" + (new DateTime().plusHours(100).toDate().getTime() / 1000), 120000));
	}

	@Test
	public void demo4(){
		//http://zeus-video.zunpeng.org/5da6854c6d7f42c5829aa1717a5fe07a.m3u8
		System.out.println(auth.privateDownloadUrl("http://zeus-video.zunpeng.org/5da6854c6d7f42c5829aa1717a5fe07a.m3u8" + "?pm3u8/0/deadline/" + (new DateTime().plusMinutes(1).toDate().getTime() / 1000), 60));
	}

	@Test
	public void demo5(){
		System.out.println(UuidUtils.generate());
		System.out.println(SlugGenerateUtils.generate(16));
	}
}
