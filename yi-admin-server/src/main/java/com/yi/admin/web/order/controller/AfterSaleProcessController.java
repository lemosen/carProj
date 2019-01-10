/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.order.controller;

import com.yi.core.order.domain.bo.AfterSaleProcessBo;
import com.yi.core.order.domain.entity.AfterSaleProcess;
import com.yi.core.order.domain.vo.AfterSaleProcessListVo;
import com.yi.core.order.domain.vo.AfterSaleProcessVo;
import com.yi.core.order.service.IAfterSaleProcessService;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yihz.common.exception.BusinessException;
import javax.annotation.Resource;

/**
 * 售后处理
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/afterSaleProcess")
public class AfterSaleProcessController {

	private final Logger LOG = LoggerFactory.getLogger(AfterSaleProcessController.class);

	@Resource
	private IAfterSaleProcessService afterSaleProcessService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<AfterSaleProcessListVo> queryAfterSaleProcess(@RequestBody Pagination<AfterSaleProcess> query) {
		Page<AfterSaleProcessListVo> page = afterSaleProcessService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addAfterSaleProcess(@RequestBody AfterSaleProcessBo afterSaleProcessBo) {
		try {
			AfterSaleProcessListVo afterSaleProcessListVo = afterSaleProcessService.addAfterSaleProcess(afterSaleProcessBo);
			return RestUtils.successWhenNotNull(afterSaleProcessListVo);
		} catch (BusinessException ex) {
			LOG.error("add AfterSaleProcess failure : afterSaleProcessBo", afterSaleProcessBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateAfterSaleProcess(@RequestBody AfterSaleProcessBo afterSaleProcessBo) {
		try {
			AfterSaleProcessListVo afterSaleProcessListVo = afterSaleProcessService.updateAfterSaleProcess(afterSaleProcessBo);
			return RestUtils.successWhenNotNull(afterSaleProcessListVo);
		} catch (BusinessException ex) {
			LOG.error("update AfterSaleProcess failure : afterSaleProcessBo", afterSaleProcessBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeAfterSaleProcessById(@RequestParam("id") int afterSaleProcessId) {
		try {
			afterSaleProcessService.removeAfterSaleProcessById(afterSaleProcessId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove AfterSaleProcess failure : id={}", afterSaleProcessId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getAfterSaleProcessVoById(@RequestParam("id") int afterSaleProcessId) {
		try {
			AfterSaleProcessVo afterSaleProcessVo = afterSaleProcessService.getAfterSaleProcessVoById(afterSaleProcessId);
			return RestUtils.successWhenNotNull(afterSaleProcessVo);
		} catch (BusinessException ex) {
			LOG.error("get AfterSaleProcess failure : id={}", afterSaleProcessId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getAfterSaleProcessBoById(@RequestParam("id") int afterSaleProcessId) {
		try {
			AfterSaleProcessBo afterSaleProcessBo = afterSaleProcessService.getAfterSaleProcessBoById(afterSaleProcessId);
			return RestUtils.successWhenNotNull(afterSaleProcessBo);
		} catch (BusinessException ex) {
			LOG.error("get AfterSaleProcess failure : id={}", afterSaleProcessId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 确认退货
	 **/
	@RequestMapping(value = "confirmReturn", method = RequestMethod.POST)
	public RestResult confirmReturn(@RequestBody AfterSaleProcessBo afterSaleProcessBo) {
		try {
			afterSaleProcessService.addByProcessState(afterSaleProcessBo);
			return RestUtils.success(Boolean.TRUE);
		} catch (BusinessException ex) {
			LOG.error("add AfterSaleProcess failure : afterSaleProcessBo", afterSaleProcessBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}