/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.member.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.member.domain.bo.AccountBo;
import com.yi.core.member.domain.entity.Account;
import com.yi.core.member.domain.vo.AccountListVo;
import com.yi.core.member.domain.vo.AccountVo;
import com.yi.core.member.service.IAccountService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 会员资金账户
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/account")
public class AccountController {

	private final Logger LOG = LoggerFactory.getLogger(AccountController.class);

	@Resource
	private IAccountService accountService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<AccountListVo> queryAccount(@RequestBody Pagination<Account> query) {
		Page<AccountListVo> page = accountService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewAccount(@RequestParam("id") int accountId) {
		try {
			AccountVo account = accountService.getVoById(accountId);
			return RestUtils.successWhenNotNull(account);
		} catch (BusinessException ex) {
			LOG.error("get Account failure : id=accountId", ex);
			return RestUtils.error("get Account failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addAccount(@RequestBody AccountBo account) {
		try {
			AccountVo dbAccount = accountService.addAccount(account);
			return RestUtils.successWhenNotNull(dbAccount);
		} catch (BusinessException ex) {
			LOG.error("add Account failure : account", account, ex);
			return RestUtils.error("add Account failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateAccount(@RequestBody AccountBo account) {
		try {
			AccountVo dbAccount = accountService.updateAccount(account);
			return RestUtils.successWhenNotNull(dbAccount);
		} catch (BusinessException ex) {
			LOG.error("update Account failure : account", account, ex);
			return RestUtils.error("update Account failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeAccountById(@RequestParam("id") int accountId) {
		try {
			accountService.removeAccountById(accountId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Account failure : id=accountId", ex);
			return RestUtils.error("remove Account failure : " + ex.getMessage());
		}
	}
}