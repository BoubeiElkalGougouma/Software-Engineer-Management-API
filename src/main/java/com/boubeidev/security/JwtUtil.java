package com.boubeidev.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static javax.crypto.Cipher.SECRET_KEY;

@Component
public class JwtUtil {

  private final SecretKey secretKey;
  private final long expiration;

  public JwtUtil(
    @Value("${jwt.secret}") String secret,
    @Value("${jwt.expiration}") long expiration) {

    this.secretKey = Keys.hmacShaKeyFor(
      secret.getBytes(StandardCharsets.UTF_8)
    );

    this.expiration = expiration;
  }

  public String generateToken(String username){
    return Jwts.builder()
      .subject(username)
      .issuedAt(new Date())
      .expiration(new Date(System.currentTimeMillis() + expiration)) // 1h avant expiration
      .signWith(secretKey)
      .compact();
  }

  public String extractUsername(String token){
    return getClaims(token).getSubject();
  }

  public boolean validateToken(String token){
    try {
      getClaims(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  private Claims getClaims(String token) {

    return Jwts.parser()
      .verifyWith(secretKey)
      .build()
      .parseSignedClaims(token)
      .getPayload();
  }

}
