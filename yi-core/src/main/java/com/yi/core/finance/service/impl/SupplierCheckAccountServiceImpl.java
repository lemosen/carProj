/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.finance.service.impl;

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

import com.yi.core.finance.dao.SupplierCheckAccountDao;
import com.yi.core.finance.domain.bo.SupplierCheckAccountBo;
import com.yi.core.finance.domain.entity.SupplierCheckAccount;
import com.yi.core.finance.domain.entity.SupplierCheckAccount_;
import com.yi.core.finance.domain.simple.SupplierCheckAccountSimple;
import com.yi.core.finance.domain.vo.SupplierCheckAccountListVo;
import com.yi.core.finance.domain.vo.SupplierCheckAccountVo;
import com.yi.core.finance.service.ISupplierCheckAccountService;
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
public class SupplierCheckAccountServiceImpl implements ISupplierCheckAccountService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(SupplierCheckAccountServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private SupplierCheckAccountDao supplierCheckAccountDao;

	private EntityListVoBoSimpleConvert<SupplierCheckAccount, SupplierCheckAccountBo, SupplierCheckAccountVo, SupplierCheckAccountSimple, SupplierCheckAccountListVo> supplierCheckAccountConvert;

	@Override
	public SupplierCheckAccount getSupplierCheckAccountById(int supplierCheckAccountId) {
		return supplierCheckAccountDao.getOne(supplierCheckAccountId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierCheckAccountVo getSupplierCheckAccountVoById(int supplierCheckAccountId) {

		return supplierCheckAccountConvert.toVo(this.supplierCheckAccountDao.getOne(supplierCheckAccountId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierCheckAccountListVo getSupplierCheckAccountListVoById(int supplierCheckAccountId) {
		return supplierCheckAccountConvert.toListVo(this.supplierCheckAccountDao.getOne(supplierCheckAccountId));
	}

	@Override
	public SupplierCheckAccountVo addSupplierCheckAccount(SupplierCheckAccount supplierCheckAccount) {
		return supplierCheckAccountConvert.toVo(supplierCheckAccountDao.save(supplierCheckAccount));
	}

	@Override
	public SupplierCheckAccountListVo addSupplierCheckAccount(SupplierCheckAccountBo supplierCheckAccountBo) {
		return supplierCheckAccountConvert
				.toListVo(supplierCheckAccountDao.save(supplierCheckAccountConvert.toEntity(supplierCheckAccountBo)));
	}

	@Override
	public SupplierCheckAccount updateSupplierCheckAccount(SupplierCheckAccount supplierCheckAccount) {

		SupplierCheckAccount dbSupplierCheckAccount = supplierCheckAccountDao.getOne(supplierCheckAccount.getId());
		AttributeReplication.copying(supplierCheckAccount, dbSupplierCheckAccount,
				 SupplierCheckAccount_.supplierName, 
				SupplierCheckAccount_.saleOrderNo, SupplierCheckAccount_.orderTime, 
				SupplierCheckAccount_.productNo, SupplierCheckAccount_.productName, SupplierCheckAccount_.supplyPrice,
				SupplierCheckAccount_.quantity, SupplierCheckAccount_.settlementAmount,
				SupplierCheckAccount_.settlementTime);
		return dbSupplierCheckAccount;
	}

	@Override
	public SupplierCheckAccountListVo updateSupplierCheckAccount(SupplierCheckAccountBo supplierCheckAccountBo) {
		SupplierCheckAccount dbSupplierCheckAccount = supplierCheckAccountDao.getOne(supplierCheckAccountBo.getId());
		AttributeReplication.copying(supplierCheckAccountBo, dbSupplierCheckAccount,
				 SupplierCheckAccount_.supplierName, 
				SupplierCheckAccount_.saleOrderNo, SupplierCheckAccount_.orderTime, 
				SupplierCheckAccount_.productNo, SupplierCheckAccount_.productName, SupplierCheckAccount_.supplyPrice,
				SupplierCheckAccount_.quantity, SupplierCheckAccount_.settlementAmount,
				SupplierCheckAccount_.settlementTime);
		return supplierCheckAccountConvert.toListVo(dbSupplierCheckAccount);
	}

	@Override
	public void removeSupplierCheckAccountById(int supplierCheckAccountId) {
		supplierCheckAccountDao.deleteById(supplierCheckAccountId);
	}

	@Override
	public Page<SupplierCheckAccount> query(Pagination<SupplierCheckAccount> query) {
		query.setEntityClazz(SupplierCheckAccount.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list1.add(criteriaBuilder.desc(root.get(SupplierCheckAccount_.orderTime)));
		}));
		Page<SupplierCheckAccount> page = supplierCheckAccountDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SupplierCheckAccountListVo> queryListVo(Pagination<SupplierCheckAccount> query) {

		Page<SupplierCheckAccount> pages = this.query(query);

		List<SupplierCheckAccountListVo> vos = supplierCheckAccountConvert.toListVos(pages.getContent());
		return new PageImpl<SupplierCheckAccountListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.supplierCheckAccountConvert = new EntityListVoBoSimpleConvert<SupplierCheckAccount, SupplierCheckAccountBo, SupplierCheckAccountVo, SupplierCheckAccountSimple, SupplierCheckAccountListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<SupplierCheckAccount, SupplierCheckAccountVo> createEntityToVoConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierCheckAccount, SupplierCheckAccountVo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierCheckAccountVo SupplierCheckAccountVo,
							SupplierCheckAccount SupplierCheckAccount) {

					}
				};
			}

			@Override
			protected BeanConvert<SupplierCheckAccount, SupplierCheckAccountListVo> createEntityToListVoConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierCheckAccount, SupplierCheckAccountListVo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierCheckAccountListVo SupplierCheckAccountListVo,
							SupplierCheckAccount SupplierCheckAccount) {

					}
				};
			}

			@Override
			protected BeanConvert<SupplierCheckAccount, SupplierCheckAccountBo> createEntityToBoConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierCheckAccount, SupplierCheckAccountBo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierCheckAccountBo SupplierCheckAccountBo,
							SupplierCheckAccount SupplierCheckAccount) {

					}
				};
			}

			@Override
			protected BeanConvert<SupplierCheckAccountBo, SupplierCheckAccount> createBoToEntityConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierCheckAccountBo, SupplierCheckAccount>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SupplierCheckAccount, SupplierCheckAccountSimple> createEntityToSimpleConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierCheckAccount, SupplierCheckAccountSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SupplierCheckAccountSimple, SupplierCheckAccount> createSimpleToEntityConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierCheckAccountSimple, SupplierCheckAccount>(beanConvertManager) {
					@Override
					public SupplierCheckAccount convert(SupplierCheckAccountSimple SupplierCheckAccountSimple)
							throws BeansException {
						return supplierCheckAccountDao.getOne(SupplierCheckAccountSimple.getId());
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
