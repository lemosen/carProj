/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.promotion.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.promotion.domain.bo.GroupBuyActivityBo;
import com.yi.core.promotion.domain.entity.GroupBuyActivity;
import com.yi.core.promotion.domain.listVo.GroupBuyActivityListVo;
import com.yi.core.promotion.domain.vo.GroupBuyActivityVo;
import com.yi.core.promotion.service.IGroupBuyActivityService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 团购活动
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/groupBuyActivity")
public class GroupBuyActivityController {

	private final Logger LOG = LoggerFactory.getLogger(GroupBuyActivityController.class);

	@Resource
	private IGroupBuyActivityService groupBuyActivityService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<GroupBuyActivityListVo> queryGroupBuyActivity(@RequestBody Pagination<GroupBuyActivity> query) {
		Page<GroupBuyActivityListVo> page = groupBuyActivityService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addGroupBuyActivity(@RequestBody GroupBuyActivityBo groupBuyActivityBo) {
		try {
			GroupBuyActivityListVo groupBuyActivityListVo = groupBuyActivityService.addGroupBuyActivity(groupBuyActivityBo);
			return RestUtils.successWhenNotNull(groupBuyActivityListVo);
		} catch (BusinessException ex) {
			LOG.error("add GroupBuyActivity failure : {}", groupBuyActivityBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateGroupBuyActivity(@RequestBody GroupBuyActivityBo groupBuyActivityBo) {
		try {
			GroupBuyActivityListVo groupBuyActivityListVo = groupBuyActivityService.updateGroupBuyActivity(groupBuyActivityBo);
			return RestUtils.successWhenNotNull(groupBuyActivityListVo);
		} catch (BusinessException ex) {
			LOG.error("update GroupBuyActivity failure : {}", groupBuyActivityBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeGroupBuyActivityById(@RequestParam("id") int groupBuyActivityId) {
		try {
			groupBuyActivityService.removeGroupBuyActivityById(groupBuyActivityId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove GroupBuyActivity failure : id={}", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getGroupBuyActivityBoById(@RequestParam("id") int groupBuyActivityId) {
		try {
			GroupBuyActivityBo groupBuyActivityBo = groupBuyActivityService.getBoById(groupBuyActivityId);
			return RestUtils.successWhenNotNull(groupBuyActivityBo);
		} catch (BusinessException ex) {
			LOG.error("get GroupBuyActivity failure : id={}", groupBuyActivityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getGroupBuyActivityVoById(@RequestParam("id") int groupBuyActivityId) {
		try {
			GroupBuyActivityVo groupBuyActivityVo = groupBuyActivityService.getVoById(groupBuyActivityId);
			return RestUtils.successWhenNotNull(groupBuyActivityVo);
		} catch (BusinessException ex) {
			LOG.error("get GroupBuyActivity failure : id={}", groupBuyActivityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}
}