package com.baqi.pweb.bean;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alibaba.fastjson.JSONObject;

public class MyResEntity extends ResponseEntity<Object>{
	
	public MyResEntity() {
		super(addHeader(null),HttpStatus.OK);
	}

	public MyResEntity(Object body) {
		super(addHeader(body),HttpStatus.OK);
	}
	
	public MyResEntity(Object body,HttpStatus status) {
		super(addHeader(body),status);
	}

	private static JSONObject addHeader(Object body){
		JSONObject jo=new JSONObject();
		jo.put("retcode", "0000");
		jo.put("body", JSONObject.toJSON(body));
		return jo;
	}

	
}
