package com.baqi.pweb;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@Configuration
public class MyWebConfig extends WebMvcConfigurerAdapter{

	@Value("${doc.path}")
    private String docPath;
	
	//使用fastjson
	@Override
	public void configureMessageConverters(  
			List<HttpMessageConverter<?>> converters) {  
		super.configureMessageConverters(converters);  
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();  

		FastJsonConfig fastJsonConfig = new FastJsonConfig();  
		fastJsonConfig.setSerializerFeatures(  
			SerializerFeature.PrettyFormat  
		);  
		fastConverter.setFastJsonConfig(fastJsonConfig);  

		converters.add(fastConverter);  
	}   

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+docPath);
        super.addResourceHandlers(registry);
    }
	
	//过滤带后缀的请求
	//http://www.jianshu.com/p/02bff08fcced
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(false).
			setUseTrailingSlashMatch(true);
	}

}
