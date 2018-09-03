package com.newsupplytech.jxsmh.common.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

public class AccessRecorderFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(AccessRecorderFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String url = request.getRequestURI();
        if(url.endsWith(".css") || url.endsWith(".gif") || url.endsWith(".ico") || url.endsWith(".js") || url.endsWith(".jpg") || url.endsWith(".map") || url.endsWith(".png") ){
            filterChain.doFilter(servletRequest,servletResponse);//请求送到contrller
            return;
        }
        String ua = request.getHeader("user-agent");
        String ip = request.getRemoteAddr();

        Long st = new Date().getTime();
        filterChain.doFilter(servletRequest,servletResponse);//请求送到contrller
        Long et = new Date().getTime();
        logger.info("url:{},ip:{},time:{} ms,ua:{},",url,ip,(et-st),ua);
    }

    @Override
    public void destroy() {

    }
}
