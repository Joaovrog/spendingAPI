package com.money.spendingapi.token;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {
    // using [advice], we can manipulate a request before it return responses to the application call
    // OAuth2AccessToken -> data type that will be intercepted on the response return

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.getMethod().getName().equals("postAccessToken");
        // by the fact a OAuthAccessToken object can be returned in others situations, we just need intercept the
        // OAuthAccessToken object returned by the POST method
    }

    @Override
    public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken oAuth2AccessToken, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        HttpServletRequest req = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
        HttpServletResponse res = ((ServletServerHttpResponse) serverHttpResponse).getServletResponse();
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) oAuth2AccessToken;

        String refreshToken = oAuth2AccessToken.getRefreshToken().getValue();
        // by the good practices, we need to put the refresh token into a cookie
        addRefreshTokenIntoCookie(refreshToken, req, res);
        removeRefreshTokenFromBody(token);
        return oAuth2AccessToken;
    }

    private void removeRefreshTokenFromBody(DefaultOAuth2AccessToken token) {
        token.setRefreshToken(null);
    }

    private void addRefreshTokenIntoCookie(String refreshToken, HttpServletRequest req, HttpServletResponse res) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false); // TODO: Change to 'true' in production environment (HTTPS)
        refreshTokenCookie.setPath(req.getContextPath() + "/oauth/token");
        refreshTokenCookie.setMaxAge(2592000); // 30 days
        res.addCookie(refreshTokenCookie);
    }


}
