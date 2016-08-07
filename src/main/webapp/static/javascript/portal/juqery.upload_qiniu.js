;(function($){
	var getUploaderInstance = function(buttonEle, domContainer, uptokenUrl){
		return Qiniu.uploader({
			runtimes: 'html5,flash,html4',      // 上传模式，依次退化
			browse_button: buttonEle,         // 上传选择的点选按钮，必需
			uptoken_url: uptokenUrl,
			get_new_uptoken: true,             // 设置上传文件的时候是否每次都重新获取新的uptoken
			domain: 'http://zeus-video.zunpeng.org',     // bucket域名，下载资源时用到，必需
			container: domContainer,             // 上传区域DOM ID，默认是browser_button的父元素
			max_file_size: '1000mb',             // 最大文件体积限制
			flash_swf_url: '../plugin/plupload/js/Moxie.swf',  //引入flash，相对路径
			max_retries: 3,                     // 上传失败最大重试次数
			chunk_size: '2mb',                  // 分块上传时，每块的体积
			auto_start: true,                   // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
			multi_selection: false,

			filters : {
				max_file_size : '1000mb',
				mime_types: [
					{title : "请选择视频", extensions : "mp4,mov,avi,flv,rm,rmvb,wmv"}
				],
				prevent_duplicates : true
			},

			init: {
				'FilesAdded': function(up, files) {
					console.log("file added");
				},
				'BeforeUpload': function(up, file) {
					console.log("before upload")
				},
				'UploadProgress': function(up, file) {
					console.log("upload progress: " + file.percent);
				},
				'FileUploaded': function(up, file, info) {
					console.log(JSON.stringify(info));
				},
				'Error': function(up, err, errTip) {
					console.log("Error #" + err.code + ": " + err.message + "\t: " + errTip);
				},
				'UploadComplete': function() {
					console.log("upload complete");
				}
			}
		});
	};

	$.zeusQiniuUpload = function($buttonEle, uptokenUrl){
		console.log("zeus upload")
		getUploaderInstance($buttonEle[0], $("body")[0], uptokenUrl);
	};
})(jQuery);