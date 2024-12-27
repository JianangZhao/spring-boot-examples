package com.neo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloController {
	
    @RequestMapping("/test")
    public String index() {
        log.info("请求开始....");
        return "Hello Spring Boot 2.0!";
    }
}