package kr.co.polycube.backendtest.config;

import kr.co.polycube.backendtest.filter.UrlValidationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<UrlValidationFilter> urlValidationFilterRegistrationBean() {
         FilterRegistrationBean<UrlValidationFilter> registrationBean = new FilterRegistrationBean<>();
         registrationBean.setFilter(new UrlValidationFilter());
         registrationBean.addUrlPatterns("/*");
         registrationBean.setOrder(1);
         return registrationBean;
    }
} 