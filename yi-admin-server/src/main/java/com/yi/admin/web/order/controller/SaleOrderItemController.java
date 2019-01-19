/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.order.controller;

import com.yi.core.order.domain.bo.SaleOrderItemBo;
import com.yi.core.order.domain.entity.SaleOrderItem;
import com.yi.core.order.domain.vo.SaleOrderItemListVo;
import com.yi.core.order.domain.vo.SaleOrderItemVo;
import com.yi.core.order.service.ISaleOrderItemService;
//import com.yi.core.utils.ExportExcel;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yihz.common.exception.BusinessException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/saleOrderItem")
public class SaleOrderItemController {

	private final Logger LOG = LoggerFactory.getLogger(SaleOrderItemController.class);

	@Resource
	private ISaleOrderItemService saleOrderItemService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<SaleOrderItemListVo> querySaleOrderItem(@RequestBody Pagination<SaleOrderItem> query) {
		Page<SaleOrderItemListVo> page = saleOrderItemService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addSaleOrderItem(@RequestBody SaleOrderItemBo saleOrderItemBo) {
		try {
			SaleOrderItemListVo saleOrderItemListVo = saleOrderItemService.addSaleOrderItem(saleOrderItemBo);
			return RestUtils.successWhenNotNull(saleOrderItemListVo);
		} catch (BusinessException ex) {
			LOG.error("add SaleOrderItem failure : saleOrderItemBo", saleOrderItemBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateSaleOrderItem(@RequestBody SaleOrderItemBo saleOrderItemBo) {
		try {
			SaleOrderItemListVo saleOrderItemListVo = saleOrderItemService.updateSaleOrderItem(saleOrderItemBo);
			return RestUtils.successWhenNotNull(saleOrderItemListVo);
		} catch (BusinessException ex) {
			LOG.error("update SaleOrderItem failure : saleOrderItemBo", saleOrderItemBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeSaleOrderItemById(@RequestParam("id") int saleOrderItemId) {
		try {
			saleOrderItemService.removeSaleOrderItemById(saleOrderItemId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove SaleOrderItem failure : id={}", saleOrderItemId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getSaleOrderItemVoById(@RequestParam("id") int saleOrderItemId) {
		try {
			SaleOrderItemVo saleOrderItemVo = saleOrderItemService.getSaleOrderItemVoById(saleOrderItemId);
			return RestUtils.successWhenNotNull(saleOrderItemVo);
		} catch (BusinessException ex) {
			LOG.error("get SaleOrderItem failure : id={}", saleOrderItemId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getSaleOrderItemBoById(@RequestParam("id") int saleOrderItemId) {
		try {
			SaleOrderItemBo saleOrderItemBo = saleOrderItemService.getSaleOrderItemBoById(saleOrderItemId);
			return RestUtils.successWhenNotNull(saleOrderItemBo);
		} catch (BusinessException ex) {
			LOG.error("get SaleOrderItem failure : id={}", saleOrderItemId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 根据时间 售卖商品销售统计
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "getSaleStatisticsGrouping", method = RequestMethod.GET)
	public RestResult getSaleStatisticsGrouping(@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) {
		try {
			return RestUtils.success(saleOrderItemService.getSaleStatisticsGrouping(startTime, endTime));
		} catch (Exception ex) {
			LOG.error("销售统计概况异常", ex);
			return RestUtils.error("出错啦！请及时联系管理员: " + ex.getMessage());
		}
	}

	/**
	 * 根据时间 售卖商品销售统计导出Excel数据
	 * 
	 * @param startTime
	 * @param endTime
	 */
	@RequestMapping(value = "exportSaleStatisticsGroupingExcel", method = RequestMethod.GET)
	public void exportSaleStatisticsGroupingExcel(HttpServletResponse response, @RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) {
		// 如果出现中文乱码请添加下面这句
		List<Object[]> saleStatisticsGrouping = saleOrderItemService.getSaleStatisticsGrouping(startTime, endTime);
		String title = "销售统计";
		String[] rowsName = new String[] { "编号", "货品名称", "订单笔数", "总售数", "总销额" };
//		ExportExcel ex = new ExportExcel(title, rowsName, saleStatisticsGrouping, response);
		try {
//			ex.export();
		} catch (Exception e) {
			LOG.error("导出售卖商品销售统计Excel数据失败==》{} ==>{}", e.getMessage(), e);
			// return RestUtils.error("导出售卖商品销售统计Excel数据失败");
		}

	}

}