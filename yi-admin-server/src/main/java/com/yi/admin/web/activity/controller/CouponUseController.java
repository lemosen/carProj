/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.activity.controller;

import javax.annotation.Resource;

import com.yi.core.activity.domain.bo.CouponUseBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.activity.domain.entity.CouponUse;
import com.yi.core.activity.domain.vo.CouponUseListVo;
import com.yi.core.activity.domain.vo.CouponUseVo;
import com.yi.core.activity.service.ICouponUseService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 优惠券使用表
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/couponUse")
public class CouponUseController {

	private final Logger LOG = LoggerFactory.getLogger(CouponUseController.class);

	@Resource
	private ICouponUseService couponUseService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<CouponUseListVo> queryCouponUse(@RequestBody Pagination<CouponUse> query) {
		Page<CouponUseListVo> page = couponUseService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewCouponUse(@RequestParam("id") int couponUseId) {
		try {
			CouponUseVo couponUse = couponUseService.getCouponUseVoById(couponUseId);
			return RestUtils.successWhenNotNull(couponUse);
		} catch (BusinessException ex) {
			LOG.error("get CouponUse failure : id=couponUseId", ex);
			return RestUtils.error("get CouponUse failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addCouponUse(@RequestBody CouponUseBo couponUse) {
		try {
			CouponUseVo dbCouponUse = couponUseService.addCouponUse(couponUse);
			return RestUtils.successWhenNotNull(dbCouponUse);
		} catch (BusinessException ex) {
			LOG.error("add CouponUse failure : couponUse", couponUse, ex);
			return RestUtils.error("add CouponUse failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateCouponUse(@RequestBody CouponUseBo couponUse) {
		try {
			CouponUseVo dbCouponUse = couponUseService.updateCouponUse(couponUse);
			return RestUtils.successWhenNotNull(dbCouponUse);
		} catch (BusinessException ex) {
			LOG.error("update CouponUse failure : couponUse", couponUse, ex);
			return RestUtils.error("update CouponUse failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeCouponUseById(@RequestParam("id") int couponUseId) {
		try {
			couponUseService.removeCouponUseById(couponUseId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove CouponUse failure : id=couponUseId", ex);
			return RestUtils.error("remove CouponUse failure : " + ex.getMessage());
		}
	}
}