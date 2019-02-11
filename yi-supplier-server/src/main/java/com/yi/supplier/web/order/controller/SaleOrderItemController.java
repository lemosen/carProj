package com.yi.supplier.web.order.controller;
///*
// * Powered By [yihz-framework]
// * Web Site: yihz
// * Since 2018 - 2018
// */
//
//package com.yi.admin.web.order.controller;
//
//import javax.annotation.Resource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.domain.Page;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.yi.core.order.domain.entity.SaleOrderItem;
//import com.yi.core.order.service.ISaleOrderItemService;
//import com.yihz.common.exception.BusinessException;
//import com.yihz.common.json.RestResult;
//import com.yihz.common.persistence.Pagination;
//import com.yihz.common.utils.web.RestUtils;
//
//import java.util.Date;
//
///**
// *
// * @author lemosen
// * @version 1.0
// * @since 1.0
// **/
//@RestController
//@RequestMapping(value = "/orderItem")
//public class SaleOrderItemController {
//
//	private final Logger LOG = LoggerFactory.getLogger(SaleOrderItemController.class);
//
//	@Resource
//	private ISaleOrderItemService orderItemService;
//
//	/**
//	 * 分页查询
//	 */
//	@RequestMapping(value = "query", method = RequestMethod.POST)
//	public Page<SaleOrderItem> queryOrderItem(@RequestBody Pagination<SaleOrderItem> query) {
//		Page<SaleOrderItem> page = orderItemService.query(query);
//		return page;
//	}
//
//	/**
//	 * 查看对象
//	 **/
//	@RequestMapping(value = "getById", method = RequestMethod.GET)
//	public RestResult viewOrderItem(@RequestParam("id") int orderItemId) {
//		try {
//			SaleOrderItem orderItem = orderItemService.getOrderItemById(orderItemId);
//			return RestUtils.successWhenNotNull(orderItem);
//		} catch (BusinessException ex) {
//			LOG.error("get OrderItem failure : id=orderItemId", ex);
//			return RestUtils.error("get OrderItem failure : " + ex.getMessage());
//		}
//	}
//
//	/**
//	 * 保存新增对象
//	 **/
//	@RequestMapping(value = "add", method = RequestMethod.POST)
//	public RestResult addOrderItem(@RequestBody SaleOrderItem orderItem) {
//		try {
//			SaleOrderItem dbOrderItem = orderItemService.addOrderItem(orderItem);
//			return RestUtils.successWhenNotNull(dbOrderItem);
//		} catch (BusinessException ex) {
//			LOG.error("add OrderItem failure : orderItem", orderItem, ex);
//			return RestUtils.error("add OrderItem failure : " + ex.getMessage());
//		}
//	}
//
//	/**
//	 * 保存更新对象
//	 **/
//	@RequestMapping(value = "update", method = RequestMethod.POST)
//	public RestResult updateOrderItem(@RequestBody SaleOrderItem orderItem) {
//		try {
//			SaleOrderItem dbOrderItem = orderItemService.updateOrderItem(orderItem);
//			return RestUtils.successWhenNotNull(dbOrderItem);
//		} catch (BusinessException ex) {
//			LOG.error("update OrderItem failure : orderItem", orderItem, ex);
//			return RestUtils.error("update OrderItem failure : " + ex.getMessage());
//		}
//	}
//
//	/**
//	 * 删除对象
//	 **/
//	@RequestMapping(value = "removeById", method = RequestMethod.GET)
//	public RestResult removeOrderItemById(@RequestParam("id") int orderItemId) {
//		try {
//			orderItemService.removeOrderItemById(orderItemId);
//			return RestUtils.success(true);
//		} catch (Exception ex) {
//			LOG.error("remove OrderItem failure : id=orderItemId", ex);
//			return RestUtils.error("remove OrderItem failure : " + ex.getMessage());
//		}
//	}
//
//
//}