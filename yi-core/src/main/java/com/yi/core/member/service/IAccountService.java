/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;

import com.yi.core.member.MemberEnum;
import com.yi.core.member.domain.bo.AccountBo;
import com.yi.core.member.domain.entity.Account;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.domain.vo.AccountListVo;
import com.yi.core.member.domain.vo.AccountVo;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yihz.common.persistence.Pagination;

/**
 * 会员资金账户
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
public interface IAccountService {

	/**
	 * 根据ID得到Account
	 * 
	 * @param accountId
	 * @return
	 */
	Account getById(int accountId);

	/**
	 * 根据ID得到AccountVo
	 * 
	 * @param accountId
	 * @return
	 */
	AccountVo getVoById(int accountId);

	/**
	 * 根据ID得到AccountListVo
	 * 
	 * @param accountId
	 * @return
	 */
	AccountListVo getListVoById(int accountId);

	/**
	 * 根据Entity创建Account
	 * 
	 * @param account
	 * @return
	 */
	Account addAccount(Account account);

	/**
	 * 根据BO创建Account
	 * 
	 * @param accountBo
	 * @return
	 */
	AccountVo addAccount(AccountBo accountBo);

	/**
	 * 根据Entity更新Account
	 * 
	 * @param account
	 * @return
	 */
	Account updateAccount(Account account);

	/**
	 * 根据BO更新Account
	 * 
	 * @param accountBo
	 * @return
	 */
	AccountVo updateAccount(AccountBo accountBo);

	/**
	 * 删除Account
	 * 
	 * @param accountId
	 */
	void removeAccountById(int accountId);

	/**
	 * 分页查询: Account
	 * 
	 * @param query
	 * @return
	 */
	Page<Account> query(Pagination<Account> query);

	/**
	 * 分页查询: AccountListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<AccountListVo> queryListVo(Pagination<Account> query);

	/**
	 * 通过会员查询会员账户信息
	 * 
	 * @return
	 */
	AccountVo getAccountVoByMember(Integer memberId);

	/**
	 * 更新会员佣金并记录
	 * 
	 * @param memberId
	 * @param commission
	 * @param tradeType
	 */
	void updateMemberCommission(SaleOrder saleOrder, Integer memberId, BigDecimal commission, MemberEnum tradeType, Member contributor);

	/**
	 * 更新可提现佣金
	 * 
	 * @param saleOrder
	 * @param memberId
	 * @param commission
	 * @param tradeType
	 * @param contributor
	 */
	void updateCashableCommission(SaleOrder saleOrder, Integer memberId, BigDecimal commission, MemberEnum tradeType, Member contributor);

	/**
	 * 更新未结算佣金
	 * 
	 * @param saleOrder
	 * @param memberId
	 * @param commission
	 * @param tradeType
	 * @param contributor
	 */
	void updateUnsettledCommissionCommission(SaleOrder saleOrder, Integer memberId, BigDecimal commission, MemberEnum tradeType, Member contributor);

}
