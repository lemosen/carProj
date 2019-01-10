package com.yi.webserver.web.order;

import java.util.List;

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
import com.yi.core.order.domain.vo.AfterSaleReasonListVo;
import com.yi.core.order.service.IAfterSaleOrderService;
import com.yi.core.order.service.IAfterSaleReasonService;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 售后服务
 */
@RestController
@RequestMapping("/afterSale")
public class AfterSaleController {

	private final Logger LOG = LoggerFactory.getLogger(AfterSaleController.class);

	@Resource
	private IAfterSaleReasonService afterSaleReasonService;

	@Resource
	private IAfterSaleOrderService afterSaleOrderService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "queryReasons", method = RequestMethod.GET)
	public List<AfterSaleReasonListVo> queryReasons() {
		return afterSaleReasonService.queryAllForApp();
	}

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "queryOrders", method = RequestMethod.POST)
	public Page<AfterSaleOrderListVo> queryOrders(@RequestBody Pagination<AfterSaleOrder> query) {
		Page<AfterSaleOrderListVo> page = afterSaleOrderService.queryListVoForApp(query);
		return page;
	}

	/**
	 * 申请售后
	 * 
	 * @param afterSaleOrderBo
	 * @return
	 */
	@RequestMapping(value = "applyAfterSale", method = RequestMethod.POST)
	public RestResult applyAfterSale(@RequestBody AfterSaleOrderBo afterSaleOrderBo) {
		try {
			afterSaleOrderService.applyAfterSale(afterSaleOrderBo);
			return RestUtils.success(Boolean.TRUE);
		} catch (Exception e) {
			LOG.error("apply AfterSalec error：{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 获取售后详情
	 *
	 * @param saleOrderId
	 * @return
	 */
	@RequestMapping(value = "getAfterSaleDetail", method = RequestMethod.GET)
	public RestResult getAfterSaleDetail(@RequestParam("saleOrderId") int saleOrderId) {
		try {
			return RestUtils.successWhenNotNull(afterSaleOrderService.getVoBySaleOrderId(saleOrderId));
		} catch (Exception e) {
			LOG.error("get AfterSaleDetail error：{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}
}
