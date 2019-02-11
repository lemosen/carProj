/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.supplier.web.basic.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.basic.domain.bo.CommunityBo;
import com.yi.core.basic.domain.entity.Community;
import com.yi.core.basic.domain.vo.CommunityListVo;
import com.yi.core.basic.domain.vo.CommunityVo;
import com.yi.core.basic.service.ICommunityService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 小区
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/community")
public class CommunityController {

	private final Logger LOG = LoggerFactory.getLogger(CommunityController.class);

	@Resource
	private ICommunityService communityService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<CommunityListVo> queryCommunity(@RequestBody Pagination<Community> query) {

		Page<CommunityListVo> page = communityService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewCommunity(@RequestParam("id") int communityId) {
		try {
			CommunityVo community = communityService.getCommunityVoById(communityId);
			return RestUtils.successWhenNotNull(community);
		} catch (BusinessException ex) {
			LOG.error("get Community failure : id=communityId", ex);
			return RestUtils.error("get Community failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addCommunity(@RequestBody CommunityBo community) {
		try {

			CommunityVo dbCommunity = communityService.addCommunity(community);
			return RestUtils.successWhenNotNull(dbCommunity);
		} catch (BusinessException ex) {
			LOG.error("add Community failure : community", community, ex);
			return RestUtils.error("add Community failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateCommunity(@RequestBody Community community) {
		try {
			CommunityVo dbCommunity = communityService.updateCommunity(community);
			return RestUtils.successWhenNotNull(dbCommunity);
		} catch (BusinessException ex) {
			LOG.error("update Community failure : community", community, ex);
			return RestUtils.error("update Community failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeCommunityById(@RequestParam("id") int communityId) {
		try {
			communityService.removeCommunityById(communityId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Community failure : id=communityId", ex);
			return RestUtils.error("remove Community failure : " + ex.getMessage());
		}
	}

	/**
	 * 查询提成总额
	 */
	@RequestMapping(value = "commissionSum", method = RequestMethod.GET)
	public RestResult commissionSum() {
		try {

			return RestUtils.success(communityService.commissionSum());
		} catch (Exception ex) {
			LOG.error(" Community failure : id=communityId", ex);
			return RestUtils.error(" Community failure : " + ex.getMessage());
		}
	}

	/**
	 * 小区禁用启用
	 */
	@RequestMapping(value = "updateShelf", method = RequestMethod.GET)
	public RestResult updateShelf(@RequestParam("communityId") int communityId) {
		try {
			CommunityVo community=communityService.banKai(communityId);
			return RestUtils.success(community);
		} catch (Exception ex) {
			LOG.error("remove Recommend failure : id=recommendId", ex);
			return RestUtils.error("remove Recommend failure : " + ex.getMessage());
		}
	}

}