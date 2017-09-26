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
		success:function(data){
			layer.close(loadLayer);
			if(data.retcode!="0000"){
				layer.alert(data.errMsg);
			}else{
				data=data.body;
				callback(data);
			}
		},
		error : function(e) {
			layer.close(loadLayer);
			layer.alert("网络忙，请稍后再试！");
		} 
    });
}

