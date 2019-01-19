/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import com.yi.core.commodity.domain.entity.Commodity;

/**
 * 推荐位
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class Recommend implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 推荐位ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 位置表（position表ID）
	 */
	@NotNull
	private Position position;
	/**
	 * 推荐位标题
	 */
	@NotBlank
	@Length(max = 127)
	private String title;
	/**
	 * 推荐类型（1今日推荐2楼层推荐）
	 */
	private Integer recommendType;
	/**
	 * 排列方式（2展示2个，3展示3个，4展示4个，5展示5个）
	 */
	private Integer showMode;
	/**
	 * 推荐位默认图片路径
	 */
	@NotBlank()
	@Length(max = 255)
	private String imgPath;
	/**
	 * 状态（0启用1禁用）
	 */
	private Integer state;
	/**
	 * 链接类型（1商品2文章3活动4专区）
	 */
	private Integer linkType;
	/**
	 * 链接ID
	 */
	private Integer linkId;
	/**
	 * 是否显示banner图 0显示1不显示
	 */
	private Integer showBanner;
	/**
	 * 是否显示标题 0显示1不显示
	 */
	private Integer showTitle;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */
	private Date delTime;
	/**
	 * 排序号
	 */
	private Integer sort;
	// columns END

	private List<Commodity> commodities;

	public Recommend() {
	}

	public Recommend(int id) {
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

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "POSITION_ID", nullable = false) })
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 127)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 0)
	public Integer getRecommendType() {
		return recommendType;
	}

	public void setRecommendType(Integer recommendType) {
		this.recommendType = recommendType;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 0)
	public Integer getShowMode() {
		return showMode;
	}

	public void setShowMode(Integer showMode) {
		this.showMode = showMode;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 255)
	public String getImgPath() {
		return this.imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 0)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 0)
	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "recommend_commodity", joinColumns = @JoinColumn(name = "RECOMMEND_ID"), inverseJoinColumns = @JoinColumn(name = "COMMODITY_ID"))
	@Where(clause = "state=2 and shelf=1 and deleted=0")
	public List<Commodity> getCommodities() {
		return commodities;
	}

	public void setCommodities(List<Commodity> commodities) {
		this.commodities = commodities;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 4)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 3)
	public Integer getLinkType() {
		return linkType;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}

	@Column(unique = true, nullable = true, length = 10)
	public Integer getLinkId() {
		return linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 3)
	public Integer getShowBanner() {
		return showBanner;
	}

	public void setShowBanner(Integer showBanner) {
		this.showBanner = showBanner;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 3)
	public Integer getShowTitle() {
		return showTitle;
	}

	public void setShowTitle(Integer showTitle) {
		this.showTitle = showTitle;
	}

}