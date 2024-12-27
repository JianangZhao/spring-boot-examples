package com.neo.config;

import com.neo.filter.TraceFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;

@Slf4j
@Configuration
public class WebConfiguration {

    @Bean
    @ConditionalOnMissingBean({TraceFilter.class})
    @Order(Ordered.HIGHEST_PRECEDENCE + 101)
    public FilterRegistrationBean<TraceFilter> traceFilterFilterRegistrationBean(){
        FilterRegistrationBean<TraceFilter> bean
                = new FilterRegistrationBean<>();
        bean.setFilter(new TraceFilter());
        bean.addUrlPatterns("/*");
        return bean;
    }
}
