/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.supplier.web.commodity.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.yi.core.commodity.domain.vo.AttributeGroupListVo;
import com.yi.core.commodity.service.IAttributeGroupService;
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
import com.yi.core.commodity.domain.vo.CommodityListVo;
import com.yi.core.commodity.domain.vo.CommodityVo;
import com.yi.core.commodity.service.ICommodityService;
import com.yi.core.supplier.domain.bo.SupplierBo;
import com.yi.core.supplier.service.ISupplierService;
import com.yi.supplier.web.auth.jwt.JwtSupplierToken;
import com.yi.supplier.web.auth.jwt.SupplierToken;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

import java.util.List;

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
	private ISupplierService supplierService;

	@Resource
	private IAttributeGroupService attributeGroupService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<CommodityListVo> queryCommodity(@RequestBody Pagination<Commodity> query, HttpServletRequest request) {
		SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
		query.getParams().put("supplier.id", supplierToken.getId());
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
			LOG.error("get Commodity failure : id=commodityId", ex);
			return RestUtils.error("get Commodity failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addCommodity(@RequestBody CommodityBo commodity, HttpServletRequest request) {
		try {
			SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
			SupplierBo supplierBo = supplierService.getSupplierBoById(supplierToken.getId());
			commodity.setSupplier(supplierBo);
			CommodityVo dbCommodityVo = commodityService.addCommodity(commodity);
			return RestUtils.successWhenNotNull(dbCommodityVo);
		} catch (BusinessException ex) {
			LOG.error("add Commodity failure : commodity", commodity, ex);
			return RestUtils.error("add Commodity failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateCommodity(@RequestBody CommodityBo commodity, HttpServletRequest request) {
		try {
			SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
			SupplierBo supplierBo = supplierService.getSupplierBoById(supplierToken.getId());
			commodity.setSupplier(supplierBo);
			CommodityVo commodityVo = commodityService.updateCommodity(commodity);
			return RestUtils.successWhenNotNull(commodityVo);
		} catch (BusinessException ex) {
			LOG.error("update Commodity failure : commodity", commodity, ex);
			return RestUtils.error("update Commodity failure : " + ex.getMessage());
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
			LOG.error("remove Commodity failure : id=commodityId", ex);
			return RestUtils.error("remove Commodity failure : " + ex.getMessage());
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
			LOG.error("remove Commodity failure : id=commodityId", ex);
			return RestUtils.error("remove Commodity failure : " + ex.getMessage());
		}
	}

	/**
	 * 商品重新申请上架
	 **/
	@RequestMapping(value = "applyOnTheShelf", method = RequestMethod.GET)
	public RestResult applyOnTheShelf(@RequestParam("commodityId") int commodityId) {
		try {
			commodityService.applyOnTheShelf(commodityId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Commodity failure : id=commodityId", ex);
			return RestUtils.error("remove Commodity failure : " + ex.getMessage());
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
			LOG.error("remove Commodity failure : id=commodityId", ex);
			return RestUtils.error("remove Commodity failure : " + ex.getMessage());
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
			LOG.error("remove Commodity failure : id=commodityId", ex);
			return RestUtils.error("remove Commodity failure : " + ex.getMessage());
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
			LOG.error("add Coupon failure : coupon", ex);
			return RestUtils.error("add Coupon failure : " + ex.getMessage());
		}
	}

}