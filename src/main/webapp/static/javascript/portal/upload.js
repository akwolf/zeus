;(function($){
	var getUploaderInstance = function(buttonEle, domContainer, uploadUrl){
		console.log(buttonEle + "\t" + domContainer + "\t" + uploadUrl);
		return new plupload.Uploader({
			runtimes : 'html5,flash,silverlight,html4',
			browse_button : buttonEle, // you can pass an id...
			container: domContainer, // ... or DOM Element itself
			url : uploadUrl,
			flash_swf_url : '../plugin/plupload/js/Moxie.swf',
			silverlight_xap_url : '../plugin/plupload/js/Moxie.xap',
			max_retries: 5,
			//chunk_size: "500kb",
			multi_selection: false,
			file_data_name: "uploadFile",

			filters : {
				max_file_size : '10mb',
				mime_types: [
					{title : "请选择图片", extensions : "jpg,gif,png,jpeg"}
				]
			},

			init: {
				PostInit: function() {
					console.log("post init");
				},

				FilesAdded: function(up, files) {
					console.log("file added");
					up.start();
				},

				UploadProgress: function(up, file) {
					console.log("upload progress: " + file.percent);
				},

				Error: function(up, err) {
					console.log("Error #" + err.code + ": " + err.message);
				},

				FileUploaded: function(up, file, responseObject){
					console.log(JSON.stringify(responseObject));
				}
			}
		});
	}

	$.fn.extend({
		"zeusUpload": function($buttonEle, uploadUrl){
			console.log("zeus upload")
			var uploader = getUploaderInstance($buttonEle[0], $("body")[0], uploadUrl);
			uploader.init();
			return this;
		}
	})
})(jQuery);