package com.newsupplytech.jxsmh.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置druid数据源
 *
 */
@Configuration
public class DruidConfiguration {

    @Bean //监听获取应用数据，filter用于收集，servlet用于展现数据
    public FilterRegistrationBean webstatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());//设置过滤器
        bean.addUrlPatterns("/*");//对所有请求拦截
        //排除静态资源.
        Map<String,String> param = new HashMap<String,String>();
        param.put("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        bean.setInitParameters(param);
        return bean;
    }

    @Bean  //注册后台servlet界面,用于显示后台界面
    public ServletRegistrationBean statViewServlet(){
        //创建StatViewServlet，绑定到/druid/路径下
        //开启后。访问localhost/druid就可以访问管理后台
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        Map<String,String> param = new HashMap<String,String>();
        param.put("loginUsername","admin");
        param.put("loginPassword","123456");
        param.put("allow","");//代表所有地址可以访问
        //param.put("deny","33.33.33.33");//不允许这个ip访问
        param.put("resetEnable","false");//不允许这个ip访问
        bean.setInitParameters(param);
        return bean;
    }

    @Bean //手动初始化DruidDataSource对象
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druid(){
        DruidDataSource ds = new DruidDataSource();
        return ds;
    }
}