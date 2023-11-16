package com.learning.personal.tracker.Filters;

import com.learning.personal.tracker.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class AuthFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader != null) {
            try {
                Claims claims = Jwts.parser().verifyWith(Constants.API_SECRET_KEY).build().parseSignedClaims(authHeader).getPayload();
                httpRequest.setAttribute("user_id", Long.parseLong(claims.get("user_id").toString()));
            } catch (Exception e) {
                httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "invalid / expired token" + e);
                return;
            }
        } else {
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be provided");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
