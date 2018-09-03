package com.newsupplytech.jxsmh;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.newsupplytech.jxsmh.common.filter.AccessRecorderFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@MapperScan("com.newsupplytech.jxsmh.mapper")
public class JxsmhApplication extends SpringBootServletInitializer {

    @Bean //@Bean会将方法的返回对象在sringboot启动的时候放入ioc容器
    public FilterRegistrationBean filterRegiste(){
        FilterRegistrationBean regFilter = new FilterRegistrationBean();
        regFilter.setFilter(new AccessRecorderFilter());//注册并创建AccessRecorderFilter
        regFilter.addUrlPatterns("/*");//对所有请求拦截
        regFilter.setName("AccessRecorder");//过滤器名字
        regFilter.setOrder(1);//如果系统有多个拦截器。setOrder决定哪个先执行，数字越小越靠前

        return regFilter;
    }

    @Override//为了打包springboot项目
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }

    public static void main(String[] args) {
        SpringApplication.run(JxsmhApplication.class, args);
    }
}
