package com.api.appweb.security.jwt;

import com.api.appweb.security.entity.UsuarioPrincipal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.List;

@Component
public class JwtProvider {
    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication) {
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
        List<String> roles = usuarioPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return Jwts.builder()
                .setSubject(usuarioPrincipal.getUsername())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000L)) // Nota: Asegúrate de que la expiración esté en milisegundos
                .signWith(getSecret(secret))
                .compact();
    }

    public String getNombreUsuarioFromToken(String token) {
        return Jwts.parser().setSigningKey(getSecret(secret)).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getSecret(secret)).build().parseClaimsJws(token);
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

    private Key getSecret(String secret) {
        byte[] secretBytes = Decoders.BASE64.decode(secret); // Asegúrate de usar la codificación correcta
        if (secretBytes.length < 32) {
            throw new IllegalArgumentException("La clave debe tener al menos 256 bits (32 bytes)");
        }
        return Keys.hmacShaKeyFor(secretBytes);
    }
}
