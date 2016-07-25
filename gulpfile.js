var gulp = require("gulp"),
	rev = require("gulp-rev"),
	uglify = require("gulp-uglify"),
	less = require("gulp-less"),
	minifyCss = require("gulp-minify-css"),
	imagemin = require("gulp-imagemin"),
	pngquant = require("imagemin-pngquant"),
	gulpsync = require("gulp-sync")(gulp);

var	del = require("del"),
	fs = require("fs"),
	es = require("event-stream"),
	path = require("path");


var tomcatTemplatePath = "src/main/product/";
var staticPath = "/var/zeus/static/";
var staticUrl = "http://zeus-static-plain.zunpeng.org/";


gulp.task("default", function(cb){
	console.log("hello world");
	cb();
});


gulp.task("del", function(cb){
	del.sync("build/static/**/*", {force: true});
	del.sync("build/process/**/*", {force: true});
	console.log("delete file successfully");
	cb();
});




/**处理js*************************/

gulp.task("processCustomJs", function(cb){
	gulp.src(["src/main/resources/static/javascript/*.js",
				"src/main/resources/static/javascript/admin/*.js",
				"src/main/resources/static/javascript/portal/*.js"])
		.pipe(es.map(function(file, cb){
			console.log(file.path);
			return cb(null, file);
		}))
		.pipe(rev())
		.pipe(uglify())
		.pipe(gulp.dest("build/static/javascript"))
		.pipe(rev.manifest("js_result.json"))
		.pipe(gulp.dest("build/process/result"))
		.on("end", function(){
			console.log("copy and uglify js successfully");
			cb();
		});
});

gulp.task("processPluginJs", function(cb){
	gulp.src("src/main/resources/static/javascript/plugin/**/*")
		.pipe(es.map(function (file, cb) {
			console.log(file.path);
			return cb(null, file);
		}))
		.pipe(gulp.dest("build/static/javascript/plugin"))
		.on("end", function(){
			console.log("copy js plugin successfully");
			cb();
		});
});

gulp.task("processJs", gulpsync.sync(["processCustomJs", "processPluginJs"]));




/**处理less和css*************************/

gulp.task("processCustomLess", function(cb){
	gulp.src(["src/main/resources/static/style/*.less",
				"src/main/resources/static/style/portal/*.less",
				"src/main/resources/static/style/admin/*.less"])
		.pipe(es.map(function(file, cb){
			console.log(file.path);
			return cb(null, file);
		}))
		.pipe(less())
		.pipe(minifyCss())
		.pipe(rev())
		.pipe(gulp.dest("build/static/style"))
		.pipe(rev.manifest("less_result.json"))
		.pipe(gulp.dest("build/process/result"))
		.on("end", function(){
			console.log("minify css successfully");
			cb();
		});
});

gulp.task("processPluginCss", function(cb){
	gulp.src("src/main/resources/static/style/plugin/**/*")
		.pipe(es.map(function(file, cb){
			console.log(file.path);
			return cb(null, file);
		}))
		.pipe(gulp.dest("build/static/style/plugin"))
		.on("end", function(){
			console.log("copy plugin css successfully")
			cb();
		});
});

gulp.task("processLess", gulpsync.sync(["processCustomLess", "processPluginCss"]));





/**copy image *************************/
gulp.task("copyImage", function(cb){
	gulp.src("src/main/resources/static/image/**/*")
		.pipe(es.map(function(file, cb){
			console.log(file.path);
			return cb(null, file);
		}))
		//.pipe(imagemin({
		//	progressive: true,
		//	svgoPlugins: [{removeViewBox:false}],
		//	use: [pngquant()]
		//}))
		.pipe(gulp.dest("build/static/image"))
		.on("end", function(){
			console.log("copy image successfully");
			cb();
		});
});




/**copy thymeleaf html**/
gulp.task("copyHtml", function(cb){
	gulp.src("src/main/resources/templates/**/*")
		.pipe(es.map(function(file, cb){
			console.log(file.path);
			return cb(null, file);
		}))
		.pipe(gulp.dest("build/process/html"))
		.on("end", function(){
			console.log("copy html successfully");
			cb();
		});
});




/**update html**/
gulp.task("updateHtml", function(cb){
	var jsMappingJson = JSON.parse(fs.readFileSync("build/process/result/js_result.json", "utf-8"));
	var lessMappingJson = JSON.parse(fs.readFileSync("build/process/result/less_result.json", "utf-8"));

	gulp.src("build/process/html/**/*.html")
		.pipe(es.map(function (file, cb) {
			//console.log("---------: " + file.path);
			var htmlContent = fs.readFileSync(file.path, "utf-8");
			htmlContent = htmlContent.replace(/rel="stylesheet\/less"/, "rel=\"stylesheet\"");
			var styleOptions = htmlContent.match(/@\{\/style\/([\w\._-]*)\.less\}/g);
			var styleAdminOptions = htmlContent.match(/@\{\/style\/admin\/([\w\._-]*)\.less\}/g);
			var stylePluginOptions = htmlContent.match(/@\{\/style\/plugin\/([\/\w\._-]*)\}/g);
			var jsOptions = htmlContent.match(/@\{\/javascript\/([\w\._-]*)\.js\}/g);
			var jsPluginOptions = htmlContent.match(/@\{\/javascript\/plugin\/([\/\w\._-]*)\}/g);
			var jsAdminOptions = htmlContent.match(/@\{\/javascript\/admin\/([\w\._-]*)\.js\}/g);
			var imageOptions = htmlContent.match(/@\{\/image\/([\/\w\._-]*)\}/g);

			//删除less.min.js
			//<script type="text/javascript" th:src="@{/javascript/plugin/less.min.js}"></script>
			//<script type="text/javascript" th:src="@{/javascript/admin/lesson_list.js}"></script>
			var lessReg = new RegExp("<script.+?src=(\'|\").+?less\\..+?js.*?(\'|\").*?><\/script>", "g");
			htmlContent = htmlContent.replace(lessReg, "");

			//替换less
			for(var option in styleOptions){
				var opts = styleOptions[option].match(/@\{\/style\/([\w\._-]*)\.less\}/);

				var reg = new RegExp("@{/style/" + opts[1] + ".less}", "g");
				htmlContent = htmlContent.replace(reg, "'" + staticUrl + "style/" + lessMappingJson[opts[1] + ".css"] + "'");
			}
			for(var option in stylePluginOptions){
				var opts = stylePluginOptions[option].match(/@\{\/style\/plugin\/([\/\w\._-]*)\}/);

				var reg = new RegExp("@{/style/plugin/" + opts[1] + "}", "g");
				htmlContent = htmlContent.replace(reg, "'" + staticUrl + "style/plugin/" + opts[1] + "'");
			}
			//替换admin的less
			for(var option in styleAdminOptions){
				var opts = styleAdminOptions[option].match(/@\{\/style\/admin\/([\w\._-]*)\.less\}/);

				var reg = new RegExp("@{/style/admin/" + opts[1] + ".less}", "g");
				htmlContent = htmlContent.replace(reg, "'" + staticUrl + "style/" + lessMappingJson["admin/" + opts[1] + ".css"] + "'");
			}

			//替换js
			for(var option in jsOptions){
				var opts = jsOptions[option].match(/@\{\/javascript\/([\w\._-]*)\.js\}/);

				var reg = new RegExp("@{/javascript/" + opts[1] + ".js}", "g");
				htmlContent = htmlContent.replace(reg, "'" + staticUrl + "javascript/" + jsMappingJson[opts[1] + ".js"] + "'");
			}
			for(var option in jsPluginOptions){
				var opts = jsPluginOptions[option].match(/@\{\/javascript\/plugin\/([\/\w\._-]*)\}/);

				var reg = new RegExp("@{/javascript/plugin/" + opts[1] + "}", "g");
				htmlContent = htmlContent.replace(reg, "'" + staticUrl + "javascript/plugin/" + opts[1] + "'");
			}
			//替换admin的js
			for(var option in jsAdminOptions){
				var opts = jsAdminOptions[option].match(/@\{\/javascript\/admin\/([\w\._-]*)\.js\}/);

				var reg = new RegExp("@{/javascript/admin/" + opts[1] + ".js}", "g");
				htmlContent = htmlContent.replace(reg, "'" + staticUrl + "javascript/" + jsMappingJson["admin/" + opts[1] + ".js"] + "'");
			}


			//替换images
			for(var option in imageOptions){
				var opts = imageOptions[option].match(/@\{\/image\/([\/\w\._-]*)\}/);

				var reg = new RegExp("@{/image/" + opts[1] + "}", "g");
				htmlContent = htmlContent.replace(reg, "'" + staticUrl + "image/" + opts[1] + "'");
			}

			fs.writeFileSync(file.path, htmlContent, "utf-8");
			return cb()
		}))
		.on("end", function(){
			console.log("update html successfully");
			cb();
		});
});


/**copy src/main/resources to src/main/product**/
gulp.task("copyResourcesToProduct", function(cb){
	del.sync(tomcatTemplatePath, {force: true});
	gulp.src("src/main/resources/**/*")
		.pipe(gulp.dest(tomcatTemplatePath))
		.on("end", function(){
			console.log("copy src/main/resources to src/main/product");
			cb();
		});
	del.sync(tomcatTemplatePath + "static", {force: true});
});


/**deploy html to product**/
gulp.task("deployTemplatesToBoot", function(cb){
	del.sync(tomcatTemplatePath + "templates", {force: true});
	gulp.src("build/process/html/**/*")
		.pipe(gulp.dest(tomcatTemplatePath + "templates"))
		.on("end", function(){
			console.log("deploy html to tomcat successfully");
			cb();
		});
});


/**deploy resources to product**/
gulp.task("deployResourcesToBoot", function(cb){
	del.sync(tomcatTemplatePath + "static", {force: true});
	gulp.src("build/static/**/*")
		.pipe(gulp.dest(tomcatTemplatePath + "static"))
		.on("end", function(){
			console.log("deploy resources to tomcat successfully");
			cb();
		});
});


/**deploy static**/
gulp.task("deployResourceToCDN", function(cb){
	gulp.src("build/static/**/*")
		.pipe(gulp.dest(staticPath))
		.on("end", function(){
			console.log("deploy resources successfully");
			cb();
		});
});

gulp.task("test", gulpsync.sync(["del", "processJs", "processLess", "copyImage", "copyHtml", "updateHtml"]));
//gulp.task("deploy", gulpsync.sync(["del", "processJs", "processLess", "copyImage", "copyHtml", "updateHtml", "copyResourcesToProduct", "deployTemplatesToBoot", "deployResourcesToBoot", "deployResourceToCDN"]));
gulp.task("deploy", gulpsync.sync(["del", "processJs", "processLess", "copyImage", "copyHtml", "updateHtml", "copyResourcesToProduct", "deployTemplatesToBoot", "deployResourceToCDN"]));