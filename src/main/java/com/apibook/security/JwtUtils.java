package com.apibook.security;

import com.apibook.entity.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private JwtParser parser;
    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        parser = Jwts.parser().verifyWith((SecretKey) key).build();
    }

    public String generateToken(String email, Set<Role> roles) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiration);

        List<String> roleNames = roles.stream()
                .map(r -> r.getName().name())
                .toList();

        return Jwts.builder()
                .setSubject(email)
                .claim("roles", roleNames)     // ← aquí agregamos los roles
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsername(String token) {
        return parser.parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean isValid(String token) {
        try {
            parser.parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public long getExpiration() {
        return expiration;
    }
}