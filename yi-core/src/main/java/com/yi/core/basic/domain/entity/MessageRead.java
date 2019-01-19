/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.member.domain.entity.Member;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * *
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class MessageRead implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 消息阅读ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 消息（message表ID）
	 */
	@NotNull
	private Message message;
	/**
	 * 会员（member表ID）
	 */
	@NotNull
	private Member member;
	/**
	 * 阅读时间
	 */

	private Date readTime;
	// columns END

	public MessageRead() {
	}

	public MessageRead(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, insertable = true, updatable = true, length = 10)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getReadTime() {
		return this.readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@JsonIgnore
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "MESSAGE_ID", nullable = false) })
	public Message getMessage() {
		return message;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@JsonIgnore
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "MEMBER_ID", nullable = false) })
	public Member getMember() {
		return member;
	}

}