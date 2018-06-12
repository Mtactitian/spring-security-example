package com.alexb.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

@Component
public class AuthenticationFilter extends GenericFilterBean {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Value("${secret.key}")
    private String secretKey;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        final Cookie[] cookies = ((HttpServletRequest) servletRequest).getCookies();

        try {

            final Cookie token = Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals("X-TOKEN-ID"))
                    .findFirst()
                    .orElseThrow(RuntimeException::new);

            Claims body = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token.getValue())
                    .getBody();
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(body.get("name"), body.get("password")));
        } catch (NullPointerException ignored) {

        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
