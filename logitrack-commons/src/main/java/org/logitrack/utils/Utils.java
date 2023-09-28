package org.logitrack.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.logitrack.exceptions.CommonApplicationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Slf4j
public class Utils {
    @Value("${jwt.secretToken}")
    private static String secretToken;

    private static Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretToken);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Authentication authentication) {
        String email = authentication.getName();
        Date currentDate = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(System.currentTimeMillis() + 60000 * 60);
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(currentDate)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public static Claims getUserNameFromToken(String token) {
        log.info("JwtService is called to extract the userEmail from the JWT");
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static boolean validateToken(String token) throws CommonApplicationException {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Map validateTokenAndReturnDetail(String secretToken) throws CommonApplicationException {
        if (Boolean.FALSE.equals(validateToken(secretToken))) {
            throw new CommonApplicationException("Invalid Token");
        }
        var claim = getUserNameFromToken(secretToken);
        return Map.of("name", claim.get("name", String.class),
                "email", claim.get("email", String.class),
                "role", claim.get("role", String.class));
    }
}
