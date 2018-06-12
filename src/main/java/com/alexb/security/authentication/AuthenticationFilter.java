package com.alexb.security.authentication;

import com.alexb.model.Authority;
import com.alexb.model.AuthorizedUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Value("${secret.key}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        final Cookie[] jwtCookies = servletRequest.getCookies();

        logger.error("URL = " + servletRequest.getRequestURL());

        if (jwtCookies != null) {

            Cookie jwtCookieToken = Arrays.stream(jwtCookies)
                    .filter(cookie -> cookie.getName().equals("X-TOKEN-ID"))
                    .findFirst()
                    .orElseThrow(RuntimeException::new);

            Claims body = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtCookieToken.getValue())
                    .getBody();

            AuthorizedUser authorizedUser = AuthorizedUser.builder()
                    .username(body.get("name").toString())
                    .password(body.get("password").toString())
                    .firstName(body.get("firstname").toString())
                    .lastName(body.get("lastname").toString())
                    .authorities(deserializeAuthoritiesList(body.get("authorities")))
                    .build();

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(authorizedUser,
                    authorizedUser.getPassword(),
                    authorizedUser.getAuthorities()
            ));

            jwtCookieToken.setMaxAge(600);
            servletResponse.addCookie(jwtCookieToken);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }


    @SuppressWarnings("unchecked")
    private List<Authority> deserializeAuthoritiesList(Object authoritiesList) {
        List<LinkedHashMap<String, String>> list = (List<LinkedHashMap<String, String>>) authoritiesList;

        return list.stream()
                .flatMap(authMap -> authMap.values().stream())
                .map(Authority::of)
                .collect(Collectors.toList());
    }
}