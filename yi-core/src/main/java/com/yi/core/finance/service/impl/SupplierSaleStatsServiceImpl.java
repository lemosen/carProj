/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.finance.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import com.yi.core.order.dao.SaleOrderItemDao;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
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

import com.alibaba.fastjson.JSONObject;
import com.yi.core.finance.dao.SupplierSaleStatsDao;
import com.yi.core.finance.domain.bo.SupplierSaleStatsBo;
import com.yi.core.finance.domain.entity.SupplierSaleStats_;
import com.yi.core.finance.domain.entity.SupplierSaleStats;
import com.yi.core.finance.domain.simple.SupplierSaleStatsSimple;
import com.yi.core.finance.domain.vo.SupplierSaleStatsListVo;
import com.yi.core.finance.domain.vo.SupplierSaleStatsVo;
import com.yi.core.finance.service.ISupplierSaleStatsService;
import com.yi.core.order.OrderEnum;
import com.yi.core.order.service.ISaleOrderItemService;
import com.yi.core.order.service.ISaleOrderService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 供应商销售统计
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class SupplierSaleStatsServiceImpl implements ISupplierSaleStatsService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(SupplierSaleStatsServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private SupplierSaleStatsDao supplierSaleStatsDao;

	@Resource
	private ISaleOrderItemService saleOrderItemService;

	@Resource
	private SaleOrderItemDao saleOrderItemDao;

	private EntityListVoBoSimpleConvert<SupplierSaleStats, SupplierSaleStatsBo, SupplierSaleStatsVo, SupplierSaleStatsSimple, SupplierSaleStatsListVo> supplierSaleStatConvert;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierSaleStats getById(int supplierSaleStatId) {
		return supplierSaleStatsDao.getOne(supplierSaleStatId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierSaleStatsVo getVoById(int supplierSaleStatId) {

		return supplierSaleStatConvert.toVo(this.supplierSaleStatsDao.getOne(supplierSaleStatId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierSaleStatsListVo getListVoById(int supplierSaleStatId) {
		return supplierSaleStatConvert.toListVo(this.supplierSaleStatsDao.getOne(supplierSaleStatId));
	}

	@Override
	public SupplierSaleStats addSupplierSaleStat(SupplierSaleStats supplierSaleStats) {
		return supplierSaleStatsDao.save(supplierSaleStats);
	}

	@Override
	public SupplierSaleStatsListVo addSupplierSaleStat(SupplierSaleStatsBo supplierSaleStatsBo) {
		return supplierSaleStatConvert.toListVo(supplierSaleStatsDao.save(supplierSaleStatConvert.toEntity(supplierSaleStatsBo)));
	}

	@Override
	public SupplierSaleStats updateSupplierSaleStat(SupplierSaleStats supplierSaleStats) {
		SupplierSaleStats dbSupplierSaleStat = supplierSaleStatsDao.getOne(supplierSaleStats.getId());
		AttributeReplication.copying(supplierSaleStats, dbSupplierSaleStat, SupplierSaleStats_.supplierOrderNum,
				SupplierSaleStats_.saleAmount, SupplierSaleStats_.supplierRatio,
				SupplierSaleStats_.cost, SupplierSaleStats_.profit, SupplierSaleStats_.statTime);
		return dbSupplierSaleStat;
	}

	@Override
	public SupplierSaleStatsListVo updateSupplierSaleStat(SupplierSaleStatsBo supplierSaleStatsBo) {
		SupplierSaleStats dbSupplierSaleStat = supplierSaleStatsDao.getOne(supplierSaleStatsBo.getId());
		AttributeReplication.copying(supplierSaleStatsBo, dbSupplierSaleStat, SupplierSaleStats_.supplierOrderNum, SupplierSaleStats_.saleAmount, SupplierSaleStats_.supplierRatio,
				SupplierSaleStats_.cost, SupplierSaleStats_.profit, SupplierSaleStats_.statTime);
		return supplierSaleStatConvert.toListVo(dbSupplierSaleStat);
	}

	@Override
	public void removeSupplierSaleStatById(int supplierSaleStatId) {
		supplierSaleStatsDao.deleteById(supplierSaleStatId);
	}

	@Override
	public Page<SupplierSaleStats> query(Pagination<SupplierSaleStats> query) {
		query.setEntityClazz(SupplierSaleStats.class);
		Page<SupplierSaleStats> page = supplierSaleStatsDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SupplierSaleStatsListVo> queryListVo(Pagination<SupplierSaleStats> query) {
		Page<SupplierSaleStats> pages = this.query(query);
		List<SupplierSaleStatsListVo> vos = supplierSaleStatConvert.toListVos(pages.getContent());
		return new PageImpl<SupplierSaleStatsListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.supplierSaleStatConvert = new EntityListVoBoSimpleConvert<SupplierSaleStats, SupplierSaleStatsBo, SupplierSaleStatsVo, SupplierSaleStatsSimple, SupplierSaleStatsListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<SupplierSaleStats, SupplierSaleStatsVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierSaleStats, SupplierSaleStatsVo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierSaleStatsVo SupplierSaleStatsVo, SupplierSaleStats SupplierSaleStats) {

					}
				};
			}

			@Override
			protected BeanConvert<SupplierSaleStats, SupplierSaleStatsListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierSaleStats, SupplierSaleStatsListVo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierSaleStatsListVo SupplierSaleStatsListVo, SupplierSaleStats SupplierSaleStats) {

					}
				};
			}

			@Override
			protected BeanConvert<SupplierSaleStats, SupplierSaleStatsBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierSaleStats, SupplierSaleStatsBo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierSaleStatsBo SupplierSaleStatsBo, SupplierSaleStats SupplierSaleStats) {

					}
				};
			}

			@Override
			protected BeanConvert<SupplierSaleStatsBo, SupplierSaleStats> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierSaleStatsBo, SupplierSaleStats>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SupplierSaleStats, SupplierSaleStatsSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierSaleStats, SupplierSaleStatsSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SupplierSaleStatsSimple, SupplierSaleStats> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierSaleStatsSimple, SupplierSaleStats>(beanConvertManager) {
					@Override
					public SupplierSaleStats convert(SupplierSaleStatsSimple SupplierSaleStatsSimple) throws BeansException {
						return supplierSaleStatsDao.getOne(SupplierSaleStatsSimple.getId());
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
	public SupplierSaleStatsVo getTotalSaleStats() {
		List<Object[]> lists = saleOrderItemService.getTotalSupplierSaleData(OrderEnum.ORDER_STATE_ALREADY_FINISH.getCode());
		SupplierSaleStatsVo supplierSaleStatsVo = new SupplierSaleStatsVo();
		if (CollectionUtils.isNotEmpty(lists)) {
			Object[] tmp = lists.get(0);
			supplierSaleStatsVo.setSupplierOrderNum(Integer.valueOf(String.valueOf(Optional.ofNullable(tmp[0]).orElse("0"))).intValue());
			supplierSaleStatsVo.setSaleAmount(new BigDecimal(String.valueOf(Optional.ofNullable(tmp[1]).orElse(BigDecimal.ZERO))));
			supplierSaleStatsVo.setCost(new BigDecimal(String.valueOf(Optional.ofNullable(tmp[2]).orElse(BigDecimal.ZERO))));
			supplierSaleStatsVo.setProfit(supplierSaleStatsVo.getSaleAmount().subtract(supplierSaleStatsVo.getCost()));
		} else {
			supplierSaleStatsVo.setSaleAmount(BigDecimal.ZERO);
			supplierSaleStatsVo.setCost(BigDecimal.ZERO);
			supplierSaleStatsVo.setProfit(BigDecimal.ZERO);
		}
		return supplierSaleStatsVo;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SupplierSaleStatsListVo> getSaleStatsList(Pagination<SupplierSaleStatsBo> query) throws Exception {
		List<Object[]> result = saleOrderItemService.getSupplierSaleDataByDate(query.getPageRequest(), OrderEnum.ORDER_STATE_ALREADY_FINISH.getCode(),
				query.getEntityClazz().newInstance().getStatTime(), query.getEntityClazz().newInstance().getEndTime());
		List<SupplierSaleStatsListVo> list = new ArrayList<>(0);
		if (CollectionUtils.isNotEmpty(result)) {
			for (Object[] supplierArr : result) {
				if (ArrayUtils.isNotEmpty(supplierArr)) {
					SupplierSaleStatsListVo tmp = new SupplierSaleStatsListVo();
					tmp.setSupplierName(Optional.ofNullable(supplierArr[0]).orElse("").toString());
					tmp.setSupplierOrderNum(Integer.valueOf(String.valueOf(Optional.ofNullable(supplierArr[1]).orElse("0"))).intValue());
					tmp.setSaleAmount(new BigDecimal(String.valueOf(Optional.ofNullable(supplierArr[2]).orElse("0.00"))));
					tmp.setCost(new BigDecimal(String.valueOf(Optional.ofNullable(supplierArr[3]).orElse("0.00"))));
					tmp.setProfit(tmp.getSaleAmount().subtract(tmp.getCost()));
				}
			}
		}
		return list;
	}

	@Override
	public List<Object[]> getSupplierGrouping(String startTime, String endTime){
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			return	saleOrderItemDao.getSupplierGrouping(startTime,endTime,OrderEnum.ORDER_STATE_ALREADY_FINISH.getCode());
		}
		return saleOrderItemDao.getSupplierGrouping(OrderEnum.ORDER_STATE_ALREADY_FINISH.getCode());
	}

	// public FinanceDetail financeStatisticalByCondition(int zoneId, Date
	// startDate, Date endDate) {
	// //获取查询
	// CriteriaBuilder builder = entityManager.getCriteriaBuilder();
	// CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
	// Root<FinancialAccount> root = query.from(FinancialAccount.class);
	//
	// //设置查询结果，统计总收入跟总支出
	// query.multiselect(root.get(FinancialAccount_.recordType),
	// builder.sum(root.get(FinancialAccount_.amount)));
	//
	// //设置查询条件
	// Predicate predicate =
	// builder.equal(root.get(FinancialAccount_.zoneTeacher).get(ZoneTeacher_.schoolZone).get(SchoolZone_.zoneId),
	// zoneId);
	// predicate = builder.and(predicate,
	// builder.greaterThanOrEqualTo(root.get(FinancialAccount_.recordDate),
	// startDate));
	// predicate = builder.and(predicate,
	// builder.lessThanOrEqualTo(root.get(FinancialAccount_.recordDate), endDate));
	// query.where(predicate);
	//
	// //设置分组统计条件，按财务类型即收入或支出进行统计
	// query.groupBy(root.get(FinancialAccount_.recordType));
	//
	// //执行查询并处理结果
	// List<Object[]> resultList = entityManager.createQuery(query).getResultList();
	// if (CollectionUtils.isEmpty(resultList)) {
	// return new FinanceDetail();
	// }
	//
	// //准备实体封装统计结果
	// FinanceDetail financeDetail = new FinanceDetail();
	// for (Object[] result : resultList) {
	// AccountingType recordType = (AccountingType) result[0];
	// double value = ((Number) result[1]).doubleValue();
	// if (recordType == AccountingType.INCOME) {
	// financeDetail.setIncome(value);
	// } else {
	// financeDetail.setSpending(value);
	// }
	// }
	//
	// //计算盈利
	// financeDetail.setGain(financeDetail.getIncome() -
	// financeDetail.getSpending());
	//
	// //返回统计结果
	// return financeDetail;
	// }

}
