package com.money.spendingapi.token;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // filter with high priority
public class RefreshTokenCookiePreProcessorFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if( "/oauth/token".equals(request.getRequestURI())
                && "refresh_token".equals(request.getParameter("grant_type"))
                && request.getCookies() != null ) {

            for(Cookie cookie : request.getCookies()) {
                if("refreshToken".equals(cookie.getName())) {
                    String refreshToken = cookie.getValue(); // *
                    //switch the request -> enable application get the refreshToken and throw on request again even when its information are locked.
                    request = new ServletRequestWrapper(request, refreshToken);
                }
            }

        }

        filterChain.doFilter(request, servletResponse);
    }

    // * by the fact we can't put request information ( it is already defined and locked ), we use the ServletRequestWrapper
    static class ServletRequestWrapper extends HttpServletRequestWrapper {

        private String refreshToken;

        public ServletRequestWrapper(HttpServletRequest request, String refreshToken) {
            super(request);
            this.refreshToken = refreshToken;

        }

        @Override
        public Map<String, String[]> getParameterMap() {
            ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
            map.put("refresh_token", new String[] { refreshToken });
            map.setLocked(true);
            return map;
        }
    }
}
