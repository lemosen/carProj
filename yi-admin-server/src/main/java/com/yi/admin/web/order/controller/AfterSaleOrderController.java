/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.order.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.order.domain.bo.AfterSaleOrderBo;
import com.yi.core.order.domain.entity.AfterSaleOrder;
import com.yi.core.order.domain.vo.AfterSaleOrderListVo;
import com.yi.core.order.domain.vo.AfterSaleOrderVo;
import com.yi.core.order.service.IAfterSaleOrderService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 售后单
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/afterSaleOrder")
public class AfterSaleOrderController {

	private final Logger LOG = LoggerFactory.getLogger(AfterSaleOrderController.class);

	@Resource
	private IAfterSaleOrderService afterSaleOrderService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<AfterSaleOrderListVo> queryAfterSaleOrder(@RequestBody Pagination<AfterSaleOrder> query) {
		Page<AfterSaleOrderListVo> page = afterSaleOrderService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addAfterSaleOrder(@RequestBody AfterSaleOrderBo afterSaleOrderBo) {
		try {
			AfterSaleOrderListVo afterSaleOrderListVo = afterSaleOrderService.addAfterSaleOrder(afterSaleOrderBo);
			return RestUtils.successWhenNotNull(afterSaleOrderListVo);
		} catch (BusinessException ex) {
			LOG.error("add AfterSaleOrder failure : afterSaleOrderBo", afterSaleOrderBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateAfterSaleOrder(@RequestBody AfterSaleOrderBo afterSaleOrderBo) {
		try {
			AfterSaleOrderListVo afterSaleOrderListVo = afterSaleOrderService.updateAfterSaleOrder(afterSaleOrderBo);
			return RestUtils.successWhenNotNull(afterSaleOrderListVo);
		} catch (BusinessException ex) {
			LOG.error("update AfterSaleOrder failure : afterSaleOrderBo", afterSaleOrderBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeAfterSaleOrderById(@RequestParam("id") int afterSaleOrderId) {
		try {
			afterSaleOrderService.removeAfterSaleOrderById(afterSaleOrderId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove AfterSaleOrder failure : id={}", afterSaleOrderId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getAfterSaleOrderVoById(@RequestParam("id") int afterSaleOrderId) {
		try {
			AfterSaleOrderVo afterSaleOrderVo = afterSaleOrderService.getVoById(afterSaleOrderId);
			return RestUtils.successWhenNotNull(afterSaleOrderVo);
		} catch (BusinessException ex) {
			LOG.error("get AfterSaleOrder failure : id={}", afterSaleOrderId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getAfterSaleOrderBoById(@RequestParam("id") int afterSaleOrderId) {
		try {
			AfterSaleOrderBo afterSaleOrderBo = afterSaleOrderService.getBoById(afterSaleOrderId);
			return RestUtils.successWhenNotNull(afterSaleOrderBo);
		} catch (BusinessException ex) {
			LOG.error("get AfterSaleOrder failure : id={}", afterSaleOrderId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}