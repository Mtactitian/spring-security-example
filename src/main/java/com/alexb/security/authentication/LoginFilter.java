package com.alexb.security.authentication;

import com.alexb.model.AuthorizedUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFilter extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${secret.key}")
    private String secretKey;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AuthorizedUser authorizedUser = (AuthorizedUser) authentication.getPrincipal();

        Claims claims = Jwts.claims();
        claims.put("name", authorizedUser.getUsername());
        claims.put("password", authorizedUser.getPassword());
        claims.put("firstname", authorizedUser.getFirstName());
        claims.put("lastname", authorizedUser.getLastName());
        claims.put("authorities", authorizedUser.getAuthorities());

        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        response.addCookie(new Cookie("X-TOKEN-ID", token));

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
