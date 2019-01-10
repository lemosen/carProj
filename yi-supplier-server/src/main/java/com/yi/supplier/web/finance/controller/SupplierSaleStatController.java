/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.supplier.web.finance.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.finance.domain.bo.SupplierSaleStatsBo;
import com.yi.core.finance.domain.entity.SupplierSaleStats;
import com.yi.core.finance.domain.vo.SupplierSaleStatsListVo;
import com.yi.core.finance.domain.vo.SupplierSaleStatsVo;
import com.yi.core.finance.service.ISupplierSaleStatsService;
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
@RequestMapping(value = "/supplierSaleStat")
public class SupplierSaleStatController {

	private final Logger LOG = LoggerFactory.getLogger(SupplierSaleStatController.class);

	@Resource
	private ISupplierSaleStatsService supplierSaleStatsService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<SupplierSaleStatsListVo> querySupplierSaleStat(@RequestBody Pagination<SupplierSaleStats> query) {
		Page<SupplierSaleStatsListVo> page = supplierSaleStatsService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewSupplierSaleStat(@RequestParam("id") int supplierSaleStatId) {
		try {
			SupplierSaleStatsVo supplierSaleStat = supplierSaleStatsService.getVoById(supplierSaleStatId);
			return RestUtils.successWhenNotNull(supplierSaleStat);
		} catch (BusinessException ex) {
			LOG.error("get SupplierSaleStat failure : id=supplierSaleStatId", ex);
			return RestUtils.error("get SupplierSaleStat failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addSupplierSaleStat(@RequestBody SupplierSaleStatsBo supplierSaleStats) {
		try {
			SupplierSaleStatsListVo dbSupplierSaleStat = supplierSaleStatsService.addSupplierSaleStat(supplierSaleStats);
			return RestUtils.successWhenNotNull(dbSupplierSaleStat);
		} catch (BusinessException ex) {
			LOG.error("add SupplierSaleStat failure : supplierSaleStat", supplierSaleStats, ex);
			return RestUtils.error("add SupplierSaleStat failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateSupplierSaleStat(@RequestBody SupplierSaleStatsBo supplierSaleStats) {
		try {
			SupplierSaleStatsListVo dbSupplierSaleStat = supplierSaleStatsService.updateSupplierSaleStat(supplierSaleStats);
			return RestUtils.successWhenNotNull(dbSupplierSaleStat);
		} catch (BusinessException ex) {
			LOG.error("update SupplierSaleStat failure : supplierSaleStat", supplierSaleStats, ex);
			return RestUtils.error("update SupplierSaleStat failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeSupplierSaleStatById(@RequestParam("id") int supplierSaleStatId) {
		try {
			supplierSaleStatsService.removeSupplierSaleStatById(supplierSaleStatId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove SupplierSaleStat failure : id=supplierSaleStatId", ex);
			return RestUtils.error("remove SupplierSaleStat failure : " + ex.getMessage());
		}
	}
}