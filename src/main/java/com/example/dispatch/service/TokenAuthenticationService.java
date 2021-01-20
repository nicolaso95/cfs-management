package com.example.dispatch.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import com.example.dispatch.model.UserPrincipal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyList;

@Slf4j
@Service
@Configuration
public class TokenAuthenticationService {
    private final String TOKEN_PREFIX = "Bearer";
    private final String HEADER_STRING = "Authorization";

    @Value("${jwt-expiration-in-ms}")
    private long jwtExpirationInMs;
    @Value("${jwt-secret}")
    private String jwtSecret;

    public TokenAuthenticationService() {
        super();
    }

    private String trimToken(String token) {
        return token.replace(TOKEN_PREFIX, "").trim();
    }

    public String getAgencyIdFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(trimToken(token))
                    .getBody()
                    .get("agencyId").toString();
                    
        } catch (Exception e) {
            log.error("Cannot validate user token `{}`: error thrown - {}", token, e.getMessage());
        }
        return null;
    }

    public String getAuthenticationUser(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(trimToken(token))
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            log.error("Cannot validate user token `{}`: error thrown - {}", token, e.getMessage());
        }
        return null;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = getAuthenticationUser(token);
            return user != null ? new UsernamePasswordAuthenticationToken(user, null, emptyList()) : null;
        }
        return null;
    }

    public String generateToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        Map<String, Object> map = new HashMap<>();
        map.put("agencyId", userPrincipal.getAgencyId().toString());

        return Jwts.builder()
                .setClaims(map)
                .setId(userPrincipal.getId().toString())
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}