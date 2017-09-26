package com.baqi.pweb.common;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;

@Aspect  
@Component  
public class RestControllerAop {  
	private static final Logger log = LoggerFactory.getLogger(RestControllerAop.class);

	//controller下的所有@RequestMapping的方法
	@Around("execution(* com.baqi.pweb.restcontroller.*.*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)") 
	public Object Interceptor(ProceedingJoinPoint pjp) throws Throwable{  
		long beginTime = System.currentTimeMillis();
//		MethodSignature signature = (MethodSignature) pjp.getSignature();
		
		ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();  
		HttpServletRequest request = sra.getRequest();
		HttpServletResponse response = sra.getResponse();
		String uri=request.getRequestURI();
		request.getServletContext().getContextPath();
		log.info("uri:{}",uri);
		
		Enumeration<String> enu=request.getParameterNames();
		StringBuilder param=new StringBuilder();
		while(enu.hasMoreElements()){
			String paraName=(String)enu.nextElement();
			param.append(paraName+"-"+request.getParameter(paraName)+";");  
		}
		log.info("param:{}",param);

		try {
			Object result = pjp.proceed(pjp.getArgs());
			JSONObject jo=new JSONObject();
			jo.put("retcode", "0000");
			jo.put("body", JSONObject.toJSON(result));
			
			response.setContentType("text/html;charset=UTF-8"); 
			PrintWriter pw=response.getWriter();
			pw.write(jo.toJSONString());
			pw.flush();
			pw.close();
			return null;
		} finally{
			long costMs = System.currentTimeMillis() - beginTime;
			log.info("{}请求结束，耗时：{}ms",uri,costMs);
		}
	}

}