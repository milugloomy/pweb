package com.baqi.pweb;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@SpringBootApplication
public class Starter {
	
	Logger log=LoggerFactory.getLogger(Starter.class);
	public static void main(String[] args) {
		SpringApplication.run(Starter.class, args);
	}
	
	//登录过滤器,暂时注释
	@Bean
    public FilterRegistrationBean myFilter1() {  
        FilterRegistrationBean f = new FilterRegistrationBean(new Filter(){
			public void init(FilterConfig filterConfig) throws ServletException {}

			public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
					throws IOException, ServletException {
				HttpServletRequest request=(HttpServletRequest)servletRequest;
				HttpServletResponse response=(HttpServletResponse)servletResponse;
				HttpSession session=request.getSession();
				
				String uri=request.getRequestURI();
				
				if(needLogin(session, uri)){
					response.sendRedirect("/pweb/login.html?service="+uri);
				}else{
					chain.doFilter(servletRequest, servletResponse);
				}
			}
			private boolean needLogin(HttpSession session,String uri){
				//静态资源不拦截
				if(uri.endsWith(".js") || uri.endsWith(".gif") || uri.endsWith("jpg")
						|| uri.endsWith(".png") || uri.endsWith(".css") || uri.endsWith("ico") )
					return false;
				//登录交易不拦截
				if(uri.equals("/pweb/doLogin") || uri.equals("/pweb/isLogin") || uri.equals("/pweb/login.html"))
					return false;
				//政府采购不拦截
				if(uri.equals("/pweb/zfcg.html") || uri.equals("/pweb/showAll") || uri.equals("/pweb/lastWeek"))
					return false;
				//其他的判断session
				if(session.getAttribute("user")==null)
					return true;
				return false;
			}
			public void destroy() {}
		});
        f.addUrlPatterns("/*");  
        f.setName("myFilter1");
        return f;  
    }
	//druid过滤器
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean f = new FilterRegistrationBean();
		f.setFilter(new WebStatFilter());
		f.addUrlPatterns("/*");
		f.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		f.addInitParameter("profileEnable", "true");
		f.addInitParameter("principalCookieName", "USER_COOKIE");
		f.addInitParameter("principalSessionName", "USER_SESSION");
		f.setName("druidFilter");
		return f;
	}
	//druid servlet
	@Bean
	public ServletRegistrationBean druidServlet() {
		ServletRegistrationBean reg = new ServletRegistrationBean();
		reg.setServlet(new StatViewServlet());
		reg.addUrlMappings("/druid/*");
		reg.addInitParameter("loginUsername", "druid");
		reg.addInitParameter("loginPassword", "123456");
		return reg;
	}
	
	//初始化bean读取err.properties
    @Bean
    public Properties errConfig() throws IOException {
    	URL u=ResourceUtils.getURL("classpath:err.properties");
    	InputStream stream=u.openStream();
    	Properties pro = new Properties();
    	InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(stream,"UTF-8");
	    	pro.load(isr);
		} finally{
			isr.close();
		}
        return pro;
    }

	
}
