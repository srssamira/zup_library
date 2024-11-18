package com.library.zup_library.infra.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final String jwtSecret = "fhdsaf7832h32heichd93y737rg1r1r03hjabd93713bdbhdsancjasbzhsadgqgyg1972et3geyqhwdg13r8316rg3yqd8y3tr18ygd";
    private final long jwtExpirationDateTime = 3600000;

    public String generateToken(Authentication authentication) {
        String nickname = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationDateTime);

        return Jwts.builder()
                .subject(nickname)
                .issuedAt(new Date())
                .expiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, key())
                .compact();
    }

    // método pra criptografar a chave secreta
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // método pra pegar o nickname pelo token
    public String getNickname(String token) {
        return Jwts.parser()
                .verifyWith((SecretKeySpec) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // validar token
    public boolean validateToken(String token) {
        Jwts.parser()
                .verifyWith((SecretKeySpec) key())
                .build()
                .parse(token);
        return true;
    }
}
