package com.inz.WMS_Backend.config;

import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtConfigResource {
    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
//    public static final String PUBLIC_KEY = "PublicKey";
    public static final long EXPIRATION_TIME = 15 * 60 * 1000; // 15 minutes
    public static final long REFRESH_EXPIRATION_TIME = 30 * 60 * 1000; // 30 minutes
}
