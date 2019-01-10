/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.commodity.controller;

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

import com.yi.core.commodity.domain.bo.CommodityBo;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yi.core.commodity.domain.vo.AttributeGroupListVo;
import com.yi.core.commodity.domain.vo.CommodityListVo;
import com.yi.core.commodity.domain.vo.CommodityVo;
import com.yi.core.commodity.service.IAttributeGroupService;
import com.yi.core.commodity.service.ICommodityService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 商品
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/commodity")
public class CommodityController {

	private final Logger LOG = LoggerFactory.getLogger(CommodityController.class);

	@Resource
	private ICommodityService commodityService;

	@Resource
	private IAttributeGroupService attributeGroupService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<CommodityListVo> queryCommodity(@RequestBody Pagination<Commodity> query) {
		Page<CommodityListVo> page = commodityService.queryListVo(query);
		return page;
	}

	/**
	 * 供应商分页查询
	 */
	@RequestMapping(value = "querySupplier", method = RequestMethod.POST)
	public Page<CommodityListVo> querySupplier(@RequestBody Pagination<Commodity> query) {
		Page<CommodityListVo> page = commodityService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewCommodity(@RequestParam("id") int commodityId) {
		try {
			CommodityVo commodity = commodityService.getVoById(commodityId);
			return RestUtils.successWhenNotNull(commodity);
		} catch (BusinessException ex) {
			LOG.error("get Commodity failure : id={}", commodityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addCommodity(@RequestBody CommodityBo commodity) {
		try {
			CommodityVo dbCommodityVo = commodityService.addCommodity(commodity);
			return RestUtils.successWhenNotNull(dbCommodityVo);
		} catch (BusinessException ex) {
			LOG.error("add Commodity failure : {}", commodity, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateCommodity(@RequestBody CommodityBo commodity) {
		try {
			CommodityVo commodityVo = commodityService.updateCommodity(commodity);
			return RestUtils.successWhenNotNull(commodityVo);
		} catch (BusinessException ex) {
			LOG.error("update Commodity failure : {}", commodity, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeCommodityById(@RequestParam("id") int commodityId) {
		try {
			commodityService.removeCommodityById(commodityId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Commodity failure : id={}", commodityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 商品上下架
	 **/
	@RequestMapping(value = "upAndDown", method = RequestMethod.GET)
	public RestResult upAndDown(@RequestParam("commodityId") int commodityId) {
		try {
			commodityService.upAndDown(commodityId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Commodity failure : id={}", commodityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 拒绝审核
	 */
	@RequestMapping(value = "disable", method = RequestMethod.GET)
	public RestResult updateDisable(@RequestParam("id") int commodityId) {
		try {
			return RestUtils.success(commodityService.updateDisable(commodityId));
		} catch (Exception ex) {
			LOG.error("remove Commodity failure : id={}", commodityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 确认审核
	 */
	@RequestMapping(value = "enable", method = RequestMethod.GET)
	public RestResult updateEnable(@RequestParam("id") int commodityId) {
		try {
			return RestUtils.success(commodityService.updateEnable(commodityId));
		} catch (Exception ex) {
			LOG.error("remove Commodity failure : id={}", commodityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查询没有被买送券添加的商品
	 **/
	@RequestMapping(value = "getAll", method = RequestMethod.POST)
	public RestResult getAllNotInId() {
		try {
			List<Commodity> dbCouponListVo = commodityService.getAllNotInId();
			return RestUtils.successWhenNotNull(dbCouponListVo);
		} catch (BusinessException ex) {
			LOG.error("add Coupon failure : {}", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查询属性属性组，属性值
	 * 
	 * @param categoryId
	 * @return
	 */
	@RequestMapping(value = "getAttributeGroupsByCategoryId", method = RequestMethod.GET)
	public RestResult getAttributeGroupsByCategoryId(@RequestParam("categoryId") int categoryId) {
		try {
			List<AttributeGroupListVo> attributeGroup = attributeGroupService.getAttrGroupsByCategory(categoryId);
			return RestUtils.successWhenNotNull(attributeGroup);
		} catch (BusinessException ex) {
			LOG.error("add AttributeGroup failure :", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 批量上架
	 */
	@RequestMapping(value = "batchUpperShelf", method = RequestMethod.POST)
	public RestResult batchUpperShelf(@RequestBody List<Integer> ids) {
		try {
			commodityService.batchUpperShelf(ids);
			return RestUtils.success(true);
		} catch (BusinessException ex) {
			LOG.error("batchUpperShelf failure : ", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 批量下架
	 */
	@RequestMapping(value = "batchLowerShelf", method = RequestMethod.POST)
	public RestResult batchDownShelf(@RequestBody List<Integer> ids) {
		try {
			commodityService.batchLowerShelf(ids);
			return RestUtils.success(true);
		} catch (BusinessException ex) {
			LOG.error("batchDownShelf failure :", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}