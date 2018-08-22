package com.nomsa.bbs;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

public class JWT {

    @Test
    public void JWTmaking() {
        String jwtString = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("issueDate", System.currentTimeMillis())
                .setSubject("내용")
                .signWith(SignatureAlgorithm.HS512, "aaaa")
                .compact();
        System.out.println(jwtString);
    }
}
