package kr.co.polycube.backendtest.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

public class UrlValidationFilter implements Filter {
    // 영문, 숫자, ?, &, =, :, / 만 허용하는 정규표현식
    private static final Pattern INVALID_CHAR_PATTERN = Pattern.compile("[^a-zA-Z0-9?&=:/]");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
         HttpServletRequest req = (HttpServletRequest) request;
         HttpServletResponse res = (HttpServletResponse) response;
         String url = req.getRequestURI();
         
         // H2 콘솔 경로는 필터링하지 않고 통과
         if (url.contains("/h2-console")) {
             chain.doFilter(request, response);
             return;
         }
         
         String query = req.getQueryString();
         if (query != null && !query.isEmpty()) {
             url += "?" + query;
         }
         if (INVALID_CHAR_PATTERN.matcher(url).find()) {
              res.setContentType("application/json;charset=UTF-8");
              res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
              res.getWriter().write("{\"reason\":\"URL에 허용되지 않는 문자가 포함되어 있습니다.\"}");
              return;
         }
         chain.doFilter(request, response);
    }
} 