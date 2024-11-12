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
    private String jwtSecret = "n9sy937qny7w9ye9zweywq9eqwu0zm9u0n1u291ewvdfdsfdsfdsfe9ryq793ey31n9xehsuajajdnkadbiasuhnedajçajdfafuad165489564165d4sd564as8d94as8das4d98ae734d8r5e61454bgs4h9gh46agf5a46fhdkfd03af4e68f132w5f6d12d30fd,s21fds79fds85f1dsa5fds8a9f4dsa32fd01s32fd4s8f4ds6af54a8dgr48e4t8rj4854k84m879syu4856g4r894g w8a4f6df4ds6";
    private long jwtExpirationDateTime = 3600000;

    public String generateToken(Authentication authentication) {
        String nickname = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationDateTime);

        String token = Jwts.builder()
                .subject(nickname)
                .issuedAt(new Date())
                .expiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, key())
                .compact();

        return token;
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
