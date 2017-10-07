package com.github.waynehu.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access_token.expiration}")
    private Long accessTokenExpiry;

    @Value("${jwt.refresh_token.expiration}")
    private Long refreshTokenExpiry;


    public String getUsernameFromToken(String authToken) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(authToken);
            username = (String) claims.get("username");
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public boolean validateToken(String authToken, UserDetails userDetails) {
        JwtUserDetail user = (JwtUserDetail) userDetails;
        final String username = getUsernameFromToken(authToken);
//        final Date created = getCreatedDateFromToken(authToken);
        //final Date expiration = getExpirationDateFromToken(token);
        return username.equals(user.getUsername()) && !isTokenExpired(authToken);
    }

    private Date getCreatedDateFromToken(String authToken) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(authToken);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public String generateToken(Map<String, Object> claims) {
        Date expiry = new Date();
        expiry.setTime(Date.from(Instant.now()).getTime() + accessTokenExpiry);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

}
