/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.supplier.web.finance.controller;

import javax.annotation.Resource;

import com.yi.core.finance.domain.vo.SupplierCheckAccountListVo;
import com.yi.core.finance.domain.vo.SupplierCheckAccountVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.finance.domain.entity.SupplierCheckAccount;
import com.yi.core.finance.service.ISupplierCheckAccountService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/supplierCheckAccount")
public class SupplierCheckAccountController {

	private final Logger LOG = LoggerFactory.getLogger(SupplierCheckAccountController.class);

	@Resource
	private ISupplierCheckAccountService supplierCheckAccountService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<SupplierCheckAccountListVo> querySupplierCheckAccount(
			@RequestBody Pagination<SupplierCheckAccount> query) {
		Page<SupplierCheckAccountListVo> page = supplierCheckAccountService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewSupplierCheckAccount(@RequestParam("id") int supplierCheckAccountId) {
		try {
			SupplierCheckAccountVo supplierCheckAccount = supplierCheckAccountService
					.getSupplierCheckAccountVoById(supplierCheckAccountId);
			return RestUtils.successWhenNotNull(supplierCheckAccount);
		} catch (BusinessException ex) {
			LOG.error("get SupplierCheckAccount failure : id=supplierCheckAccountId", ex);
			return RestUtils.error("get SupplierCheckAccount failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addSupplierCheckAccount(@RequestBody SupplierCheckAccount supplierCheckAccount) {
		try {
			SupplierCheckAccountVo dbSupplierCheckAccount = supplierCheckAccountService
					.addSupplierCheckAccount(supplierCheckAccount);
			return RestUtils.successWhenNotNull(dbSupplierCheckAccount);
		} catch (BusinessException ex) {
			LOG.error("add SupplierCheckAccount failure : supplierCheckAccount", supplierCheckAccount, ex);
			return RestUtils.error("add SupplierCheckAccount failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateSupplierCheckAccount(@RequestBody SupplierCheckAccount supplierCheckAccount) {
		try {
			SupplierCheckAccount dbSupplierCheckAccount = supplierCheckAccountService
					.updateSupplierCheckAccount(supplierCheckAccount);
			return RestUtils.successWhenNotNull(dbSupplierCheckAccount);
		} catch (BusinessException ex) {
			LOG.error("update SupplierCheckAccount failure : supplierCheckAccount", supplierCheckAccount, ex);
			return RestUtils.error("update SupplierCheckAccount failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeSupplierCheckAccountById(@RequestParam("id") int supplierCheckAccountId) {
		try {
			supplierCheckAccountService.removeSupplierCheckAccountById(supplierCheckAccountId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove SupplierCheckAccount failure : id=supplierCheckAccountId", ex);
			return RestUtils.error("remove SupplierCheckAccount failure : " + ex.getMessage());
		}
	}
}