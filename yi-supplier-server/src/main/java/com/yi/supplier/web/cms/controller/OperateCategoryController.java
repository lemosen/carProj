/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.supplier.web.cms.controller;

import com.yi.core.cms.domain.bo.OperateCategoryBo;
import com.yi.core.cms.domain.entity.OperateCategory;
import com.yi.core.cms.domain.vo.OperateCategoryVo;
import com.yi.core.cms.service.IOperateCategoryService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 运营分类
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/operateCategory")
public class OperateCategoryController {

	private final Logger LOG = LoggerFactory.getLogger(OperateCategoryController.class);

	@Resource
	private IOperateCategoryService operateCategoryService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<OperateCategory> queryOperateCategory(@RequestBody Pagination<OperateCategory> query) {
		Page<OperateCategory> page = operateCategoryService.query(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewOperateCategory(@RequestParam("id") int operateCategoryId) {
		try {
			OperateCategoryVo operateCategoryVo = operateCategoryService.getOperateCategoryVoById(operateCategoryId);
			return RestUtils.successWhenNotNull(operateCategoryVo);
		} catch (BusinessException ex) {
			LOG.error("get OperateCategory failure : id=operateCategoryId", ex);
			return RestUtils.error("get OperateCategory failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addOperateCategory(@RequestBody OperateCategoryBo operateCategory) {
		try {
			OperateCategoryVo dbOperateCategory = operateCategoryService.addOperateCategory(operateCategory);
			return RestUtils.successWhenNotNull(dbOperateCategory);
		} catch (BusinessException ex) {
			LOG.error("add OperateCategory failure : operateCategory", operateCategory, ex);
			return RestUtils.error("add OperateCategory failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateOperateCategory(@RequestBody OperateCategoryBo operateCategory) {
		try {
			OperateCategoryVo dbOperateCategory = operateCategoryService.updateOperateCategory(operateCategory);
			return RestUtils.successWhenNotNull(dbOperateCategory);
		} catch (BusinessException ex) {
			LOG.error("update OperateCategory failure : operateCategory", operateCategory, ex);
			return RestUtils.error("update OperateCategory failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeOperateCategoryById(@RequestParam("id") int operateCategoryId) {
		try {
			operateCategoryService.removeOperateCategoryById(operateCategoryId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove OperateCategory failure : id=operateCategoryId", ex);
			return RestUtils.error("remove OperateCategory failure : " + ex.getMessage());
		}
	}

	/**
	 * 获取所有分类
	 **/
	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public RestResult getAll() {
		try {
			return RestUtils.successWhenNotNull(operateCategoryService.getAll());
		} catch (BusinessException ex) {
			LOG.error("getAll Category failure : category", ex);
			return RestUtils.error("add Category failure : " + ex.getMessage());
		}
	}
}