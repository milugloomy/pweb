function post2SRV(callUrl,formData,callback,dataType){
	var loadLayer=layer.load(1,{shade: [0.5,'#fff']});
	if(typeof dataType=='undefined'){
		dataType=callback;
		callback=formData;
		formData=null;
	}
	$.ajax({
		url:callUrl,
		data:formData,    
		type:'post',
		async:true,
		dataType:dataType,    
		success:function(data, status, xhr){
			layer.close(loadLayer);
			if(data.retcode!="0000"){
				layer.alert(data.errMsg);
			}else{
				data=data.body;
				callback(data);
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			layer.close(loadLayer);
			layer.alert("网络忙，请稍后再试！");
		} 
    });
}
function getQueryString(name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) 
		return unescape(r[2]); 
	return null; 
} 
function getCookie(name) { 
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return unescape(arr[2]); 
    else 
        return null; 
} 