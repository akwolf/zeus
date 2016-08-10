;(function($){
	var getUploaderInstance = function(buttonEle, domContainer, uploadUrl, uploaderSwfUrl){
		return new plupload.Uploader({
			runtimes : 'html5,flash,html4',
			browse_button : buttonEle, // you can pass an id...
			container: domContainer, // ... or DOM Element itself
			url : uploadUrl,
			flash_swf_url : uploaderSwfUrl,
			max_retries: 3,
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
	};

	$.zeusUpload = function($buttonEle, uploadUrl){
		var uploader = getUploaderInstance($buttonEle[0], $("body")[0], uploadUrl, $("#uploader_swf_url").val());
		uploader.init();
		return this;
	};
})(jQuery);