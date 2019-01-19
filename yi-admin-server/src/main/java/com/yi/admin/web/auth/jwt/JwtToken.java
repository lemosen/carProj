package com.yi.admin.web.auth.jwt;

import static com.yi.base.ProjectConstants.USER_CODE;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ctc.wstx.util.StringUtil;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Jwt token工具
 * 
 * @author xuyh
 *
 */
public class JwtToken {

	private final static Logger LOG = LoggerFactory.getLogger(JwtToken.class);

	public final static String TOKEN_HEADER = "token";

	private final static String SERCET_KEY = "yi-token-a001";

	private final static byte[] API_KEY_SECRET_BYTES = DatatypeConverter.parseBase64Binary(SERCET_KEY);

	// 过期时间7天 7 * 24 * 60 * 60 * 1000
	private final static long KEEP_TIME = 604_800_000;

	/**
	 * 生成 token
	 * 
	 * @param userId
	 * @param userCode
	 * @param issuer
	 * @param subject
	 * @return
	 */
	public static String generateToken(Token token) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		Key signingKey = new SecretKeySpec(API_KEY_SECRET_BYTES, signatureAlgorithm.getJcaName());
		// 签发时间
		Date issuedAt = new Date();
		// 失效时间
		Date expiration = new Date(issuedAt.getTime() + KEEP_TIME);
		JwtBuilder builder = Jwts.builder().setId(token.getId()).setIssuedAt(issuedAt);
		if (StringUtils.isNotBlank(token.getIssuer())) {
			builder.setIssuer(token.getIssuer());
		}
		if (StringUtils.isNotBlank(token.getSubject())) {
			builder.setSubject(token.getSubject());
		}
		if (StringUtils.isNotBlank(token.getAudience())) {
			builder.setAudience(token.getAudience());
		}
		if (StringUtils.isNotBlank(token.getGuid())) {
			builder.claim(USER_CODE, token.getGuid());
		}
		builder.signWith(signatureAlgorithm, signingKey);
		builder.setExpiration(expiration);
		// if (keeptime >= 0) {
		// Date exp = new Date(System.currentTimeMillis() + KEEP_TIME);
		// builder.setExpiration(exp);
		// }
		return builder.compact();
	}

}
