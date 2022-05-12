package com.bitmoi.user.util;

import java.sql.Date;

import javax.crypto.SecretKey;

import com.bitmoi.user.model.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

    // @Value("${jwt.secret}")
    private String secret = "Y29kZXN0YXRlcy1iaXRodW1iLW1zYS10ZWFtLW1lbWJlci1zcHJpbmctYm9vdC13ZWJmbHV4LWp3dC1zZWNyZXQ=";

    public String createJwtToken(User user, long interval) {
        Date expiration = new Date(System.currentTimeMillis() + interval);
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

        return Jwts.builder()
                .claim("id", user.getId()) // id
                .claim("name", user.getName()) // name
                .claim("userId", user.getUserId()) // userId
                .signWith(key, SignatureAlgorithm.HS256) // 해시값
                .setExpiration(expiration) // 만료시간
                .compact();
    }
}
