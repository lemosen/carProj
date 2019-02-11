/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.finance.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;

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

import com.google.common.collect.Lists;
import com.yi.core.commodity.dao.CommodityDao;
import com.yi.core.finance.dao.PlatformSaleStatDao;
import com.yi.core.finance.domain.bo.PlatformSaleStatBo;
import com.yi.core.finance.domain.entity.PlatformSaleStat;
import com.yi.core.finance.domain.entity.PlatformSaleStat_;
import com.yi.core.finance.domain.simple.PlatformSaleStatSimple;
import com.yi.core.finance.domain.vo.PlatformSaleStatListVo;
import com.yi.core.finance.domain.vo.PlatformSaleStatVo;
import com.yi.core.finance.service.IPlatformSaleStatService;
import com.yi.core.order.OrderEnum;
import com.yi.core.order.dao.SaleOrderDao;
import com.yi.core.order.dao.SaleOrderItemDao;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.domain.entity.SaleOrderItem;
import com.yi.core.order.domain.entity.SaleOrder_;
import com.yi.core.supplier.dao.SupplierDao;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class PlatformSaleStatServiceImpl implements IPlatformSaleStatService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(PlatformSaleStatServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private PlatformSaleStatDao platformSaleStatDao;
	@Resource
	private SaleOrderDao saleOrderDao;

	@Resource
	private SaleOrderItemDao saleOrderItemDao;

	@Resource
	private CommodityDao commodityDao;
	@Resource
	private SupplierDao supplierDao;

	private EntityListVoBoSimpleConvert<PlatformSaleStat, PlatformSaleStatBo, PlatformSaleStatVo, PlatformSaleStatSimple, PlatformSaleStatListVo> platformSaleStatConvert;

	@Override
	public PlatformSaleStat getPlatformSaleStatById(int platformSaleStatId) {

		return platformSaleStatDao.getOne(platformSaleStatId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PlatformSaleStatVo getPlatformSaleStatVoById(int platformSaleStatId) {

		return platformSaleStatConvert.toVo(this.platformSaleStatDao.getOne(platformSaleStatId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PlatformSaleStatListVo getPlatformSaleStatListVoById(int platformSaleStatId) {
		return platformSaleStatConvert.toListVo(this.platformSaleStatDao.getOne(platformSaleStatId));
	}

	@Override
	public PlatformSaleStatVo addPlatformSaleStat(PlatformSaleStat platformSaleStat) {
		return platformSaleStatConvert.toVo(platformSaleStatDao.save(platformSaleStat));
	}

	@Override
	public PlatformSaleStatListVo addPlatformSaleStat(PlatformSaleStatBo platformSaleStatBo) {
		return platformSaleStatConvert.toListVo(platformSaleStatDao.save(platformSaleStatConvert.toEntity(platformSaleStatBo)));
	}

	@Override
	public PlatformSaleStatVo updatePlatformSaleStat(PlatformSaleStat platformSaleStat) {

		PlatformSaleStat dbPlatformSaleStat = platformSaleStatDao.getOne(platformSaleStat.getId());
		AttributeReplication.copying(platformSaleStat, dbPlatformSaleStat, PlatformSaleStat_.platformOrderNum, PlatformSaleStat_.saleAmount, PlatformSaleStat_.platformRatio,
				PlatformSaleStat_.cost, PlatformSaleStat_.profit, PlatformSaleStat_.statTime);
		return platformSaleStatConvert.toVo(dbPlatformSaleStat);
	}

	@Override
	public PlatformSaleStatListVo updatePlatformSaleStat(PlatformSaleStatBo platformSaleStatBo) {
		PlatformSaleStat dbPlatformSaleStat = platformSaleStatDao.getOne(platformSaleStatBo.getId());
		AttributeReplication.copying(platformSaleStatBo, dbPlatformSaleStat, PlatformSaleStat_.platformOrderNum, PlatformSaleStat_.saleAmount, PlatformSaleStat_.platformRatio,
				PlatformSaleStat_.cost, PlatformSaleStat_.profit, PlatformSaleStat_.statTime);
		return platformSaleStatConvert.toListVo(dbPlatformSaleStat);
	}

	@Override
	public void removePlatformSaleStatById(int platformSaleStatId) {
		platformSaleStatDao.deleteById(platformSaleStatId);
	}

	@Override
	public Page<PlatformSaleStat> query(Pagination<PlatformSaleStat> query) {
		query.setEntityClazz(PlatformSaleStat.class);
		Page<PlatformSaleStat> page = platformSaleStatDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<PlatformSaleStatListVo> queryListVo(Pagination<PlatformSaleStat> query) {

		Page<PlatformSaleStat> pages = this.query(query);

		List<PlatformSaleStatListVo> vos = platformSaleStatConvert.toListVos(pages.getContent());
		return new PageImpl<PlatformSaleStatListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.platformSaleStatConvert = new EntityListVoBoSimpleConvert<PlatformSaleStat, PlatformSaleStatBo, PlatformSaleStatVo, PlatformSaleStatSimple, PlatformSaleStatListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<PlatformSaleStat, PlatformSaleStatVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<PlatformSaleStat, PlatformSaleStatVo>(beanConvertManager) {
					@Override
					protected void postConvert(PlatformSaleStatVo PlatformSaleStatVo, PlatformSaleStat PlatformSaleStat) {

					}
				};
			}

			@Override
			protected BeanConvert<PlatformSaleStat, PlatformSaleStatListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<PlatformSaleStat, PlatformSaleStatListVo>(beanConvertManager) {
					@Override
					protected void postConvert(PlatformSaleStatListVo PlatformSaleStatListVo, PlatformSaleStat PlatformSaleStat) {

					}
				};
			}

			@Override
			protected BeanConvert<PlatformSaleStat, PlatformSaleStatBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<PlatformSaleStat, PlatformSaleStatBo>(beanConvertManager) {
					@Override
					protected void postConvert(PlatformSaleStatBo PlatformSaleStatBo, PlatformSaleStat PlatformSaleStat) {

					}
				};
			}

			@Override
			protected BeanConvert<PlatformSaleStatBo, PlatformSaleStat> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<PlatformSaleStatBo, PlatformSaleStat>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<PlatformSaleStat, PlatformSaleStatSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<PlatformSaleStat, PlatformSaleStatSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<PlatformSaleStatSimple, PlatformSaleStat> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<PlatformSaleStatSimple, PlatformSaleStat>(beanConvertManager) {
					@Override
					public PlatformSaleStat convert(PlatformSaleStatSimple PlatformSaleStatSimple) throws BeansException {
						return platformSaleStatDao.getOne(PlatformSaleStatSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}

}
