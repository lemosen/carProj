package com.yi.admin.web.auth.jwt;

import java.util.Date;

/**
 * Jwt token
 * 
 * @author xuyh
 *
 */
public class Token {

	/** JWT ID */
	private String id;
	/** 发行人 */
	private String issuer;
	/** 主题 */
	private String subject;
	/** 用户 */
	private String audience;
	/** 到期时间 */
	private Date expiration;
	/** 在此之前不可用 */
	private Date notBefore;
	/** 发布时间 */
	private Date issuedAt;
	/** 全局唯一标识符 */
	private String guid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAudience() {
		return audience;
	}

	public void setAudience(String audience) {
		this.audience = audience;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public Date getNotBefore() {
		return notBefore;
	}

	public void setNotBefore(Date notBefore) {
		this.notBefore = notBefore;
	}

	public Date getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(Date issuedAt) {
		this.issuedAt = issuedAt;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

}
