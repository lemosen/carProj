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

import com.yi.core.member.domain.bo.AccountRecordBo;
import com.yi.core.member.domain.entity.AccountRecord;
import com.yi.core.member.domain.vo.AccountRecordListVo;
import com.yi.core.member.domain.vo.AccountRecordVo;
import com.yi.core.member.service.IAccountRecordService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 会员资金账户记录
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/accountRecord")
public class AccountRecordController {

	private final Logger LOG = LoggerFactory.getLogger(AccountRecordController.class);

	@Resource
	private IAccountRecordService accountRecordService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<AccountRecordListVo> queryAccountRecord(@RequestBody Pagination<AccountRecord> query) {
		Page<AccountRecordListVo> page = accountRecordService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewAccountRecord(@RequestParam("id") int accountRecordId) {
		try {
			AccountRecordVo accountRecord = accountRecordService.getVoById(accountRecordId);
			return RestUtils.successWhenNotNull(accountRecord);
		} catch (BusinessException ex) {
			LOG.error("get AccountRecord failure : id=accountRecordId", ex);
			return RestUtils.error("get AccountRecord failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addAccountRecord(@RequestBody AccountRecordBo accountRecord) {
		try {
			AccountRecordVo dbAccountRecord = accountRecordService.addAccountRecord(accountRecord);
			return RestUtils.successWhenNotNull(dbAccountRecord);
		} catch (BusinessException ex) {
			LOG.error("add AccountRecord failure : accountRecord", accountRecord, ex);
			return RestUtils.error("add AccountRecord failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateAccountRecord(@RequestBody AccountRecordBo accountRecord) {
		try {
			AccountRecordVo dbAccountRecord = accountRecordService.updateAccountRecord(accountRecord);
			return RestUtils.successWhenNotNull(dbAccountRecord);
		} catch (BusinessException ex) {
			LOG.error("update AccountRecord failure : accountRecord", accountRecord, ex);
			return RestUtils.error("update AccountRecord failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeAccountRecordById(@RequestParam("id") int accountRecordId) {
		try {
			accountRecordService.removeAccountRecordById(accountRecordId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove AccountRecord failure : id=accountRecordId", ex);
			return RestUtils.error("remove AccountRecord failure : " + ex.getMessage());
		}
	}

}