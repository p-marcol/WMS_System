package com.inz.WMS_Backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.inz.WMS_Backend.config.JwtConfigResource;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
public class JwtTokenUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtils.class);

    private static String getNewToken(UserDetails userDetails, long expirationTime) {
        List<String> authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", authorities);

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(JwtConfigResource.SECRET_KEY)
                .compact();
    }

    public static String generateToken(UserDetails userDetails) {
        return getNewToken(userDetails, JwtConfigResource.EXPIRATION_TIME);
    }

    public static String generateRefreshToken(UserDetails userDetails) {
        return getNewToken(userDetails, JwtConfigResource.REFRESH_EXPIRATION_TIME);
    }

    private static Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(JwtConfigResource.SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public static List<String> extractAuthorities(String token) {
        return extractClaims(token).get("authorities", List.class);
    }

    public static boolean validate(String token) {
        try {
            Jwts.parser().verifyWith(JwtConfigResource.SECRET_KEY).build().parseSignedClaims(token);
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            logger.error("Invalid token" + e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Token has expired");
        }
        return false;
    }

    public static User getUserFromContext() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        } else {
            return null;
        }
    }
}
