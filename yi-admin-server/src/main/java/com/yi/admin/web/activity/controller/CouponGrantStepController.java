/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.activity.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.activity.domain.bo.CouponGrantStepBo;
import com.yi.core.activity.domain.entity.CouponGrantStep;
import com.yi.core.activity.domain.vo.CouponGrantStepListVo;
import com.yi.core.activity.domain.vo.CouponGrantStepVo;
import com.yi.core.activity.service.ICouponGrantStepService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 优惠券发放步骤
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/couponGrantStep")
public class CouponGrantStepController {

	private final Logger LOG = LoggerFactory.getLogger(CouponGrantStepController.class);

	@Resource
	private ICouponGrantStepService couponGrantStepService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<CouponGrantStepListVo> queryCouponGrantStep(@RequestBody Pagination<CouponGrantStep> query) {
		Page<CouponGrantStepListVo> page = couponGrantStepService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addCouponGrantStep(@RequestBody CouponGrantStepBo couponGrantStepBo) {
		try {
			CouponGrantStepListVo couponGrantStepListVo = couponGrantStepService.addCouponGrantStep(couponGrantStepBo);
			return RestUtils.successWhenNotNull(couponGrantStepListVo);
		} catch (BusinessException ex) {
			LOG.error("add CouponGrantStep failure : couponGrantStepBo", couponGrantStepBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateCouponGrantStep(@RequestBody CouponGrantStepBo couponGrantStepBo) {
		try {
			CouponGrantStepListVo couponGrantStepListVo = couponGrantStepService.updateCouponGrantStep(couponGrantStepBo);
			return RestUtils.successWhenNotNull(couponGrantStepListVo);
		} catch (BusinessException ex) {
			LOG.error("update CouponGrantStep failure : couponGrantStepBo", couponGrantStepBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeCouponGrantStepById(@RequestParam("id") int couponGrantStepId) {
		try {
			couponGrantStepService.removeCouponGrantStepById(couponGrantStepId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove CouponGrantStep failure : id={}", couponGrantStepId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getCouponGrantStepVoById(@RequestParam("id") int couponGrantStepId) {
		try {
			CouponGrantStepVo couponGrantStepVo = couponGrantStepService.getCouponGrantStepVoById(couponGrantStepId);
			return RestUtils.successWhenNotNull(couponGrantStepVo);
		} catch (BusinessException ex) {
			LOG.error("get CouponGrantStep failure : id={}", couponGrantStepId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getCouponGrantStepBoById(@RequestParam("id") int couponGrantStepId) {
		try {
			CouponGrantStepBo couponGrantStepBo = couponGrantStepService.getCouponGrantStepBoById(couponGrantStepId);
			return RestUtils.successWhenNotNull(couponGrantStepBo);
		} catch (BusinessException ex) {
			LOG.error("get CouponGrantStep failure : id={}", couponGrantStepId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}