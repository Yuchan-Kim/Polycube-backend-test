package kr.co.polycube.backendtest.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LoggingAspect {
    // 클라이언트의 User-Agent를 로그 출력합니다. UserController의 모든 메서드 실행 전 수행하도록 설정.
    @Before("execution(* kr.co.polycube.backendtest.controller.UserController.*(..))")
    public void logClientAgent(JoinPoint joinPoint) {
         ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
         if (attributes != null) {
              HttpServletRequest request = attributes.getRequest();
              String clientAgent = request.getHeader("User-Agent");
              System.out.println("클라이언트 에이전트: " + clientAgent);
         }
    }
} 