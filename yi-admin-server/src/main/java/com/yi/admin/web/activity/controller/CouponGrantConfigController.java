/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.activity.controller;

import javax.annotation.Resource;

import com.yi.core.member.domain.vo.MemberVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.activity.domain.bo.CouponGrantConfigBo;
import com.yi.core.activity.domain.entity.CouponGrantConfig;
import com.yi.core.activity.domain.vo.CouponGrantConfigListVo;
import com.yi.core.activity.domain.vo.CouponGrantConfigVo;
import com.yi.core.activity.service.ICouponGrantConfigService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 优惠券发放方案
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/couponGrantConfig")
public class CouponGrantConfigController {

	private final Logger LOG = LoggerFactory.getLogger(CouponGrantConfigController.class);

	@Resource
	private ICouponGrantConfigService couponGrantConfigService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<CouponGrantConfigListVo> queryCouponGrantConfig(@RequestBody Pagination<CouponGrantConfig> query) {
		Page<CouponGrantConfigListVo> page = couponGrantConfigService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addCouponGrantConfig(@RequestBody CouponGrantConfigBo couponGrantConfigBo) {
		try {
			CouponGrantConfigListVo couponGrantConfigListVo = couponGrantConfigService.addCouponGrantConfig(couponGrantConfigBo);
			return RestUtils.successWhenNotNull(couponGrantConfigListVo);
		} catch (BusinessException ex) {
			LOG.error("add CouponGrantConfig failure : couponGrantConfigBo", couponGrantConfigBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateCouponGrantConfig(@RequestBody CouponGrantConfigBo couponGrantConfigBo) {
		try {
			CouponGrantConfigListVo couponGrantConfigListVo = couponGrantConfigService.updateCouponGrantConfig(couponGrantConfigBo);
			return RestUtils.successWhenNotNull(couponGrantConfigListVo);
		} catch (BusinessException ex) {
			LOG.error("update CouponGrantConfig failure : couponGrantConfigBo", couponGrantConfigBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeCouponGrantConfigById(@RequestParam("id") int couponGrantConfigId) {
		try {
			couponGrantConfigService.removeCouponGrantConfigById(couponGrantConfigId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove CouponGrantConfig failure : id={}", couponGrantConfigId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getCouponGrantConfigVoById(@RequestParam("id") int couponGrantConfigId) {
		try {
			CouponGrantConfigVo couponGrantConfigVo = couponGrantConfigService.getCouponGrantConfigVoById(couponGrantConfigId);
			return RestUtils.successWhenNotNull(couponGrantConfigVo);
		} catch (BusinessException ex) {
			LOG.error("get CouponGrantConfig failure : id={}", couponGrantConfigId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getCouponGrantConfigBoById(@RequestParam("id") int couponGrantConfigId) {
		try {
			CouponGrantConfigBo couponGrantConfigBo = couponGrantConfigService.getCouponGrantConfigBoById(couponGrantConfigId);
			return RestUtils.successWhenNotNull(couponGrantConfigBo);
		} catch (BusinessException ex) {
			LOG.error("get CouponGrantConfig failure : id={}", couponGrantConfigId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 禁用
	 **/
	@RequestMapping(value = "upAndDown", method = RequestMethod.GET)
	public RestResult updateState(@RequestParam("couponGrantConfigId")int couponGrantConfigId) {
		try {
			CouponGrantConfigVo couponGrantConfigVo=couponGrantConfigService.updateState(couponGrantConfigId);
			return RestUtils.success(couponGrantConfigId);
		} catch (Exception ex) {
			LOG.error("修改状态失败:", ex);
			return RestUtils.error("修改状态失败:" + ex.getMessage());
		}
	}

}