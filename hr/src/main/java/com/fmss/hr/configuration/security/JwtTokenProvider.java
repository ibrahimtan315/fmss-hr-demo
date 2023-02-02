package com.fmss.hr.configuration.security;

import com.fmss.hr.common.constant.ExceptionMessages;
import com.fmss.hr.entities.Role;
import com.fmss.hr.exceptions.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final AuthService authService;

    @Value("secret-key")
    private String secretKey;

    @Value("432000000")
    private long validityInMilliseconds = 432000000;

    public String createToken(String email, Role role, Long id) {
        final Claims claims = Jwts.claims().setSubject(email);
        claims.put("auth", role.getName());
        claims.put("id", id);


        final Date now = new Date();
        final Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        final UserDetails user = authService.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }

    private String getEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        final String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException(ExceptionMessages.EXPIRED_OR_INVALID_JWT_TOKEN, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
