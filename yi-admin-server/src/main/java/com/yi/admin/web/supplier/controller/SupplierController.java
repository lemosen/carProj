/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.supplier.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.stats.domain.vo.SupplierDataVo;
import com.yi.core.stats.service.ISupplierStatsService;
import com.yi.core.supplier.domain.bo.SupplierBo;
import com.yi.core.supplier.domain.entity.Supplier;
import com.yi.core.supplier.domain.vo.SupplierListVo;
import com.yi.core.supplier.domain.vo.SupplierVo;
import com.yi.core.supplier.service.ISupplierService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 供应商
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/supplier")
public class SupplierController {

	private final Logger LOG = LoggerFactory.getLogger(SupplierController.class);

	@Resource
	private ISupplierService supplierService;

	@Resource
	private ISupplierStatsService supplierStatsService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<SupplierListVo> querySupplier(@RequestBody Pagination<Supplier> query) {
		Page<SupplierListVo> page = supplierService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewSupplier(@RequestParam("id") int supplierId) {
		try {
			SupplierVo supplier = supplierService.getSupplierVoById(supplierId);
			return RestUtils.successWhenNotNull(supplier);
		} catch (BusinessException ex) {
			LOG.error("get Supplier failure : id=supplierId", ex);
			return RestUtils.error("get Supplier failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addSupplier(@RequestBody SupplierBo supplier) {
		try {
			SupplierVo supplierListVo = supplierService.addSupplier(supplier);
			return RestUtils.successWhenNotNull(supplierListVo);
		} catch (BusinessException ex) {
			LOG.error("add Supplier failure : supplier", supplier, ex);
			return RestUtils.error("add Supplier failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateSupplier(@RequestBody SupplierBo supplier) {
		try {
			SupplierVo supplierVo = supplierService.updateSupplier(supplier);
			return RestUtils.successWhenNotNull(supplierVo);
		} catch (BusinessException ex) {
			LOG.error("update Supplier failure : supplier", supplier, ex);
			return RestUtils.error("update Supplier failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeSupplierById(@RequestParam("id") int supplierId) {
		try {
			supplierService.removeSupplierById(supplierId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Supplier failure : id=supplierId", ex);
			return RestUtils.error("remove Supplier failure : " + ex.getMessage());
		}
	}

	@RequestMapping(value = "updateShelf", method = RequestMethod.GET)
	public RestResult updateShelf(@RequestParam("supplierId") int supplierId) {
		try {
			SupplierVo supplier = supplierService.banKai(supplierId);
			return RestUtils.success(supplier);
		} catch (Exception ex) {
			LOG.error("remove Recommend failure : id=recommendId", ex);
			return RestUtils.error("remove Recommend failure : " + ex.getMessage());
		}
	}

	/**
	 * 供应商 概况
	 * 
	 * @return
	 */
	@RequestMapping(value = "querySupplierData", method = RequestMethod.GET)
	public RestResult querySupplierData() {
		try {
			SupplierDataVo supplierDataVo = supplierStatsService.getSupplierData();
			return RestUtils.successWhenNotNull(supplierDataVo);
		} catch (BusinessException ex) {
			LOG.error("query SupplierData failure : ", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}