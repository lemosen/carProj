/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.activity.controller;

import com.yi.core.activity.domain.bo.CommunityGroupBo;
import com.yi.core.activity.domain.entity.CommunityGroup;
import com.yi.core.activity.domain.vo.CommunityGroupListVo;
import com.yi.core.activity.domain.vo.CommunityGroupVo;

import com.yi.core.activity.service.ICommunityGroupService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Deprecated
@RestController
@RequestMapping(value = "/communityGroup")
public class CommunityGroupController {

	private final Logger LOG = LoggerFactory.getLogger(CommunityGroupController.class);

	@Resource
	private ICommunityGroupService communityGroupService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<CommunityGroupListVo> queryCommunityGroup(@RequestBody Pagination<CommunityGroup> query) {
		Page<CommunityGroupListVo> page = communityGroupService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewCommunityGroup(@RequestParam("id") int communityGroupId) {
		try {
			CommunityGroupVo communityGroup = communityGroupService.getCommunityGroupVoById(communityGroupId);
			return RestUtils.successWhenNotNull(communityGroup);
		} catch (BusinessException ex) {
			LOG.error("get CommunityGroup failure : id=communityGroupId", ex);
			return RestUtils.error("get CommunityGroup failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addCommunityGroup(@RequestBody CommunityGroupBo communityGroup) {
		try {
			CommunityGroupVo dbCommunityGroup = communityGroupService.addCommunityGroup(communityGroup);
			return RestUtils.successWhenNotNull(dbCommunityGroup);
		} catch (BusinessException ex) {
			LOG.error("add CommunityGroup failure : communityGroup", communityGroup, ex);
			return RestUtils.error("add CommunityGroup failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateCommunityGroup(@RequestBody CommunityGroupBo communityGroup) {
		try {
			CommunityGroupVo dbCommunityGroup = communityGroupService.updateCommunityGroup(communityGroup);
			return RestUtils.successWhenNotNull(dbCommunityGroup);
		} catch (BusinessException ex) {
			LOG.error("update CommunityGroup failure : communityGroup", communityGroup, ex);
			return RestUtils.error("update CommunityGroup failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeCommunityGroupById(@RequestParam("id") int communityGroupId) {
		try {
			communityGroupService.removeCommunityGroupById(communityGroupId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove CommunityGroup failure : id=communityGroupId", ex);
			return RestUtils.error("remove CommunityGroup failure : " + ex.getMessage());
		}
	}
}