/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.finance.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.yi.core.finance.domain.bo.SupplierSaleStatsBo;
import com.yi.core.finance.domain.entity.SupplierSaleStats;
import com.yi.core.finance.domain.vo.SupplierSaleStatsListVo;
import com.yi.core.finance.service.ISupplierSaleStatsService;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 供应商销售统计
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/supplierSaleStats")
public class SupplierSaleStatsController {

	private final Logger LOG = LoggerFactory.getLogger(SupplierSaleStatsController.class);

	@Resource
	private ISupplierSaleStatsService supplierSaleStatsService;

	/**
	 * 分页查询
	 */
//	@RequestMapping(value = "query", method = RequestMethod.POST)
//	public Page<SupplierSaleStatsListVo> querySupplierSaleStat(@RequestBody Pagination<SupplierSaleStats> query) {
//		Page<SupplierSaleStatsListVo> page = supplierSaleStatsService.queryListVo(query);
//		return page;
//	}

	/**
	 * 查询供应商销售合计数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "getTotalSaleStats", method = RequestMethod.GET)
	public RestResult getTotalSaleStats() {
		try {
			return RestUtils.success(supplierSaleStatsService.getTotalSaleStats());
		} catch (Exception ex) {
			LOG.error("getTotalSaleStats has exception", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查询供应商销售集合
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public RestResult getSaleStatsList(@RequestBody Pagination<SupplierSaleStatsBo> query) {
		try {
			return RestUtils.success(supplierSaleStatsService.getSaleStatsList(query));
		} catch (Exception ex) {
			LOG.error("getSaleStatsList has exception", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 根据时间 查询分组供应商数据
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "getSupplierGrouping", method = RequestMethod.GET)
	public RestResult getSupplierGrouping(@RequestParam(value = "startTime", required = false) String startTime, @RequestParam(value = "endTime", required = false) String endTime) {
		try {
			return RestUtils.success(supplierSaleStatsService.getSupplierGrouping(startTime, endTime));
		} catch (Exception ex) {
			LOG.error("供应商概况异常", ex);
			return RestUtils.error("出错啦！请及时联系管理员: " + ex.getMessage());
		}
	}

}