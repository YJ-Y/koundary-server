package com.koundary.koundaryserver.global.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {
    private final Key key;
    private final long expMs;

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-exp-minutes}") long expMinutes
    ) {
        // secret을 byte 배열로 변환해서 서명 키 생성
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expMs = expMinutes * 60_000L;
    }
        // 토큰 발급
        public String generateAccessToken(String username) {
            Date now = new Date();
            Date exp = new Date(now.getTime() + expMs);

            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(now)
                    .setExpiration(exp)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        }

        public String getUsername(String token) {
            return parse(token).getBody().getSubject();
        }

        public boolean validate(String token) {
            try {
                parse(token);
                return true;
            } catch (JwtException | IllegalArgumentException e) {
                return false;
            }
        }

        private Jws<Claims> parse(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
    }

}


