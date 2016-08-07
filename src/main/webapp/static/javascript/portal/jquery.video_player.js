;(function(){
	var isMobileBrowser = function() {
		return (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent));
	}

	var initPCPlayer = function($myVideo, swfUrl){
		videojs.options.flash.swf = swfUrl,
		videojs($myVideo[0], {
			techOrder: ["flash", "html5"]
		}).ready(function(){
			this.hotkeys({
				volumeStep: 0.1,
				seekStep: 5
			});
		});
	};

	var initMobilePlayer = function($myVideo){
		videojs($myVideo[0], {
			techOrder: ["html5"]
		});
	};

	$.isMobileBrowser = isMobileBrowser;
	$.initPCPlayer = initPCPlayer;
	$.initMobilePlayer = initMobilePlayer;
})(jQuery);