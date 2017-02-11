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
public class QiniuJHTest {

	Auth auth = Auth.create("4WsVSiezxXxOqI8YYK4TyooB5KK3eWP336ni_1Kw", "xYrSs87KsqbtNW0U6UItCPEBrbhC_FJAxAKr-Jr6");
	String fops = "avthumb/m3u8/noDomain/1/segtime/10/ab/64k/ar/44100/acodec/libfaac/r/25/vcodec/libx264/stripmeta/0";
	String key = "新婚快乐.wmv";
	String bucket = "zeus-video";
	String pipeline = "zeus-video";
	String callbackUrl = "http://www.caikuaibang.com/admin/qiniu/video/transcoding/callback";

	@Test
	public void test() throws Exception {
		String uuid = UuidUtils.generate();
		//+ "/pattern/" + UrlSafeBase64.encodeToString(uuid + "_${count}")
		String videoFops = fops + "/pattern/" + UrlSafeBase64.encodeToString(uuid + "_$(count).ts")
//				+ "/wmText/" + UrlSafeBase64.encodeToString("鑫顺门窗") + "/wmFont/" + UrlSafeBase64.encodeToString("黑体")
//				+ "/wmFontColor/" + UrlSafeBase64.encodeToString("FFFFFF") + "/wmFontSize/18"
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
		System.out.println(auth.privateDownloadUrl("http://zeus-video.zunpeng.org/bea6b4ef365c4eaead113a24a7794376.m3u8" + "?pm3u8/0/deadline/" + (new DateTime().plusHours(3).toDate().getTime() / 1000), 1200));
	}

	@Test
	public void demo3(){
		//http://zeus-video.zunpeng.org/5da6854c6d7f42c5829aa1717a5fe07a.m3u8
		System.out.println(auth.privateDownloadUrl("http://zeus-video.zunpeng.org/bea6b4ef365c4eaead113a24a7794376.m3u8" + "?pm3u8/0/deadline/" + (new DateTime().plusHours(10).toDate().getTime() / 1000), 12000));
	}

	@Test
	public void demo4(){
		//http://zeus-video.zunpeng.org/5da6854c6d7f42c5829aa1717a5fe07a.m3u8
		System.out.println(auth.privateDownloadUrl("http://zeus-video.zunpeng.org/bea6b4ef365c4eaead113a24a7794376.m3u8" + "?pm3u8/0/deadline/" + (new DateTime().plusMinutes(1).toDate().getTime() / 1000), 60));
	}

	@Test
	public void demo5(){
		System.out.println(UuidUtils.generate());
		System.out.println(SlugGenerateUtils.generate(16));
		System.out.println(UuidUtils.generate());
	}
}
