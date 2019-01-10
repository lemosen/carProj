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

import com.yi.core.promotion.domain.bo.GroupBuyActivityTimeBo;
import com.yi.core.promotion.domain.entity.GroupBuyActivityTime;
import com.yi.core.promotion.domain.listVo.GroupBuyActivityTimeListVo;
import com.yi.core.promotion.domain.vo.GroupBuyActivityTimeVo;
import com.yi.core.promotion.service.IGroupBuyActivityTimeService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/groupBuyActivityTime")
public class GroupBuyActivityTimeController {

	private final Logger LOG = LoggerFactory.getLogger(GroupBuyActivityTimeController.class);

	@Resource
	private IGroupBuyActivityTimeService groupBuyActivityTimeService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<GroupBuyActivityTimeListVo> queryGroupBuyActivityTime(@RequestBody Pagination<GroupBuyActivityTime> query) {
		Page<GroupBuyActivityTimeListVo> page = groupBuyActivityTimeService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addGroupBuyActivityTime(@RequestBody GroupBuyActivityTimeBo groupBuyActivityTimeBo) {
		try {
			GroupBuyActivityTimeListVo groupBuyActivityTimeListVo = groupBuyActivityTimeService.addGroupBuyActivityTime(groupBuyActivityTimeBo);
			return RestUtils.successWhenNotNull(groupBuyActivityTimeListVo);
		} catch (BusinessException ex) {
			LOG.error("add GroupBuyActivityTime failure : groupBuyActivityTimeBo", groupBuyActivityTimeBo, ex);
			return RestUtils.error("add GroupBuyActivityTime failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateGroupBuyActivityTime(@RequestBody GroupBuyActivityTimeBo groupBuyActivityTimeBo) {
		try {
			GroupBuyActivityTimeListVo groupBuyActivityTimeListVo = groupBuyActivityTimeService.updateGroupBuyActivityTime(groupBuyActivityTimeBo);
			return RestUtils.successWhenNotNull(groupBuyActivityTimeListVo);
		} catch (BusinessException ex) {
			LOG.error("update GroupBuyActivityTime failure : groupBuyActivityTimeBo", groupBuyActivityTimeBo, ex);
			return RestUtils.error("update GroupBuyActivityTime failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeGroupBuyActivityTimeById(@RequestParam("id") int groupBuyActivityTimeId) {
		try {
			groupBuyActivityTimeService.removeGroupBuyActivityTimeById(groupBuyActivityTimeId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove GroupBuyActivityTime failure : id=groupBuyActivityTimeId", ex);
			return RestUtils.error("remove GroupBuyActivityTime failure : " + ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getGroupBuyActivityTimeBoById(@RequestParam("id") int groupBuyActivityTimeId) {
		try {
			GroupBuyActivityTimeBo groupBuyActivityTimeBo = groupBuyActivityTimeService.getBoById(groupBuyActivityTimeId);
			return RestUtils.successWhenNotNull(groupBuyActivityTimeBo);
		} catch (BusinessException ex) {
			LOG.error("get GroupBuyActivityTime failure : id=groupBuyActivityTimeId", ex);
			return RestUtils.error("get GroupBuyActivityTime failure : " + ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getVoById", method = RequestMethod.GET)
	public RestResult getGroupBuyActivityTimeVoById(@RequestParam("id") int groupBuyActivityTimeId) {
		try {
			GroupBuyActivityTimeVo groupBuyActivityTimeVo = groupBuyActivityTimeService.getVoById(groupBuyActivityTimeId);
			return RestUtils.successWhenNotNull(groupBuyActivityTimeVo);
		} catch (BusinessException ex) {
			LOG.error("get GroupBuyActivityTime failure : id=groupBuyActivityTimeId", ex);
			return RestUtils.error("get GroupBuyActivityTime failure : " + ex.getMessage());
		}
	}
}