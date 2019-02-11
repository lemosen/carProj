/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.supplier.web.activity.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.activity.domain.bo.CouponBo;
import com.yi.core.activity.domain.entity.Coupon;
import com.yi.core.activity.domain.vo.CouponListVo;
import com.yi.core.activity.domain.vo.CouponVo;
import com.yi.core.activity.service.ICouponService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 优惠券
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/coupon")
public class CouponController {

	private final Logger LOG = LoggerFactory.getLogger(CouponController.class);

	@Resource
	private ICouponService couponService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<CouponListVo> queryCoupon(@RequestBody Pagination<Coupon> query) {
		Page<CouponListVo> page = couponService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewCoupon(@RequestParam("id") int couponId) {
		try {
			CouponVo couponVo = couponService.getVoById(couponId);
			return RestUtils.successWhenNotNull(couponVo);
		} catch (BusinessException ex) {
			LOG.error("get Coupon failure : id={}", couponId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addCoupon(@RequestBody CouponBo coupon) {
		try {
			CouponListVo dbCouponListVo = couponService.addCoupon(coupon);
			return RestUtils.successWhenNotNull(dbCouponListVo);
		} catch (BusinessException ex) {
			LOG.error("add Coupon failure : {}", coupon, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateCoupon(@RequestBody CouponBo coupon) {
		try {
			CouponListVo dbCouponListVo = couponService.updateCoupon(coupon);
			return RestUtils.successWhenNotNull(dbCouponListVo);
		} catch (BusinessException ex) {
			LOG.error("update Coupon failure : {}", coupon, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeCouponById(@RequestParam("id") int couponId) {
		try {
			couponService.removeCouponById(couponId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Coupon failure : id={}", couponId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}
}