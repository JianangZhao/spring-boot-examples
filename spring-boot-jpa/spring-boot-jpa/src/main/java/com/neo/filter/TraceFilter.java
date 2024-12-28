package com.neo.filter;

import com.neo.util.TraceIdUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class TraceFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Init Trace Filter init...");
//        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try{
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            String gatewayTraceId = servletRequest.getHeader(TraceIdUtil.TRACE_ID);
            TraceIdUtil.generateTraceId(gatewayTraceId);
            //将请求和应答交给下一个方法处理
            filterChain.doFilter(request, response);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
          TraceIdUtil.removeTraceId();
        }

    }
    @Override
    public void destroy() {
        log.info("Init Trace Filter destroy...");
//        Filter.super.destroy();
    }
}
