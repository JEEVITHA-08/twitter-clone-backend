package com.jeevitha.twitter_clone.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY =
            "abcdefghijklmnopqrstuvwxyz123456abcdefghijklmnopqrstuvwxyz123456";

    // Generate Token
    public String generateToken(String email) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * 60)
                )
                .signWith(
                        SignatureAlgorithm.HS256,
                        SECRET_KEY
                )
                .compact();
    }

    // Extract Claims
    public Claims extractClaims(String token) {

        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // Extract Email
    public String extractEmail(String token) {

        return extractClaims(token).getSubject();
    }

    // Validate Token
    public boolean validateToken(String token) {

        try {

            extractClaims(token);
            return true;

        } catch (Exception e) {

            return false;
        }
    }
}