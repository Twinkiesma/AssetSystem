package com.example.basic.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * JWT工具类
 */
@Component
public class JwtUtil {

    //设置秘钥明文
    public static final String SECRET = "code-duck-*%#@*!&";

    // 生成加密后的秘钥
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getMimeDecoder().decode(JwtUtil.SECRET);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static String getUUID(){
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }

    // 生成token
    public static String createToken(String subject) {
        JwtBuilder builder = generateToken(subject, getUUID());
        return builder.compact();
    }

    private static JwtBuilder generateToken(String subject, String uuid) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long ttlMillis = 60 * 60 * 1000L;     // 过期时间1小时
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)                // 唯一的ID
                .setSubject(subject)        // 主题  可以是JSON数据
                .setIssuer("sg")            // 签发者
                .setIssuedAt(now)           // 签发时间
                .signWith(SignatureAlgorithm.HS256, generalKey()) // 使用HS256对称加密算法签名, 第二个参数为秘钥
                .setExpiration(expDate);
    }

    // 解析token
    public static Claims parseToken(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

}
