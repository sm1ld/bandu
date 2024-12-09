package com.sm1ld.utils;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
@Slf4j
@Service
public class JwtUtils {

    private static String signKey = "sm1ld";
    private static Long expire = 43200000L;

    /**
     * 生成JWT令牌
     * @param claims JWT第二部分负载 payload 中存储的内容
     * @return
     */
    public static String generateJwt(Map<String, Object> claims){
        String jwt = Jwts.builder()
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, signKey)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
        return jwt;
    }

    /**
     * 解析JWT令牌
     * @param jwt JWT令牌
     * @return JWT第二部分负载 payload 中存储的内容
     */
    public static Claims parseJWT(String jwt){
        try {
            return Jwts.parser()
                    .setSigningKey(signKey)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.error("JWT 已过期: {}", e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            log.error("JWT 格式错误: {}", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            log.error("不支持的 JWT: {}", e.getMessage());
            throw e;
        } catch (JwtException e) {
            log.error("JWT 解析失败: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * 获取 JWT 头部的用户 ID
     *
     * @param request HttpServletRequest 对象
     * @return 当前用户的 ID，如果无法解析则返回 null
     */
    public static Integer getCurrentUserId(HttpServletRequest request) {
        // 从 Authorization 请求头中提取 token
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // 去掉 "Bearer " 前缀

            // 确保 token 不为空
            if (token.isEmpty()) {
                log.error("Token 不应为空");
                return null;
            }

            try {
                // 解析 JWT，获取用户 ID
                Map<String, Object> claims = JwtUtils.parseJWT(token);

                // 确保从 claims 中获取到 id
                if (claims != null && claims.containsKey("id")) {
                    return (Integer) claims.get("id"); // 返回用户 ID
                } else {
                    log.error("JWT 中没有包含用户 ID");
                    return null;
                }
            } catch (JwtException e) {
                // 如果是解析错误或签名不匹配，捕获并打印错误
                log.error("解析 JWT 失败: {}", e.getMessage());
            } catch (Exception e) {
                // 捕获其他未知异常
                log.error("发生了其他异常: {}", e.getMessage());
            }
        } else {
            log.error("请求头中没有 Bearer Token");
        }

        return null; // 如果没有有效的 token 或解析失败，返回 null
    }

}
