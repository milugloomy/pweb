package com.baqi.pweb.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alibaba.fastjson.JSONObject;

//异常捕获类
@ControllerAdvice
public class MyExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(MyExceptionHandler.class);

	@Autowired
	private Properties errConfig;
	
	@ExceptionHandler(value = Exception.class)
    public void defaultErrorHandler(HttpServletResponse response, Exception e) throws IOException  {
		log.error(e.getMessage());
		log.error("trace:",e);
		JSONObject jo=new JSONObject();
		
		if(e instanceof BaqiException){
			BaqiException be=(BaqiException)e;
			jo.put("retcode", be.getCode());
			String errMsg=errConfig.getProperty(be.getCode());
			if(errMsg==null)
				errMsg=errConfig.getProperty("error");
			jo.put("errMsg", errMsg);
		}else{
			jo.put("retcode", "error");
			jo.put("errMsg", errConfig.getProperty("error"));
		}
		
		response.setContentType("text/html;charset=UTF-8"); 
		PrintWriter pw=response.getWriter();
		pw.write(jo.toJSONString());
		pw.flush();
		pw.close();
	}
	
}
