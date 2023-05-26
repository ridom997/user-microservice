package com.pragma.powerup.usermicroservice.configuration.security.jwt;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.PrincipalUser;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.JwtResponseDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static com.pragma.powerup.usermicroservice.configuration.Constants.ROLES_KEY_MESSAGE;

@Component
public class JwtProvider {
    private static final  Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication) {
        PrincipalUser usuarioPrincipal = (PrincipalUser) authentication.getPrincipal();
        List<String> roles = usuarioPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        SecretKey signingKey = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.builder()
                .setSubject(usuarioPrincipal.getEmail())
                .claim(ROLES_KEY_MESSAGE, roles)
                .claim("idUser",usuarioPrincipal.getIdUser())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 180))
                .signWith(signingKey)
                .compact();
    }

    public String getNombreUsuarioFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("token mal formado");
        } catch (UnsupportedJwtException e) {
            logger.error("token no soportado");
        } catch (ExpiredJwtException e) {
            logger.error("token expirado");
        } catch (IllegalArgumentException e) {
            logger.error("token vacío");
        } catch (SignatureException e) {
            logger.error("fail en la firma");
        }
        return false;
    }

    public String refreshToken(JwtResponseDto jwtResponseDto) throws ParseException {
        try {
            Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(jwtResponseDto.getToken());
        } catch (ExpiredJwtException e) {
            JWT jwt = JWTParser.parse(jwtResponseDto.getToken());
            JWTClaimsSet claims = jwt.getJWTClaimsSet();
            String nombreUsuario = claims.getSubject();
            List<String> roles = claims.getStringListClaim(ROLES_KEY_MESSAGE);

            SecretKey signingKey = Keys.hmacShaKeyFor(secret.getBytes());

            return Jwts.builder()
                    .setSubject(nombreUsuario)
                    .claim(ROLES_KEY_MESSAGE, roles)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + expiration))
                    .signWith(signingKey)
                    .compact();
        }
        return null;
    }

}
