package com.yi.webserver.web.activity;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.activity.domain.entity.NationalGroup;
import com.yi.core.activity.domain.vo.NationalGroupListVo;
import com.yi.core.activity.service.INationalGroupRecordService;
import com.yi.core.activity.service.INationalGroupService;
import com.yi.core.order.service.ISaleOrderService;
import com.yi.webserver.web.commodity.CommodityController;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 全国拼团
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/activity")
public class NationalGroupController {
	private final Logger LOG = LoggerFactory.getLogger(CommodityController.class);

	@Resource
	private INationalGroupService nationalGroupService;

	@Resource
	private INationalGroupRecordService nationalGroupRecordService;

	@Resource
	private ISaleOrderService saleOrderService;

	/**
	 * 全国拼团分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<NationalGroupListVo> queryCommodity(@RequestBody Pagination<NationalGroup> query) {
		Page<NationalGroupListVo> page = nationalGroupService.queryListVoApp(query);
		return page;
	}

	/**
	 * 查询我的开团
	 */
	@RequestMapping(value = "myCollage", method = RequestMethod.GET)
	public RestResult myCollage(@RequestParam("memberId") int memberId) {
		try {
			return RestUtils.success(nationalGroupRecordService.myCollage(memberId));
		} catch (Exception e) {
			LOG.error("changePwd error :" + e.getMessage());
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 拼团 货 根据拼团id查询货品
	 */
	@RequestMapping(value = "collageGoods", method = RequestMethod.GET)
	public RestResult collageGoods(@RequestParam("nationalGroupId") int nationalGroupId) {
		try {
			return RestUtils.success(nationalGroupService.getNationalGroupVoById(nationalGroupId));
		} catch (Exception e) {
			LOG.error("changePwd error :" + e.getMessage());
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 根据商品id查已参团货品
	 */
	@RequestMapping(value = "goGroupPurchase", method = RequestMethod.GET)
	public RestResult goGroupPurchase(@RequestParam("commodityId") int commodityId) {
		try {
			return RestUtils.success(nationalGroupService.goGroupPurchase(commodityId));
		} catch (Exception e) {
			LOG.error("changePwd error :" + e.getMessage());
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 昨日已完成
	 */
	@RequestMapping(value = "yesterdayPurchase", method = RequestMethod.GET)
	public RestResult yesterdayPurchase() {
		try {
			return RestUtils.success(nationalGroupService.yesterdayPurchase());
		} catch (Exception e) {
			LOG.error("changePwd error :" + e.getMessage());
			return RestUtils.error(e.getMessage());
		}
	}

}
