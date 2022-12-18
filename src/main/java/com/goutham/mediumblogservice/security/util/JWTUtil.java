package com.goutham.mediumblogservice.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

  @Value("${jwt.secret")
  private String jwtSecret;

  @Value("${token.validity}")
  private Integer tokenValidity;

  public final String AUTHORITIES_KEY = "roles";

  public String generateToken(Authentication authentication) {
    Map<String, Object> claims = new HashMap<>();
    List<String> authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

    long currentTimeMillis = System.currentTimeMillis();
    return Jwts.builder()
        .claim(AUTHORITIES_KEY, authorities)
        .setSubject(authentication.getName())
        .setIssuedAt(new Date(currentTimeMillis))
        .setExpiration(new Date(currentTimeMillis + (tokenValidity * 1000)))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public Boolean validateToken(String jwt, UserDetails userDetails) {
    Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody();
    Boolean isJWTExpired = claims.getExpiration().before(new Date());
    return !isJWTExpired && Objects.equals(claims.getSubject(), userDetails.getUsername());
  }

  public String getUsernameFromToken(String jwt) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getSubject();
  }

}
