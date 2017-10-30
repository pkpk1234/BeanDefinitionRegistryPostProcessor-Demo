package com.example.registerbean;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * AOP切面，拦截PersonManager所有public方法调用，并打印简单字符串
 * @author 李佳明 https://github.com/pkpk1234/
 * @date 2017-10-30
 */
@Component
@Aspect
@Slf4j
public class PersonManagerAspect {
    @Before("within(com.example.registerbean.PersonManager)")
    public void log() {
        log.info("invoke PersonManager");
    }
}
