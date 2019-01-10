/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.attachment.domain.vo.AttachmentVo;
import com.yi.core.attachment.service.IAttachmentService;
import com.yi.core.cms.CmsEnum;
import com.yi.core.cms.dao.RecommendDao;
import com.yi.core.cms.domain.bo.RecommendBo;
import com.yi.core.cms.domain.entity.Position;
import com.yi.core.cms.domain.entity.Position_;
import com.yi.core.cms.domain.entity.Recommend;
import com.yi.core.cms.domain.entity.Recommend_;
import com.yi.core.cms.domain.simple.RecommendSimple;
import com.yi.core.cms.domain.vo.RecommendListVo;
import com.yi.core.cms.domain.vo.RecommendVo;
import com.yi.core.cms.service.IPositionService;
import com.yi.core.cms.service.IRecommendService;
import com.yi.core.commodity.CommodityEnum;
import com.yi.core.common.Deleted;
import com.yi.core.common.FileType;
import com.yi.core.common.ObjectType;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 推荐位
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class RecommendServiceImpl implements IRecommendService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(RecommendServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private RecommendDao recommendDao;

	@Resource
	private IAttachmentService attachmentService;

	@Resource
	private IPositionService positionService;

	private EntityListVoBoSimpleConvert<Recommend, RecommendBo, RecommendVo, RecommendSimple, RecommendListVo> recommendConvert;

	@Override
	public Page<Recommend> query(Pagination<Recommend> query) {
		query.setEntityClazz(Recommend.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Recommend_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.asc(root.get(Recommend_.sort)));
		}));
		Page<Recommend> page = recommendDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<RecommendListVo> queryListVo(Pagination<Recommend> query) {
		Page<Recommend> pages = this.query(query);
		List<RecommendListVo> vos = recommendConvert.toListVos(pages.getContent());
		return new PageImpl<RecommendListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<RecommendVo> queryVoForApp(Integer positionType, String city) {
		Position dbPosition = positionService.getPositionByPositionType(positionType);
		if (dbPosition == null) {
			LOG.error("queryVoForApp，根据位置查询推荐数据时，位置为空");
			return new ArrayList<RecommendVo>(0);
		}
		Pagination<Recommend> query = new Pagination<>();
		query.setEntityClazz(Recommend.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Recommend_.deleted), Deleted.DEL_FALSE)));
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Recommend_.state), CmsEnum.STATE_ENABLE.getCode())));
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Recommend_.position).get(Position_.id), dbPosition.getId())));
			list1.add(criteriaBuilder.asc(root.get(Recommend_.sort)));

			// 根据定位城市查询商品
			// if (StringUtils.isNotBlank(city)) {
			// ListJoin<Recommend, Commodity> commodityJoin =
			// root.join(root.getModel().getDeclaredList("commodities", Commodity.class),
			// JoinType.LEFT);
			// // SetJoin<Recommend, Region> regionJoin =
			// //
			// root.join(commodityJoin.getModel().getDeclaringType().getDeclaredSet("regions",
			// // Region.class), JoinType.LEFT);
			// Predicate deletedPredicate =
			// criteriaBuilder.equal(commodityJoin.get(Commodity_.deleted),
			// Deleted.DEL_FALSE);
			// Predicate shelfPredicate =
			// criteriaBuilder.equal(commodityJoin.get(Commodity_.shelf),
			// CommodityEnum.SHELF_STATE_ON.getCode());
			// // Predicate cityPredicate =
			// // criteriaBuilder.like(regionJoin.get(Region_.area).get(Area_.name), "%" +
			// city
			// // + "%");
			// list.add(deletedPredicate);
			// list.add(shelfPredicate);
			// // list.add(cityPredicate);
			// }
		}));
		Page<Recommend> pages = recommendDao.findAll(query, query.getPageRequest());
		List<RecommendVo> vos = recommendConvert.toVos(pages.getContent());
		return vos;
	}

	@Override
	public Recommend getRecommendById(int recommendId) {
		return recommendDao.getOne(recommendId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RecommendVo getRecommendVoById(int recommendId) {
		Recommend recommend = this.recommendDao.getOne(recommendId);
		recommend.setCommodities(recommend.getCommodities().stream().filter(e -> e.getDeleted() == Deleted.DEL_FALSE && e.getShelf() == CmsEnum.COMMODITY_SHELF.getCode())
				.collect(Collectors.toList()));
		RecommendVo recommendVo = recommendConvert.toVo(recommend);
		recommendVo.setAttachmentVos(attachmentService.findByObjectIdAndObjectType(recommendId, ObjectType.RECOMMEND));

		return recommendVo;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RecommendVo getRecommendListVoById(int recommendId) {
		return recommendConvert.toVo(this.recommendDao.getOne(recommendId));
	}

	@Override
	public Recommend addRecommend(Recommend recommend) {
		if (recommend == null || recommend.getPosition() == null || CollectionUtils.isEmpty(recommend.getCommodities())) {
			LOG.error("提交数据不能为空");
			throw new BusinessException("提交数据不能为空");
		}
		// 非楼层推荐 校验位置是否重复
		if (!CmsEnum.POSITION_TYPE_FLOOR.getCode().equals(recommend.getPosition().getPositionType())) {
			int checkCount = recommendDao.countByPosition_idAndDeleted(recommend.getPosition().getId(), Deleted.DEL_FALSE);
			if (checkCount > 0) {
				LOG.error("位置不能重复");
				throw new BusinessException("位置不能重复");
			}
		}
		return recommendDao.save(recommend);
	}

	@Override
	public RecommendVo addRecommend(RecommendBo recommendBo) {
		RecommendVo dbRecommendVo = recommendConvert.toVo(this.addRecommend(recommendConvert.toEntity(recommendBo)));
		// 图片
		if (CollectionUtils.isNotEmpty(recommendBo.getAttachmentVos())) {
			for (AttachmentVo tmp : recommendBo.getAttachmentVos()) {
				tmp.setObjectId(dbRecommendVo.getId());
				tmp.setFileType(FileType.PICTURES);
				tmp.setObjectType(ObjectType.RECOMMEND);
				tmp.setDescription(dbRecommendVo.getTitle());
				tmp.setFilePath(tmp.getUrl());
			}
			attachmentService.saveAll(recommendBo.getAttachmentVos());
		}
		return dbRecommendVo;
	}

	@Override
	public Recommend updateRecommend(Recommend recommend) {
		if (recommend == null || recommend.getPosition() == null || CollectionUtils.isEmpty(recommend.getCommodities())) {
			LOG.error("提交数据不能为空");
			throw new BusinessException("提交数据不能为空");
		}
		// 非楼层推荐 校验位置是否重复
		if (!CmsEnum.POSITION_TYPE_FLOOR.getCode().equals(recommend.getPosition().getPositionType())) {
			// 校验位置是否重复
			int checkCount = recommendDao.countByPosition_idAndDeletedAndIdNot(recommend.getPosition().getId(), Deleted.DEL_FALSE, recommend.getId());
			if (checkCount > 0) {
				LOG.error("位置不能重复");
				throw new BusinessException("位置不能重复");
			}
		}
		Recommend dbRecommend = recommendDao.getOne(recommend.getId());
		AttributeReplication.copying(recommend, dbRecommend, Recommend_.title, Recommend_.recommendType, Recommend_.sort, Recommend_.showMode, Recommend_.imgPath, Recommend_.state,
				Recommend_.position, Recommend_.linkType, Recommend_.linkId, Recommend_.showBanner, Recommend_.showTitle);
		dbRecommend.setCommodities(recommend.getCommodities());
		return dbRecommend;
	}

	@Override
	public RecommendVo updateRecommend(RecommendBo recommendBo) {
		Recommend dbRecommend = this.updateRecommend(recommendConvert.toEntity(recommendBo));
		// 图片
		if (CollectionUtils.isNotEmpty(recommendBo.getAttachmentVos())) {
			// 删除图片 重新添加
			attachmentService.deleteByObjectIdAndObjectType(dbRecommend.getId(), ObjectType.RECOMMEND);
			for (AttachmentVo tmp : recommendBo.getAttachmentVos()) {
				tmp.setAttachId(null);
				tmp.setObjectId(dbRecommend.getId());
				tmp.setFileType(FileType.PICTURES);
				tmp.setObjectType(ObjectType.RECOMMEND);
				tmp.setDescription(dbRecommend.getTitle());
				tmp.setFilePath(tmp.getUrl());
			}
			attachmentService.saveAll(recommendBo.getAttachmentVos());
		}
		return recommendConvert.toVo(dbRecommend);
	}

	@Override
	public void removeRecommendById(int recommendId) {
		Recommend dbRecommend = recommendDao.getOne(recommendId);
		if (dbRecommend != null) {
			dbRecommend.setDeleted(Deleted.DEL_TRUE);
			dbRecommend.setDelTime(new Date());
			// 删除商品中间表数据
			dbRecommend.setCommodities(null);
		}
	}

	protected void initConvert() {
		this.recommendConvert = new EntityListVoBoSimpleConvert<Recommend, RecommendBo, RecommendVo, RecommendSimple, RecommendListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Recommend, RecommendVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Recommend, RecommendVo>(beanConvertManager) {
					@Override
					protected void postConvert(RecommendVo RecommendVo, Recommend Recommend) {

					}
				};
			}

			@Override
			protected BeanConvert<Recommend, RecommendListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Recommend, RecommendListVo>(beanConvertManager) {
					@Override
					protected void postConvert(RecommendListVo RecommendListVo, Recommend Recommend) {

					}
				};
			}

			@Override
			protected BeanConvert<Recommend, RecommendBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Recommend, RecommendBo>(beanConvertManager) {
					@Override
					protected void postConvert(RecommendBo RecommendBo, Recommend Recommend) {

					}
				};
			}

			@Override
			protected BeanConvert<RecommendBo, Recommend> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RecommendBo, Recommend>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Recommend, RecommendSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Recommend, RecommendSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<RecommendSimple, Recommend> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RecommendSimple, Recommend>(beanConvertManager) {
					@Override
					public Recommend convert(RecommendSimple RecommendSimple) throws BeansException {
						return recommendDao.getOne(RecommendSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}

	/**
	 * 禁用
	 * 
	 * @return
	 */
	@Override
	public RecommendVo updateStateDisable(int recommendId) {
		Recommend recommend = recommendDao.getOne(recommendId);
		recommend.setState(CmsEnum.STATE_DISABLE.getCode());
		return recommendConvert.toVo(recommend);
	}

	/**
	 * 启用
	 * 
	 * @return
	 */
	@Override
	public RecommendVo updateStateEnable(int recommendId) {
		Recommend recommend = recommendDao.getOne(recommendId);
		recommend.setState(CmsEnum.STATE_ENABLE.getCode());
		return recommendConvert.toVo(recommend);
	}

	// @Deprecated
	// @Override
	// @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	// public List<RecommendVo> getFloorRecommends(Integer positionType) {
	// Position dbPosition =
	// positionService.getPositionByPositionType(positionType);
	// if (dbPosition == null) {
	// LOG.error("getFloorRecommends，查询楼层推荐时，位置为空");
	// return null;
	// }
	// return
	// recommendConvert.toVos(recommendDao.findByPosition_IdAndStateAndDeleted(dbPosition.getId(),
	// CmsEnum.STATE_ENABLE.getCode(), Deleted.DEL_FALSE).stream().map(e -> {
	// e.setCommodities(e.getCommodities().stream().filter(e1 ->
	// e1.getDeleted().equals(Deleted.DEL_FALSE) &&
	// e1.getShelf().equals(CommodityEnum.SHELF_ON.getCode()))
	// .collect(Collectors.toList()));
	// return e;
	// }).collect(Collectors.toList()));
	// }
	//
	// @Deprecated
	// @Override
	// @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	// public List<RecommendVo> getTodayRecommends(Integer positionType) {
	// Position dbPosition =
	// positionService.getPositionByPositionType(positionType);
	// if (dbPosition == null) {
	// LOG.error("getFloorRecommends，查询今日推荐时，位置为空");
	// return null;
	// }
	// return
	// recommendConvert.toVos(recommendDao.findByPosition_IdAndStateAndDeleted(dbPosition.getId(),
	// CmsEnum.STATE_ENABLE.getCode(), Deleted.DEL_FALSE).stream().map(e -> {
	// e.setCommodities(e.getCommodities().stream().filter(e1 ->
	// Deleted.DEL_FALSE.equals(e1.getDeleted()) &&
	// CommodityEnum.SHELF_ON.getCode().equals(e1.getShelf()))
	// .collect(Collectors.toList()));
	// return e;
	// }).collect(Collectors.toList()));
	// }

	/**
	 * 根据位置获取推荐数据
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<RecommendVo> getRecommendsByPositionType(Integer positionType, String city) {
		Position dbPosition = positionService.getPositionByPositionType(positionType);
		if (dbPosition == null) {
			LOG.error("getRecommendsByPositionType，根据位置查询推荐数据时，位置为空");
			return new ArrayList<RecommendVo>(0);
		}
		List<Recommend> dbRecommends = recommendDao.findByPosition_IdAndStateAndDeleted(dbPosition.getId(), CmsEnum.STATE_ENABLE.getCode(), Deleted.DEL_FALSE);
		dbRecommends.stream().map(e -> {
			e.setCommodities(e.getCommodities().stream().filter(e1 -> {
				if (Deleted.DEL_FALSE.equals(e1.getDeleted()) && CommodityEnum.SHELF_ON.getCode().equals(e1.getShelf())) {
					// if (StringUtils.isNotBlank(city)) {
					// if (CollectionUtils.isNotEmpty(e1.getRegions())) {
					// boolean flag = false;
					// e1.getRegions().forEach(tmpRegion -> {
					// if (tmpRegion != null && tmpRegion.getArea().getName().contains(city)) {
					// flag = true;
					// }
					// });
					// }
					// return false;
					// } else {
					// return true;
					// }
					return true;
				}
				return false;
			}).collect(Collectors.toList()));
			return e;
		}).collect(Collectors.toList());

		return recommendConvert.toVos(dbRecommends);

	}

	/**
	 * 根据商品id获取推荐位
	 * 
	 * @param type
	 * @param id
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<RecommendVo> getRecommendsByCommodityId(Integer id, Integer type) {
		return recommendConvert.toVos(recommendDao.findByLinkIdAndLinkType(id, type));
	}

	@Override
	public List<RecommendVo> queryRecommends(Integer positionType, String city) {
		if (StringUtils.isNotBlank(city)) {
			return recommendConvert.toVos(recommendDao.findByPositionAndCity(positionType, CmsEnum.STATE_ENABLE.getCode(), Deleted.DEL_FALSE, CommodityEnum.SHELF_ON.getCode(),
					CommodityEnum.STATE_AGREE.getCode(), city));
		} else {
			return recommendConvert.toVos(recommendDao.findByPosition(positionType, CmsEnum.STATE_ENABLE.getCode(), Deleted.DEL_FALSE, CommodityEnum.SHELF_ON.getCode(),
					CommodityEnum.STATE_AGREE.getCode()));

		}
	}

}
