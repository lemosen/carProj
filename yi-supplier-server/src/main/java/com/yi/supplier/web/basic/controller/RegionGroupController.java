/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.supplier.web.basic.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.basic.domain.bo.RegionGroupBo;
import com.yi.core.basic.domain.entity.RegionGroup;
import com.yi.core.basic.domain.vo.RegionGroupListVo;
import com.yi.core.basic.domain.vo.RegionGroupVo;
import com.yi.core.basic.service.IRegionGroupService;
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
@RequestMapping(value = "/regionGroup")
public class RegionGroupController {

	private final Logger LOG = LoggerFactory.getLogger(RegionGroupController.class);

	@Resource
	private IRegionGroupService regionGroupService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<RegionGroupListVo> queryRegionGroup(@RequestBody Pagination<RegionGroup> query) {
		Page<RegionGroupListVo> page = regionGroupService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewRegionGroup(@RequestParam("id") int regionGroupId) {
		try {
			RegionGroupVo regionGroup = regionGroupService.getVoById(regionGroupId);
			return RestUtils.successWhenNotNull(regionGroup);
		} catch (BusinessException ex) {
			LOG.error("get RegionGroup failure : id=regionGroupId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addRegionGroup(@RequestBody RegionGroupBo regionGroup) {
		try {
			RegionGroupVo dbRegionGroup = regionGroupService.addRegionGroup(regionGroup);
			return RestUtils.successWhenNotNull(dbRegionGroup);
		} catch (BusinessException ex) {
			LOG.error("add RegionGroup failure : regionGroup", regionGroup, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateRegionGroup(@RequestBody RegionGroupBo regionGroup) {
		try {
			RegionGroupVo dbRegionGroup = regionGroupService.updateRegionGroup(regionGroup);
			return RestUtils.successWhenNotNull(dbRegionGroup);
		} catch (BusinessException ex) {
			LOG.error("update RegionGroup failure : regionGroup", regionGroup, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeRegionGroupById(@RequestParam("id") int regionGroupId) {
		try {
			regionGroupService.removeRegionGroupById(regionGroupId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove RegionGroup failure : id={}", regionGroupId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取地区组集合
	 *
	 * @return
	 */
	@RequestMapping(value = "getRegionGroups", method = RequestMethod.GET)
	public RestResult getRegionGroups() {
		try {
			List<RegionGroupListVo> regionGroups = regionGroupService.getRegionGroupListVos();
			return RestUtils.successWhenNotNull(regionGroups);
		} catch (BusinessException ex) {
			LOG.error("get RegionGroup failure", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 地区启用禁用
	 **/
	@RequestMapping(value = "updateShelf", method = RequestMethod.GET)
	public RestResult updateShelf(@RequestParam("regionGroupId") int regionGroupId) {
		try {
			regionGroupService.updateState(regionGroupId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("update RegionGroup state : id{}", regionGroupId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}
}