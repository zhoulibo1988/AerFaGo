(function($){
	var fileIndex = 1;
	$.fn.h5upload = function(opts,fileId,param){
		if(!$.isPlainObject(opts)){
			if(opts=="cancel"){
				var xhrs = $(this).data("xhrs");
				if(xhrs){
					if(fileId){
						var xhr = xhrs[fileId];
						xhr.cancel = true;
						xhr.xhr.abort();
					}else{
						$.each(xhrs,function(i,xhr){
							xhr.cancel = true;
							xhr.xhr.abort();
						});
					}
					
				}
			}
			if(opts=="settings"&&fileId&&param){
				if($(this).data("options")[fileId]){
					$(this).data("options")[fileId] = param;
				}
			}
			return;
		}
		var defaults = {
			fileTypeExts:'',// 允许上传的文件类型 用,隔开 jpg,png
			url:'',// 文件提交的地址
			auto:true,// 自动上传
			multi:true,// 默认允许选择多个文件
			formData:{},// post数据
			fileObjName:'',
			fileSizeLimit:5*1024*1024,
			queueSizeLimit:100,
			onSelect:function(){return true;},//选择文件后的动作
			onUploadStart:function(){},// 上传开始时的动作
			onUploadProgress:function(){},//进度监控
			onUploadSuccess:function(){},// 上传成功的动作
			onUploadComplete:function(){},// 上传完成的动作
			onUploadError:function(){}, // 上传失败的动作
			onInit:function(){},// 初始化时的动作
			onCancel:function(){}//取消文件上传时的动作
		}
		var option = $.extend(defaults,opts);
		var _this = $(this);
		//已经绑定过了只更新options
		if(_this.next("form.h5upload").size()>0){
			$.extend(_this.data("options"),option);
			return;
		}
		_this.data("options",option);
		var inputForm = $('<form class="h5upload" style="display:none;"><input type="file"/></form>');
		var input = inputForm.find("input[type='file']");
		if(option.multi){
			input.attr("multiple","");
		}
		input.change(function(){
			var files = this.files;
			if(!option.onSelect(files)){
				return;
			}
			var fileUploadCount = 0;
			if(files.length>option.queueSizeLimit){
				alert("选择的文件数量不能大于"+option.queueSizeLimit+"个！");
				return;
			}
			$.each(files,function(i,file){
				file.id = "H5upload_File_"+(fileIndex++);
				file.suffix = file.name.indexOf(".")>-1?file.name.substring(file.name.lastIndexOf(".")):"";

				if(option.auto){
					if(option.fileTypeExts){
						var fileTypes = option.fileTypeExts.split(",");
						fileTypes = $.map(fileTypes,function(n){
							return n.toUpperCase();
						});
						var result = false;
						if(file.name.indexOf(".")>-1){
							var fileType = file.name.substring(file.name.lastIndexOf(".")+1).toUpperCase();
							result = $.inArray(fileType,fileTypes)>-1;
						}
						if(!result){
							var warn = "";
							$.each(fileTypes,function(i,type){
								if(warn!=""){
									warn+=",";
								}
								warn+=type;
							});
							alert("请选择"+warn+"类型的文件！");
							return false;
						}
					}
					if(file.size>option.fileSizeLimit){
						function formatFileSize(size){
							if (size> 1024 * 1024){
								size = (Math.round(size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
							}
							else{
								size = (Math.round(size * 100 / 1024) / 100).toString() + 'KB';
							}
							return size;
						}
						alert("单个文件大小不能超过"+formatFileSize(option.fileSizeLimit)+"！");
						return false;
					}
					var xhr = new XMLHttpRequest();
					if(!_this.data("xhrs")){
						_this.data("xhrs",{});
					}
					_this.data("xhrs")[file.id]={xhr:xhr,cancel:false};
					if (xhr.upload) {
						// 文件上传成功或是失败
						xhr.onreadystatechange = function(e) {
							if (xhr.readyState == 4) {
								if(_this.data("xhrs")[file.id].cancel){
									option.onCancel(file);
								}else{
									if (xhr.status == 200) {
										option.onUploadSuccess(file,xhr.responseText);
									} else {
										option.onUploadError(file);
									}
								}
								option.onUploadComplete(file);
								//如果所有文件都上传完了 清空上次选择的文件防止重复选择同一个文件不生效
								if(++fileUploadCount==files.length){
									inputForm.get(0).reset();
								}
							}
						};
						//进度监听
						xhr.upload.addEventListener("progress",function(e){
							option.onUploadProgress(file,e.loaded,e.total);
						}, false);
//						var boundary = "--multipartboundary"+new Date().getTime();
						// 开始上传
						option.onUploadStart(file);
						xhr.open("POST",option.url,true);
						alert("AA");
						xhr.setRequestHeader('content-type', 'multipart/form-data; boundary=' + boundary);
	  				    xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
	  				    var form = new FormData();
	  				    $.each(option.formData,function(k,v){
	  				    	form.append(k,v);
	  				    });
	  				    form.append(option.fileObjName,file);
	  				    xhr.send(form);
	  				    /*
	  				    if(!xhr.sendAsBinary){
		  		            xhr.sendAsBinary = function(datastr) {
			  		            function byteValue(x) {
			  		            	return x.charCodeAt(0) & 0xff;
			  		            }
			  		            var ords = Array.prototype.map.call(datastr, byteValue);
			  		            var ui8a = new Uint8Array(ords);
				  		        if ('ArrayBufferView' in window)
				  		        	this.send(ui8a);
				  		        else
				  		        	this.send(ui8a.buffer);
		  		            }
	  		            }
						xhr.sendAsBinary(getBuilder(file.name,this.result,file.type,boundary)); */
					}
			    }
			
				/*
				reader.readAsBinaryString(file);
				reader.onloadend = function(){
					if(option.auto){
						if(option.fileTypeExts){
							var fileTypes = option.fileTypeExts.split(",");
							fileTypes = $.map(fileTypes,function(n){
								return n.toUpperCase();
							});
							var result = false;
							if(file.name.indexOf(".")>-1){
								var fileType = file.name.substring(file.name.lastIndexOf(".")+1).toUpperCase();
								result = $.inArray(fileType,fileTypes)>-1;
							}
							if(!result){
								var warn = "";
								$.each(fileTypes,function(i,type){
									if(warn!=""){
										warn+=",";
									}
									warn+=type;
								});
								alert("请选择"+warn+"类型的文件！");
								return;
							}
						}
						if(file.size>option.fileSizeLimit){
							function formatFileSize(size){
								if (size> 1024 * 1024){
									size = (Math.round(size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
								}
								else{
									size = (Math.round(size * 100 / 1024) / 100).toString() + 'KB';
								}
								return size;
							}
							alert("文件大小不能超过"+formatFileSize(option.fileSizeLimit)+"！");
							return;
						}
						option.onSelect(file);
						var xhr = new XMLHttpRequest();
						if (xhr.upload) {
							// 文件上传成功或是失败
							xhr.onreadystatechange = function(e) {
								if (xhr.readyState == 4) {
									if (xhr.status == 200) {
										option.onUploadSuccess(file,xhr.responseText);
									} else {
										option.onUploadError(file);
									}
									file.value="";
									option.onUploadComplete();
								}
							};
							//进度监听
							xhr.upload.addEventListener("progress",function(e){
								option.onUploadProgress(file,e.loaded,e.total);
							}, false);
							var boundary = "--multipartboundary"+new Date().getTime();
							// 开始上传
							option.onUploadStart(file);
							xhr.open("POST",option.url,true);
//							xhr.setRequestHeader('content-type', 'multipart/form-data; boundary=' + boundary);
		  				    xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
		  				    var form = new FormData();
		  				    $.each(option.data,function(k,v){
		  				    	form.append(k,v);
		  				    });
		  				    form.append(option.fileObjName,file);
		  				    xhr.send(form);
		  				    
		  				    if(!xhr.sendAsBinary){
			  		            xhr.sendAsBinary = function(datastr) {
				  		            function byteValue(x) {
				  		            	return x.charCodeAt(0) & 0xff;
				  		            }
				  		            var ords = Array.prototype.map.call(datastr, byteValue);
				  		            var ui8a = new Uint8Array(ords);
					  		        if ('ArrayBufferView' in window)
					  		        	this.send(ui8a);
					  		        else
					  		        	this.send(ui8a.buffer);
			  		            }
		  		            }
							xhr.sendAsBinary(getBuilder(file.name,this.result,file.type,boundary)); 
						}
				    }	 
				}*/
			});
		});
		_this.click(function(e){
			input.click();
			e.preventDefault();
		});
		_this.after(inputForm);
		// 初始化完成
		option.onInit();
		
		function getBuilder(filename, filedata, mime, boundary) {
	      var dashdash = '--',
	          crlf = '\r\n',
	          builder = '',
	          paramname = option.fileObjName;

	      if (opts.data) {
	        var params = $.param(opts.data).replace(/\+/g, '%20').split(/&/);

	        $.each(params, function() {
	          var pair = this.split("=", 2),
	              name = decodeURIComponent(pair[0]),
	              val  = decodeURIComponent(pair[1]);

	          if (pair.length !== 2) {
	              return;
	          }

	          builder += dashdash;
	          builder += boundary;
	          builder += crlf;
	          builder += 'Content-Disposition: form-data; name="' + name + '"';
	          builder += crlf;
	          builder += crlf;
	          builder += val;
	          builder += crlf;
	        });
	      }

	      if (jQuery.isFunction(paramname)){
	        paramname = paramname(filename);
	      }

	      builder += dashdash;
	      builder += boundary;
	      builder += crlf;
	      builder += 'Content-Disposition: form-data; name="' + (paramname||"") + '"';
	      builder += '; filename="' + encodeURIComponent(filename) + '"';
	      builder += crlf;

	      builder += 'Content-Type: ' + mime;
	      builder += crlf;
	      builder += crlf;

	      builder += filedata;
	      builder += crlf;

	      builder += dashdash;
	      builder += boundary;
	      builder += dashdash;
	      builder += crlf;
	      return builder;
	    }
	}
	
})(jQuery);