package com.example.project.global.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
public class JwtTokenUtil {

    /* Token 발행 */
    public static String createToken(final Long userId, final String key, final Long expiredTimeMs) {
        // 토큰의 내용에 값을 넣기 위해 Claims 객체 생성
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
//                .signWith(SignatureAlgorithm.HS256, key) // 11버전에서는 deprecated 되어서 key를 사용해야 된다.
                .signWith(JwtTokenUtil.makeKey(key))
                .compact();
    }

    /* key 생성 */
    public static Key makeKey(final String key) {
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    /* token에서 claim 추출하는 메서드 */
    public static Claims extractClaims(final String token, final String key) {
        return Jwts.parserBuilder()
                .setSigningKey(JwtTokenUtil.makeKey(key))
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    /* 유효한 token인지 확인*/
    public static boolean validateToken(final String token, final String key) {
        try {
            JwtTokenUtil.extractClaims(token, key);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다.");
        } catch (MalformedJwtException | IllegalArgumentException e) {
            log.error("잘못된 JWT 토큰입니다.");
        }

        return false;
    }

    /* userId 반환 */
    public static Long getUserId(final String token, final String key) {
        return extractClaims(token, key).get("userId", Long.class);
    }
}
