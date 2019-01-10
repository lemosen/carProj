package com.yi.webserver.web.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static com.yi.base.ProjectConstants.USER_CODE;

/**
 * Jwt token工具
 */
public class JwtWebToken {

    private final static Logger LOG = LoggerFactory.getLogger(JwtWebToken.class);

    public final static String TOKEN_HEADER = "token";

    private final static String sercetKey = "yi-yy-y001";

    private final static byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(sercetKey);

    // 过期时间7天
    private final static long keeptime = 7 * 24 * 60 * 60 * 1000;

    public static String generateToken(int userId, String userCode, String issuer, String subject) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setId(String.valueOf(userId))
                .setIssuedAt(new Date());

        if (subject != null) {
            builder.setSubject(subject);
        }
        if (issuer != null) {
            builder.setIssuer(issuer);
        }
        if (userCode != null) {
            builder.claim(USER_CODE, userCode);
        }

        builder.signWith(signatureAlgorithm, signingKey);

        if (keeptime >= 0) {
            Date exp = new Date(System.currentTimeMillis() + keeptime);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    public static String updateToken(String token) {
        try {
            Claims claims = verifyToken(token);

            String id = claims.getId();
            String subject = claims.getSubject();
            String issuer = claims.getIssuer();
            String userCode = (String) claims.get(USER_CODE);

            return generateToken(Integer.parseInt(id), userCode, issuer, subject);

        } catch (Exception ex) {
            LOG.info("JWT令牌更新失败, token={}", token, ex);
        }
        return null;
    }


    public static String updateTokenBase64Code(String token) {
        Base64.Encoder base64Encoder = Base64.getEncoder();
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            token = new String(decoder.decode(token), "utf-8");
            Claims claims = verifyToken(token);

            String id = claims.getId();
            String subject = claims.getSubject();
            String issuer = claims.getIssuer();
            String userCode = (String) claims.get(USER_CODE);

            String newToken = generateToken(Integer.parseInt(id), userCode, issuer, subject);
            return base64Encoder.encodeToString(newToken.getBytes());

        } catch (Exception ex) {
            LOG.info("JWT令牌更新失败, token={}", token, ex);
        }
        return null;
    }


    public static Claims verifyToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(apiKeySecretBytes)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }


    public static WebToken getToken(String token) {
        LOG.debug("解析JWT令牌 {}", token);
        try {
            Claims claims = verifyToken(token);

            String id = claims.getId();
            String subject = claims.getSubject();
            String issuer = claims.getIssuer();
            String userCode = (String) claims.get(USER_CODE);

            WebToken tk = new WebToken(Integer.parseInt(id), userCode, issuer, subject);

            LOG.debug("JWT令牌解析成功 {}", tk);
            return tk;

        } catch (Exception ex) {
            LOG.info("JWT令牌解析失败, token={}", token, ex);
        }

        return null;
    }
}