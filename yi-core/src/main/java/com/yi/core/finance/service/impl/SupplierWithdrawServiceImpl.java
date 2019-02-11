/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.finance.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.finance.dao.SupplierWithdrawDao;
import com.yi.core.finance.domain.bo.SupplierWithdrawBo;
import com.yi.core.finance.domain.entity.SupplierWithdraw;
import com.yi.core.finance.domain.entity.SupplierWithdraw_;
import com.yi.core.finance.domain.simple.SupplierWithdrawSimple;
import com.yi.core.finance.domain.vo.SupplierWithdrawListVo;
import com.yi.core.finance.domain.vo.SupplierWithdrawVo;
import com.yi.core.finance.service.ISupplierWithdrawService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class SupplierWithdrawServiceImpl implements ISupplierWithdrawService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(SupplierWithdrawServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private SupplierWithdrawDao supplierWithdrawDao;

	private EntityListVoBoSimpleConvert<SupplierWithdraw, SupplierWithdrawBo, SupplierWithdrawVo, SupplierWithdrawSimple, SupplierWithdrawListVo> supplierWithdrawConvert;

	@Override
	public SupplierWithdraw getSupplierWithdrawById(int supplierWithdrawId) {
		return supplierWithdrawDao.getOne(supplierWithdrawId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierWithdrawVo getSupplierWithdrawVoById(int supplierWithdrawId) {

		return supplierWithdrawConvert.toVo(this.supplierWithdrawDao.getOne(supplierWithdrawId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierWithdrawListVo getSupplierWithdrawListVoById(int supplierWithdrawId) {
		return supplierWithdrawConvert.toListVo(this.supplierWithdrawDao.getOne(supplierWithdrawId));
	}

	@Override
	public SupplierWithdrawVo addSupplierWithdraw(SupplierWithdraw supplierWithdraw) {
		return supplierWithdrawConvert.toVo(supplierWithdrawDao.save(supplierWithdraw));
	}

	@Override
	public SupplierWithdrawListVo addSupplierWithdraw(SupplierWithdrawBo supplierWithdrawBo) {
		return supplierWithdrawConvert.toListVo(supplierWithdrawDao.save(supplierWithdrawConvert.toEntity(supplierWithdrawBo)));
	}

	@Override
	public SupplierWithdrawVo updateSupplierWithdraw(SupplierWithdraw supplierWithdraw) {

		SupplierWithdraw dbSupplierWithdraw = supplierWithdrawDao.getOne(supplierWithdraw.getId());
		AttributeReplication.copying(supplierWithdraw, dbSupplierWithdraw, SupplierWithdraw_.amount, SupplierWithdraw_.cardType, SupplierWithdraw_.cardNo, SupplierWithdraw_.payee,
				SupplierWithdraw_.state, SupplierWithdraw_.errorDescription, SupplierWithdraw_.applyTime, SupplierWithdraw_.dealTime);
		return supplierWithdrawConvert.toVo(dbSupplierWithdraw);
	}

	@Override
	public SupplierWithdrawListVo updateSupplierWithdraw(SupplierWithdrawBo supplierWithdrawBo) {
		SupplierWithdraw dbSupplierWithdraw = supplierWithdrawDao.getOne(supplierWithdrawBo.getId());
		AttributeReplication.copying(supplierWithdrawBo, dbSupplierWithdraw, SupplierWithdraw_.guid, SupplierWithdraw_.amount, SupplierWithdraw_.cardType, SupplierWithdraw_.cardNo,
				SupplierWithdraw_.payee, SupplierWithdraw_.state, SupplierWithdraw_.errorDescription, SupplierWithdraw_.applyTime, SupplierWithdraw_.dealTime);
		return supplierWithdrawConvert.toListVo(dbSupplierWithdraw);
	}

	@Override
	public void removeSupplierWithdrawById(int supplierWithdrawId) {
		SupplierWithdraw supplierWithdraw = supplierWithdrawDao.getOne(supplierWithdrawId);
		supplierWithdraw.setState(3);
		supplierWithdraw.setDealTime(new Date());
	}

	/*
	 * 发放
	 */
	@Override
	public void grant(int supplierWithdrawId) {
		/* supplierWithdrawDao.deleteById(supplierWithdrawId); */
		SupplierWithdraw supplierWithdraw = supplierWithdrawDao.getOne(supplierWithdrawId);
		supplierWithdraw.setState(3);
		supplierWithdraw.setDealTime(new Date());
	}

	/*
	 * 发放
	 */
	@Override
	public void reject(int supplierWithdrawId) {
		/* supplierWithdrawDao.deleteById(supplierWithdrawId); */
		SupplierWithdraw supplierWithdraw = supplierWithdrawDao.getOne(supplierWithdrawId);
		supplierWithdraw.setState(2);
		/* supplierWithdraw.setDealTime(new Date()); */
	}

	@Override
	public Page<SupplierWithdraw> query(Pagination<SupplierWithdraw> query) {
		query.setEntityClazz(SupplierWithdraw.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list1.add(criteriaBuilder.desc(root.get(SupplierWithdraw_.applyTime)));
		}));
		Page<SupplierWithdraw> page = supplierWithdrawDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SupplierWithdrawListVo> queryListVo(Pagination<SupplierWithdraw> query) {

		Page<SupplierWithdraw> pages = this.query(query);

		List<SupplierWithdrawListVo> vos = supplierWithdrawConvert.toListVos(pages.getContent());
		return new PageImpl<SupplierWithdrawListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.supplierWithdrawConvert = new EntityListVoBoSimpleConvert<SupplierWithdraw, SupplierWithdrawBo, SupplierWithdrawVo, SupplierWithdrawSimple, SupplierWithdrawListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<SupplierWithdraw, SupplierWithdrawVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierWithdraw, SupplierWithdrawVo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierWithdrawVo SupplierWithdrawVo, SupplierWithdraw SupplierWithdraw) {

					}
				};
			}

			@Override
			protected BeanConvert<SupplierWithdraw, SupplierWithdrawListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierWithdraw, SupplierWithdrawListVo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierWithdrawListVo SupplierWithdrawListVo, SupplierWithdraw SupplierWithdraw) {

					}
				};
			}

			@Override
			protected BeanConvert<SupplierWithdraw, SupplierWithdrawBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierWithdraw, SupplierWithdrawBo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierWithdrawBo SupplierWithdrawBo, SupplierWithdraw SupplierWithdraw) {

					}
				};
			}

			@Override
			protected BeanConvert<SupplierWithdrawBo, SupplierWithdraw> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierWithdrawBo, SupplierWithdraw>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SupplierWithdraw, SupplierWithdrawSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierWithdraw, SupplierWithdrawSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SupplierWithdrawSimple, SupplierWithdraw> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierWithdrawSimple, SupplierWithdraw>(beanConvertManager) {
					@Override
					public SupplierWithdraw convert(SupplierWithdrawSimple SupplierWithdrawSimple) throws BeansException {
						return supplierWithdrawDao.getOne(SupplierWithdrawSimple.getId());
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
	 * 查询申请状态供应商
	 * 
	 * @param state
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int countByState(Integer state) {
		return supplierWithdrawDao.countByState(state);
	}

	/**
	 * 查询供应商待结算资金
	 * 
	 * @param state
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BigDecimal waitSettlement(Integer state) {
		return supplierWithdrawDao.sumAmountByState(state);
	}

	/**
	 * 查询供应商后台提现数量
	 * 
	 * @param supplierId
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int withdrawNum(int supplierId) {
		return supplierWithdrawDao.countBySupplierId(supplierId);
	}
}
