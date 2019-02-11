/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.order.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.order.domain.bo.AfterSaleReasonBo;
import com.yi.core.order.domain.entity.AfterSaleReason;
import com.yi.core.order.domain.vo.AfterSaleReasonListVo;
import com.yi.core.order.domain.vo.AfterSaleReasonVo;
import com.yi.core.order.service.IAfterSaleReasonService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 售后原因
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/afterSaleReason")
public class AfterSaleReasonController {

	private final Logger LOG = LoggerFactory.getLogger(AfterSaleReasonController.class);

	@Resource
	private IAfterSaleReasonService afterSaleReasonService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<AfterSaleReasonListVo> queryAfterSaleReason(@RequestBody Pagination<AfterSaleReason> query) {
		Page<AfterSaleReasonListVo> page = afterSaleReasonService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addAfterSaleReason(@RequestBody AfterSaleReasonBo afterSaleReasonBo) {
		try {
			AfterSaleReasonListVo afterSaleReasonVo = afterSaleReasonService.addAfterSaleReason(afterSaleReasonBo);
			return RestUtils.successWhenNotNull(afterSaleReasonVo);
		} catch (BusinessException ex) {
			LOG.error("add AfterSaleReason failure : afterSaleReasonBo", afterSaleReasonBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateAfterSaleReason(@RequestBody AfterSaleReasonBo afterSaleReasonBo) {
		try {
			AfterSaleReasonListVo afterSaleReasonVo = afterSaleReasonService.updateAfterSaleReason(afterSaleReasonBo);
			return RestUtils.successWhenNotNull(afterSaleReasonVo);
		} catch (BusinessException ex) {
			LOG.error("update AfterSaleReason failure : afterSaleReasonBo", afterSaleReasonBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeAfterSaleReasonById(@RequestParam("id") int afterSaleReasonId) {
		try {
			afterSaleReasonService.removeAfterSaleReasonById(afterSaleReasonId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove AfterSaleReason failure : id=afterSaleReasonId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getAfterSaleReasonBoById(@RequestParam("id") int afterSaleReasonId) {
		try {
			AfterSaleReasonBo afterSaleReasonBo = afterSaleReasonService.getBoById(afterSaleReasonId);
			return RestUtils.successWhenNotNull(afterSaleReasonBo);
		} catch (BusinessException ex) {
			LOG.error("get AfterSaleReason failure : id=afterSaleReasonId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getAfterSaleReasonVoById(@RequestParam("id") int afterSaleReasonId) {
		try {
			AfterSaleReasonVo afterSaleReasonVo = afterSaleReasonService.getVoById(afterSaleReasonId);
			return RestUtils.successWhenNotNull(afterSaleReasonVo);
		} catch (BusinessException ex) {
			LOG.error("get AfterSaleReason failure : id=afterSaleReasonId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}
}