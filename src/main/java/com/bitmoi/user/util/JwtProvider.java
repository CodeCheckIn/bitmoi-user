package com.bitmoi.user.util;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bitmoi.user.model.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.accessExpires}")
    private String interval;

    public String createJwtToken(User user) {
        Date expiration = new Date(System.currentTimeMillis() + Long.parseLong(interval));
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

    public int decode(final String token) {
        DecodedJWT jwt = JWT.decode(token);
        int userId = jwt.getClaim("userId").asInt();
        return userId;
    }
}
