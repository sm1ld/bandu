package com.sm1ld;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest
class BanduApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * 生成JWT令牌
     */
    @Test
    public void testGenJwt() {
        Map<String,Object> claims =new HashMap<>();
        claims.put("id",1);
        claims.put("name","test");
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,"sm1ld")//签名算法
                .setClaims(claims)//自定义载荷
                .setExpiration(new Date(System.currentTimeMillis()+3600*1000))//有效期
                .compact();
        System.out.println(jwt);
    }

    /**
     * 解析JWT令牌
     */
    @Test
    public void testParseJwt() {
        Claims claims = Jwts.parser()
                .setSigningKey("sm1ld")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoidGVzdCIsImlkIjoxLCJleHAiOjE3MzAzMDk5NjV9.sUOnMTZHNtGlWz2upcyA6A2-CsYLEmKUomGSh6issYM")
                .getBody();
        System.out.println(claims);
    }



}
