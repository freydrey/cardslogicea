package com.logicea.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JWTUtil {

    @Value("#{'${auth.secret-key}'}")
    private String SECRET_KEY;

    public String issueToken(String subject){
        return issueToken(subject, Map.of());
    }

    public String issueToken(String subject, String ...scopes){
        return issueToken(subject, Map.of("scopes", scopes));
    }

    public String issueToken(String subject, List<String> scopes){
        return issueToken(subject, Map.of("scopes", scopes));
    }

    public String issueToken(
            String subject,
            Map<String, Object> claims){

        return Jwts
                .builder()
                .claims(claims)
                .subject(subject)
                .issuer("https://logiceacards.com")
                .issuedAt(Date.from(Instant.now()))
                .expiration(
                        Date.from(
                                Instant.now().plus(5, ChronoUnit.DAYS)
                        )
                )
                .signWith(getSigningKey())
                .compact();

    }

    public String getSubject(String token){
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    private Claims getClaims(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    public Collection<GrantedAuthority> extractAuthorities(String jwtToken) {
        // Extract authorities from the JWT token
        Claims claims = getClaims(jwtToken);
        List<String> authorities = claims.get("scopes", List.class);

        // Map authorities to Spring Security's GrantedAuthority objects
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public boolean isTokenValid(String jwt, String username) {
        String subject = getSubject(jwt);
        return subject.equals(username) && !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt) {
        Date today = Date.from(Instant.now());
        return getClaims(jwt).getExpiration().before(today);
    }

}
