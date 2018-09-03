package com.newsupplytech.jxsmh.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Value("${jxsmh.filePath}")
    private String filePath;
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        if(filePath.equals("") || filePath.equals("${jxsmh.filePath}")){
//            String imagesPath = WebAppConfig.class.getClassLoader().getResource("").getPath();
//            if(imagesPath.indexOf(".jar")>0){
//                imagesPath = imagesPath.substring(0, imagesPath.indexOf(".jar"));
//            }else if(imagesPath.indexOf("classes")>0){
//                imagesPath = "file:"+imagesPath.substring(0, imagesPath.indexOf("classes"));
//            }
//            imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/"))+"/images/";
//            filePath = imagesPath;
//        }
//        LoggerFactory.getLogger(WebAppConfig.class).info("filePath="+filePath);
//        registry.addResourceHandler("/images/**").addResourceLocations(filePath);
//        // TODO Auto-generated method stub
//        super.addResourceHandlers(registry);
//    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/templates/");
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:///D:/upload/");
        super.addResourceHandlers(registry);
    }
}
