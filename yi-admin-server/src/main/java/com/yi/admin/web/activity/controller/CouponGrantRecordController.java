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

import com.yi.core.activity.domain.bo.CouponGrantRecordBo;
import com.yi.core.activity.domain.entity.CouponGrantRecord;
import com.yi.core.activity.domain.vo.CouponGrantRecordListVo;
import com.yi.core.activity.domain.vo.CouponGrantRecordVo;
import com.yi.core.activity.service.ICouponGrantRecordService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 优惠券发放记录
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/couponGrantRecord")
public class CouponGrantRecordController {

	private final Logger LOG = LoggerFactory.getLogger(CouponGrantRecordController.class);

	@Resource
	private ICouponGrantRecordService couponGrantRecordService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<CouponGrantRecordListVo> queryCouponGrantRecord(@RequestBody Pagination<CouponGrantRecord> query) {
		Page<CouponGrantRecordListVo> page = couponGrantRecordService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addCouponGrantRecord(@RequestBody CouponGrantRecordBo couponGrantRecordBo) {
		try {
			CouponGrantRecordListVo couponGrantRecordListVo = couponGrantRecordService.addCouponGrantRecord(couponGrantRecordBo);
			return RestUtils.successWhenNotNull(couponGrantRecordListVo);
		} catch (BusinessException ex) {
			LOG.error("add CouponGrantRecord failure : couponGrantRecordBo", couponGrantRecordBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateCouponGrantRecord(@RequestBody CouponGrantRecordBo couponGrantRecordBo) {
		try {
			CouponGrantRecordListVo couponGrantRecordListVo = couponGrantRecordService.updateCouponGrantRecord(couponGrantRecordBo);
			return RestUtils.successWhenNotNull(couponGrantRecordListVo);
		} catch (BusinessException ex) {
			LOG.error("update CouponGrantRecord failure : couponGrantRecordBo", couponGrantRecordBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeCouponGrantRecordById(@RequestParam("id") int couponGrantRecordId) {
		try {
			couponGrantRecordService.removeCouponGrantRecordById(couponGrantRecordId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove CouponGrantRecord failure : id={}", couponGrantRecordId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getCouponGrantRecordVoById(@RequestParam("id") int couponGrantRecordId) {
		try {
			CouponGrantRecordVo couponGrantRecordVo = couponGrantRecordService.getCouponGrantRecordVoById(couponGrantRecordId);
			return RestUtils.successWhenNotNull(couponGrantRecordVo);
		} catch (BusinessException ex) {
			LOG.error("get CouponGrantRecord failure : id={}", couponGrantRecordId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getCouponGrantRecordBoById(@RequestParam("id") int couponGrantRecordId) {
		try {
			CouponGrantRecordBo couponGrantRecordBo = couponGrantRecordService.getCouponGrantRecordBoById(couponGrantRecordId);
			return RestUtils.successWhenNotNull(couponGrantRecordBo);
		} catch (BusinessException ex) {
			LOG.error("get CouponGrantRecord failure : id={}", couponGrantRecordId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}