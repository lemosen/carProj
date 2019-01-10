/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service.impl;

import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.dao.NationalGroupDao;
import com.yi.core.activity.dao.NationalGroupRecordDao;
import com.yi.core.activity.domain.bo.NationalGroupBo;
import com.yi.core.activity.domain.entity.NationalGroup;
import com.yi.core.activity.domain.entity.NationalGroupRecord;
import com.yi.core.activity.domain.entity.NationalGroup_;
import com.yi.core.activity.domain.simple.NationalGroupSimple;
import com.yi.core.activity.domain.vo.NationalGroupListVo;
import com.yi.core.activity.domain.vo.NationalGroupRecordVo;
import com.yi.core.activity.domain.vo.NationalGroupVo;
import com.yi.core.activity.service.INationalGroupService;
import com.yi.core.attachment.domain.vo.AttachmentVo;
import com.yi.core.attachment.service.IAttachmentService;
import com.yi.core.commodity.dao.ProductDao;
import com.yi.core.commodity.domain.entity.Stock;
import com.yi.core.common.Deleted;
import com.yi.core.common.ObjectType;
import com.yi.core.order.dao.SaleOrderDao;
import com.yihz.common.utils.date.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.beans.BeansException;
import com.yihz.common.persistence.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.data.domain.Page;
import com.yihz.common.persistence.Pagination;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import org.springframework.beans.factory.InitializingBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class NationalGroupServiceImpl implements INationalGroupService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(NationalGroupServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private NationalGroupDao nationalGroupDao;

	@Resource
	private NationalGroupRecordDao nationalGroupRecordDao;

	@Resource
	private SaleOrderDao saleOrderDao;

	@Resource
	private IAttachmentService attachmentService;

	@Resource
	private ProductDao productDao;

	private EntityListVoBoSimpleConvert<NationalGroup, NationalGroupBo, NationalGroupVo, NationalGroupSimple, NationalGroupListVo> nationalGroupConvert;

	/**
	 * 分页查询NationalGroup
	 **/
	@Override
	public Page<NationalGroup> query(Pagination<NationalGroup> query) {
		query.setEntityClazz(NationalGroup.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(NationalGroup_.deleted), Deleted.DEL_FALSE)));
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(NationalGroup_.state), ActivityEnum.STATE_ENABLE.getCode())));
		}));
		Page<NationalGroup> page = nationalGroupDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 创建NationalGroup
	 **/
	@Override
	public NationalGroupVo addNationalGroup(NationalGroupBo nationalGroupBo) {
		NationalGroup nationalGroup = nationalGroupConvert.toEntity(nationalGroupBo);
		nationalGroup.setCreateTime(new Date());
		return nationalGroupConvert.toVo(nationalGroupDao.save(nationalGroup));
	}

	/**
	 * 更新NationalGroup
	 **/
	@Override
	public NationalGroupVo updateNationalGroup(NationalGroupBo nationalGroupBo) {
		if (nationalGroupBo.getCoupon().getId() == 0) {
			nationalGroupBo.setCoupon(null);
		}
		NationalGroup dbNationalGroup = nationalGroupDao.getOne(nationalGroupBo.getId());
		AttributeReplication.copying(nationalGroupConvert.toEntity(nationalGroupBo), dbNationalGroup, NationalGroup_.label, NationalGroup_.startTime, NationalGroup_.endTime,
				NationalGroup_.activityCover, NationalGroup_.shareTitle, NationalGroup_.stockSet, NationalGroup_.product, NationalGroup_.activityStock, NationalGroup_.groupPrice,
				NationalGroup_.groupPeople, NationalGroup_.limitGroupTime, NationalGroup_.limitQuantity, NationalGroup_.limitPayTime, NationalGroup_.rewardType,
				NationalGroup_.rewardIntegral, NationalGroup_.coupon, NationalGroup_.freightSet, NationalGroup_.freight, NationalGroup_.state, NationalGroup_.createTime);
		return nationalGroupConvert.toVo(dbNationalGroup);
	}

	/**
	 * 删除NationalGroup
	 **/
	@Override
	public void removeNationalGroupById(int id) {
		nationalGroupDao.deleteById(id);
	}

	/**
	 * 根据ID得到NationalGroup
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public NationalGroupVo getNationalGroupVoById(int id) {
		NationalGroup nationalGroup = this.nationalGroupDao.getOne(id);
		NationalGroupVo nationalGroupVo = nationalGroupConvert.toVo(nationalGroup);
//		nationalGroupVo.setNationalGroupRecords(
//				nationalGroupVo.getNationalGroupRecords().stream().filter(e -> ActivityEnum.WAIT_GROUP_RECORD_STATE.getCode() == e.getState()).collect(Collectors.toSet()));
		nationalGroupVo.getNationalGroupRecords().forEach(e -> {
			Date date = new Date();
			Date date1 = DateUtils.addHours(e.getCreateTime(), nationalGroup.getLimitGroupTime());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format.format(date);
			// 毫秒ms
			long diff = date1.getTime() - date.getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000);
			String sj = diffHours + ":" + diffMinutes + ":" + diffSeconds;
			e.setEndTime(sj);
		});
		nationalGroupVo.setCommodity(nationalGroupVo.getProduct().getCommodity());
		List<AttachmentVo> attachmentVos = attachmentService.findByObjectIdAndObjectType(nationalGroupVo.getCommodity().getId(), ObjectType.COMMODITY);
		nationalGroupVo.getCommodity().setAttachmentVos(attachmentVos);

		return nationalGroupVo;
	}

	/**
	 * 根据ID得到NationalGroupListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public NationalGroupVo getListVoById(int id) {
		return nationalGroupConvert.toVo(this.nationalGroupDao.getOne(id));
	}

	/**
	 * 分页查询: NationalGroup
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<NationalGroupListVo> queryListVo(Pagination<NationalGroup> query) {

		Page<NationalGroup> pages = this.query(query);

		List<NationalGroupListVo> vos = nationalGroupConvert.toListVos(pages.getContent());
		return new PageImpl<NationalGroupListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	/**
	 * 分页app查询
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<NationalGroupListVo> queryListVoApp(Pagination<NationalGroup> query) {
		Page<NationalGroup> pages = this.query(query);

		List<NationalGroupListVo> vos = nationalGroupConvert.toListVos(pages.getContent());
		return new PageImpl<NationalGroupListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.nationalGroupConvert = new EntityListVoBoSimpleConvert<NationalGroup, NationalGroupBo, NationalGroupVo, NationalGroupSimple, NationalGroupListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<NationalGroup, NationalGroupVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<NationalGroup, NationalGroupVo>(beanConvertManager) {
					@Override
					protected void postConvert(NationalGroupVo NationalGroupVo, NationalGroup NationalGroup) {

					}
				};
			}

			@Override
			protected BeanConvert<NationalGroup, NationalGroupListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<NationalGroup, NationalGroupListVo>(beanConvertManager) {
					@Override
					protected void postConvert(NationalGroupListVo NationalGroupListVo, NationalGroup NationalGroup) {

					}
				};
			}

			@Override
			protected BeanConvert<NationalGroup, NationalGroupBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<NationalGroup, NationalGroupBo>(beanConvertManager) {
					@Override
					protected void postConvert(NationalGroupBo NationalGroupBo, NationalGroup NationalGroup) {

					}
				};
			}

			@Override
			protected BeanConvert<NationalGroupBo, NationalGroup> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<NationalGroupBo, NationalGroup>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<NationalGroup, NationalGroupSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<NationalGroup, NationalGroupSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<NationalGroupSimple, NationalGroup> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<NationalGroupSimple, NationalGroup>(beanConvertManager) {
					@Override
					public NationalGroup convert(NationalGroupSimple NationalGroupSimple) throws BeansException {
						return nationalGroupDao.getOne(NationalGroupSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public NationalGroupVo goGroupPurchase(int commodityId) {
		// Commodity commodity=commodityDao.getOne(commodityId);
		// for (Product product: commodity.getProducts()) {
		// NationalGroup
		// nationalGroup=nationalGroupDao.findByProductId(product.getId());
		// //ArrayUtils StringUtils
		// if(!StringUtils.isEmpty(nationalGroup)){
		// return nationalGroupConvert.toVo(nationalGroup);
		// }
		// }
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<NationalGroupListVo> yesterdayPurchase() {
		Date startDate = DateUtils.addDays(new Date(), -1);
		List<NationalGroup> nationalGroup = nationalGroupDao.findByYesterday(startDate);
		/*
		 * if(CollectionUtils.isNotEmpty(nationalGroup)){ return
		 * nationalGroupConvert.toVos(nationalGroup); }
		 */
		return nationalGroupConvert.toListVos(nationalGroup);
	}

	/**
	 * 团购检查库存
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean groupBuyCheckStock(Integer nationalGroupId, int quantity) {
		NationalGroup nationalGroup = nationalGroupDao.findByIdAndDeleted(nationalGroupId, Deleted.DEL_FALSE);
		if (nationalGroup == null) {
			LOG.error("nationalGroupId={}，活动不存在");
			return false;
		}
		if (nationalGroup.getActivityStock() < quantity) {
			LOG.error("productId={}，库存不足");
			return false;
		}
		return true;
	}

}
