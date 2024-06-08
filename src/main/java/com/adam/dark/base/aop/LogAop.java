package com.adam.dark.base.aop;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author VAIO-adam
 */
@Slf4j
@Aspect
@Component
public class LogAop {

    /**
     * JSON转换器
     */
    @Resource
    private ObjectMapper objectMapper;

    /**
     * 进入方法时间戳
     */
    private Long beginTime;

    public LogAop() {
    }

    /**
     * 自定义切点
     */
    private static final String POINTCUT = "execution(* com.adam.dark..*Controller.*(..))";

    /**
     * 前置通知，方法之前执行
     *
     * @param joinPoint
     */
    @Before(POINTCUT)
    public void doBefore(JoinPoint joinPoint) {
        // 获取当前的HttpServletRequest对象
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 获取请求头中的User-Agent
        UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        // 打印请求的内容
        beginTime = System.currentTimeMillis();
        log.info("请求开始时间：{}", LocalDateTime.now());
        log.info("请求Url : {}", request.getRequestURL().toString());
        log.info("请求方式 : {}", request.getMethod());
        log.info("请求ip : {}", request.getRemoteAddr());

        log.info("请求接口方法 : {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("请求内容类型 : {}", request.getContentType());
        log.info("请求参数 : {}", Arrays.toString(joinPoint.getArgs()));
        // 系统信息
        log.info("浏览器 : {}", userAgent.getBrowser().toString());
        log.info("浏览器版本 : {}", userAgent.getVersion());
        log.info("浏览器引擎 : {}", userAgent.getEngine());
        log.info("浏览器引擎版本 : {}", userAgent.getEngineVersion());
        log.info("操作系统: {}", userAgent.getOs().toString());
        log.info("操作系统版本 : {}", userAgent.getOsVersion());
    }

    @After(POINTCUT)
    public void doAfter(JoinPoint joinPoint) {
//    System.out.println("doAfter");
    }

    /**
     * 返回通知 正常结束时进入此方法
     *
     * @param result
     */
    @SneakyThrows
    @AfterReturning(returning = "result", pointcut = POINTCUT)
    public void doAfterReturning(Object result) {
        log.info("请求结束时间 : {},耗时 : {} ms", LocalDateTime.now(), (System.currentTimeMillis() - beginTime));
        // 处理完请求，返回内容
        log.info("请求返回 : {}", objectMapper.writeValueAsString(result));
    }

    /**
     * 异常通知： 1. 在目标方法非正常结束，发生异常或者抛出异常时执行
     *
     * @param throwable
     */
    @AfterThrowing(pointcut = POINTCUT, throwing = "throwable")
    public void doAfterThrowing(Throwable throwable) {
        // 保存异常日志记录
        log.error("发生异常时间 : {},请求至此耗时 ： {}", LocalDateTime.now(), (System.currentTimeMillis() - beginTime));
        log.error("抛出异常 : {}", throwable.getMessage());
    }

}
