package com.nomsa.bbs.Config;

import com.nomsa.bbs.Util.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Component
public class JWTInterceptor implements HandlerInterceptor {

    // Name of JWT
    private static final String HEADER_user = "infranics";

    @Autowired
    JWT jwtService;

    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Get JWT Token
        final String token = request.getHeader(HEADER_user);

        Enumeration<String> headerNames = request.getHeaderNames();

        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                System.out.println("Header > " + headerName + ":"+ request.getHeader(headerName));
            }
        }

        jwtService.isUsable(token);

        return true;
    }

}
