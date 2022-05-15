package com.bitmoi.user.util;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import com.bitmoi.user.model.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    public String createJwtToken(User user, long interval) {
        Date expiration = new Date(System.currentTimeMillis() + interval);
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");
        return Jwts.builder()
                .setHeader(headers)
                .claim("id", user.getId()) // id
                .claim("name", user.getName()) // name
                .claim("userId", user.getUserId()) // userId
                .setExpiration(expiration) // 만료시간
                .signWith(key, SignatureAlgorithm.HS256) // 해시값
                .compact();
    }
}
