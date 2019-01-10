/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.supplier.web.finance.controller;

import javax.annotation.Resource;

import com.yi.core.finance.domain.vo.SupplierWithdrawListVo;
import com.yi.core.finance.domain.vo.SupplierWithdrawVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.finance.domain.entity.SupplierWithdraw;
import com.yi.core.finance.service.ISupplierWithdrawService;
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
@RequestMapping(value = "/supplierWithdraw")
public class SupplierWithdrawController {

	private final Logger LOG = LoggerFactory.getLogger(SupplierWithdrawController.class);

	@Resource
	private ISupplierWithdrawService supplierWithdrawService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<SupplierWithdrawListVo> querySupplierWithdraw(@RequestBody Pagination<SupplierWithdraw> query) {
		Page<SupplierWithdrawListVo> page = supplierWithdrawService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewSupplierWithdraw(@RequestParam("id") int supplierWithdrawId) {
		try {
			SupplierWithdrawVo supplierWithdraw = supplierWithdrawService.getSupplierWithdrawVoById(supplierWithdrawId);
			return RestUtils.successWhenNotNull(supplierWithdraw);
		} catch (BusinessException ex) {
			LOG.error("get SupplierWithdraw failure : id=supplierWithdrawId", ex);
			return RestUtils.error("get SupplierWithdraw failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addSupplierWithdraw(@RequestBody SupplierWithdraw supplierWithdraw) {
		try {
			SupplierWithdrawVo dbSupplierWithdraw = supplierWithdrawService.addSupplierWithdraw(supplierWithdraw);
			return RestUtils.successWhenNotNull(dbSupplierWithdraw);
		} catch (BusinessException ex) {
			LOG.error("add SupplierWithdraw failure : supplierWithdraw", supplierWithdraw, ex);
			return RestUtils.error("add SupplierWithdraw failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateSupplierWithdraw(@RequestBody SupplierWithdraw supplierWithdraw) {
		try {
			SupplierWithdrawVo dbSupplierWithdraw = supplierWithdrawService.updateSupplierWithdraw(supplierWithdraw);
			return RestUtils.successWhenNotNull(dbSupplierWithdraw);
		} catch (BusinessException ex) {
			LOG.error("update SupplierWithdraw failure : supplierWithdraw", supplierWithdraw, ex);
			return RestUtils.error("update SupplierWithdraw failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeSupplierWithdrawById(@RequestParam("id") int supplierWithdrawId) {
		try {
			supplierWithdrawService.removeSupplierWithdrawById(supplierWithdrawId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove SupplierWithdraw failure : id=supplierWithdrawId", ex);
			return RestUtils.error("remove SupplierWithdraw failure : " + ex.getMessage());
		}
	}

	/*
	 * 发放
	 */
	@RequestMapping(value = "grant", method = RequestMethod.GET)
	public RestResult grant(@RequestParam("id") int supplierWithdrawId) {
		try {
			supplierWithdrawService.grant(supplierWithdrawId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove SupplierWithdraw failure : id=supplierWithdrawId", ex);
			return RestUtils.error("remove SupplierWithdraw failure : " + ex.getMessage());
		}
	}

	/*
	 * 驳回
	 */
	@RequestMapping(value = "reject", method = RequestMethod.GET)
	public RestResult reject(@RequestParam("id") int supplierWithdrawId) {
		try {
			supplierWithdrawService.reject(supplierWithdrawId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove SupplierWithdraw failure : id=supplierWithdrawId", ex);
			return RestUtils.error("remove SupplierWithdraw failure : " + ex.getMessage());
		}
	}

	/*
	 * grant(id){ return this.getByParams("grant", {id:id}); }
	 * 
	 *//*
		 * 驳回
		 *//*
			 * reject(id){ return this.getByParams("reject", {id:id}); }
			 */

}