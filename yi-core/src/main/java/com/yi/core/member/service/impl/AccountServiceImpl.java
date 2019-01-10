/*
* Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

import com.yi.core.member.MemberEnum;
import com.yi.core.member.dao.AccountDao;
import com.yi.core.member.domain.bo.AccountBo;
import com.yi.core.member.domain.entity.Account;
import com.yi.core.member.domain.entity.Account_;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.domain.simple.AccountSimple;
import com.yi.core.member.domain.vo.AccountListVo;
import com.yi.core.member.domain.vo.AccountVo;
import com.yi.core.member.service.IAccountRecordService;
import com.yi.core.member.service.IAccountService;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.service.ISaleOrderService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 会员资金账户
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class AccountServiceImpl implements IAccountService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private AccountDao accountDao;

	@Resource
	private IAccountRecordService accountRecordService;

	@Resource
	private ISaleOrderService saleOrderService;

	private EntityListVoBoSimpleConvert<Account, AccountBo, AccountVo, AccountSimple, AccountListVo> accountConvert;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Account getById(int accountId) {
		return accountDao.getOne(accountId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AccountVo getVoById(int accountId) {
		return accountConvert.toVo(this.accountDao.getOne(accountId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AccountListVo getListVoById(int accountId) {
		return accountConvert.toListVo(this.accountDao.getOne(accountId));
	}

	@Override
	public Account addAccount(Account account) {
		return accountDao.save(account);
	}

	@Override
	public AccountVo addAccount(AccountBo accountBo) {
		return accountConvert.toVo(accountDao.save(accountConvert.toEntity(accountBo)));
	}

	@Override
	public Account updateAccount(Account account) {
		Account dbAccount = accountDao.getOne(account.getId());
		AttributeReplication.copying(account, dbAccount, Account_.orderQuantity, Account_.consumeAmount, Account_.balance, Account_.freezeAmount, Account_.integral,
				Account_.residualIntegral, Account_.totalCommission, Account_.cashableCommission, Account_.cashedCommission, Account_.unsettledCommission, Account_.remark);
		return dbAccount;
	}

	@Override
	public AccountVo updateAccount(AccountBo accountBo) {
		Account dbAccount = this.updateAccount(accountConvert.toEntity(accountBo));
		return accountConvert.toVo(dbAccount);
	}

	@Override
	public void removeAccountById(int accountId) {
		accountDao.deleteById(accountId);
	}

	@Override
	public Page<Account> query(Pagination<Account> query) {
		query.setEntityClazz(Account.class);
		Page<Account> page = accountDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<AccountListVo> queryListVo(Pagination<Account> query) {

		Page<Account> pages = this.query(query);

		List<AccountListVo> vos = accountConvert.toListVos(pages.getContent());
		return new PageImpl<AccountListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.accountConvert = new EntityListVoBoSimpleConvert<Account, AccountBo, AccountVo, AccountSimple, AccountListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Account, AccountVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Account, AccountVo>(beanConvertManager) {
					@Override
					protected void postConvert(AccountVo AccountVo, Account Account) {

					}
				};
			}

			@Override
			protected BeanConvert<Account, AccountListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Account, AccountListVo>(beanConvertManager) {
					@Override
					protected void postConvert(AccountListVo AccountListVo, Account Account) {

					}
				};
			}

			@Override
			protected BeanConvert<Account, AccountBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Account, AccountBo>(beanConvertManager) {
					@Override
					protected void postConvert(AccountBo AccountBo, Account Account) {

					}
				};
			}

			@Override
			protected BeanConvert<AccountBo, Account> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AccountBo, Account>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Account, AccountSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Account, AccountSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<AccountSimple, Account> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AccountSimple, Account>(beanConvertManager) {
					@Override
					public Account convert(AccountSimple AccountSimple) throws BeansException {
						return accountDao.getOne(AccountSimple.getId());
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
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AccountVo getAccountVoByMember(Integer memberId) {
		return accountConvert.toVo(accountDao.findByMember_id(memberId));
	}

	/**
	 * 更新会员佣金并记录
	 */
	@Override
	public void updateMemberCommission(SaleOrder saleOrder, Integer memberId, BigDecimal commission, MemberEnum tradeType, Member contributor) {
		if (memberId != null && commission != null && commission.compareTo(BigDecimal.ZERO) > 0 && tradeType != null) {
			Account dbAccount = accountDao.findByMember_id(memberId);
			if (dbAccount != null) {
				// 冻结金额
				dbAccount.setFreezeAmount(Optional.ofNullable(dbAccount.getFreezeAmount()).orElse(BigDecimal.ZERO).add(commission));
				// 总佣金
				dbAccount.setTotalCommission(Optional.ofNullable(dbAccount.getTotalCommission()).orElse(BigDecimal.ZERO).add(commission));
				// 未结算佣金
				dbAccount.setUnsettledCommission(Optional.ofNullable(dbAccount.getUnsettledCommission()).orElse(BigDecimal.ZERO).add(commission));
				// 用户资金账户记录
				accountRecordService.addAccountRecordByTradeType(saleOrder, dbAccount.getMember(), commission, tradeType, contributor);
			}
		}
	}

	/**
	 * 更新会员可提现佣金并记录
	 */
	@Override
	public void updateCashableCommission(SaleOrder saleOrder, Integer memberId, BigDecimal commission, MemberEnum tradeType, Member contributor) {
		if (memberId != null && commission != null && commission.compareTo(BigDecimal.ZERO) > 0 && tradeType != null) {
			Account dbAccount = accountDao.findByMember_id(memberId);
			if (dbAccount != null) {
				// 冻结金额
				// dbAccount.setFreezeAmount(Optional.ofNullable(dbAccount.getFreezeAmount()).orElse(BigDecimal.ZERO).add(commission));
				// 总佣金
				// dbAccount.setTotalCommission(Optional.ofNullable(dbAccount.getTotalCommission()).orElse(BigDecimal.ZERO).add(commission));
				// 可提现佣金
				dbAccount.setCashableCommission(Optional.ofNullable(dbAccount.getCashableCommission()).orElse(BigDecimal.ZERO).add(commission));
				// 未结算佣金
				dbAccount.setUnsettledCommission(Optional.ofNullable(dbAccount.getUnsettledCommission()).orElse(BigDecimal.ZERO).subtract(commission));
				// 用户资金账户记录
				accountRecordService.addAccountRecordByTradeType(saleOrder, dbAccount.getMember(), commission, tradeType, contributor);
			}
		}
	}

	/**
	 * 更新会员未结算佣金并记录
	 */
	@Override
	public void updateUnsettledCommissionCommission(SaleOrder saleOrder, Integer memberId, BigDecimal commission, MemberEnum tradeType, Member contributor) {
		if (memberId != null && commission != null && commission.compareTo(BigDecimal.ZERO) > 0 && tradeType != null) {
			Account dbAccount = accountDao.findByMember_id(memberId);
			if (dbAccount != null) {
				// 冻结金额
				// dbAccount.setFreezeAmount(Optional.ofNullable(dbAccount.getFreezeAmount()).orElse(BigDecimal.ZERO).add(commission));
				// 总佣金
				dbAccount.setTotalCommission(Optional.ofNullable(dbAccount.getTotalCommission()).orElse(BigDecimal.ZERO).subtract(commission));
				// 可提现佣金
				// dbAccount.setCashableCommission(Optional.ofNullable(dbAccount.getCashableCommission()).orElse(BigDecimal.ZERO).add(commission));
				// 未结算佣金
				dbAccount.setUnsettledCommission(Optional.ofNullable(dbAccount.getUnsettledCommission()).orElse(BigDecimal.ZERO).subtract(commission));
				// 用户资金账户记录
				accountRecordService.addAccountRecordByTradeType(saleOrder, dbAccount.getMember(), commission, tradeType, contributor);
			}
		}

	}

}
