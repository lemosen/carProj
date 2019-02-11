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

import com.yi.core.activity.domain.entity.CouponReceive;
import com.yi.core.activity.domain.vo.CouponReceiveListVo;
import com.yi.core.activity.domain.vo.CouponReceiveVo;
import com.yi.core.activity.service.ICouponReceiveService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/couponReceive")
public class CouponReceiveController {

	private final Logger LOG = LoggerFactory.getLogger(CouponReceiveController.class);

	@Resource
	private ICouponReceiveService couponReceiveService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<CouponReceiveListVo> queryCouponReceive(@RequestBody Pagination<CouponReceive> query) {
		Page<CouponReceiveListVo> page = couponReceiveService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewCouponReceive(@RequestParam("id") int couponReceiveId) {
		try {
			CouponReceiveVo couponReceive = couponReceiveService.getVoById(couponReceiveId);
			return RestUtils.successWhenNotNull(couponReceive);
		} catch (BusinessException ex) {
			LOG.error("get CouponReceive failure : id=couponReceiveId", ex);
			return RestUtils.error("get CouponReceive failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addCouponReceive(@RequestBody CouponReceive couponReceive) {
		try {
			CouponReceive dbCouponReceive = couponReceiveService.addCouponReceive(couponReceive);
			return RestUtils.successWhenNotNull(dbCouponReceive);
		} catch (BusinessException ex) {
			LOG.error("add CouponReceive failure : couponReceive", couponReceive, ex);
			return RestUtils.error("add CouponReceive failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateCouponReceive(@RequestBody CouponReceive couponReceive) {
		try {
			CouponReceive dbCouponReceive = couponReceiveService.updateCouponReceive(couponReceive);
			return RestUtils.successWhenNotNull(dbCouponReceive);
		} catch (BusinessException ex) {
			LOG.error("update CouponReceive failure : couponReceive", couponReceive, ex);
			return RestUtils.error("update CouponReceive failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeCouponReceiveById(@RequestParam("id") int couponReceiveId) {
		try {
			couponReceiveService.removeCouponReceiveById(couponReceiveId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove CouponReceive failure : id=couponReceiveId", ex);
			return RestUtils.error("remove CouponReceive failure : " + ex.getMessage());
		}
	}
}