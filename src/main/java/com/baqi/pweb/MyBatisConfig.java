package com.baqi.pweb;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@MapperScan("com.baqi.pweb.dao")
public class MyBatisConfig {

	@Autowired
	private Environment env;
	
    @Bean
	@Primary
	public DataSource dataSource() throws SQLException{ 
		DruidDataSource dataSource = new DruidDataSource(); 

		dataSource.setUrl(env.getProperty("ds.url")); 
		dataSource.setUsername(env.getProperty("ds.username")); 
		dataSource.setPassword(env.getProperty("ds.password")); 
		dataSource.setDriverClassName(env.getProperty("ds.driverClassName")); 
		dataSource.setInitialSize(Integer.valueOf(env.getProperty("ds.initialSize"))); 
		dataSource.setMaxActive(Integer.valueOf(env.getProperty("ds.maxActive"))); 
		dataSource.setMaxWait(Integer.valueOf(env.getProperty("ds.maxWait"))); 
		dataSource.setMinIdle(Integer.valueOf(env.getProperty("ds.minIdle"))); 
		dataSource.setTimeBetweenEvictionRunsMillis(Integer.valueOf(env.getProperty("ds.timeBetweenEvictionRunsMillis"))); 
		dataSource.setMinEvictableIdleTimeMillis(Integer.valueOf(env.getProperty("ds.minEvictableIdleTimeMillis"))); 
		dataSource.setValidationQuery(env.getProperty("ds.minEvictableIdleTimeMillis")); 
		dataSource.setTestWhileIdle(Boolean.valueOf(env.getProperty("ds.testWhileIdle"))); 
		dataSource.setTestOnBorrow(Boolean.valueOf(env.getProperty("ds.testOnBorrow"))); 
		dataSource.setTestOnReturn(Boolean.valueOf(env.getProperty("ds.testOnReturn"))); 
		dataSource.setMaxOpenPreparedStatements(Integer.valueOf(env.getProperty("ds.maxOpenPreparedStatements"))); 
		dataSource.setFilters(env.getProperty("ds.filters")); 
		return dataSource; 
	}

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource()); // 使用titan数据源, 连接titan库
        return factoryBean.getObject();
    }
	
    @Bean  
    public TransactionTemplate transactionTemplate() throws SQLException {
    	DataSourceTransactionManager dstm=new DataSourceTransactionManager(dataSource());
        return new TransactionTemplate(dstm);  
    }  
    
}