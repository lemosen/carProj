/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.supplier.web.commodity.controller;

import javax.annotation.Resource;

import com.yi.core.commodity.domain.vo.AttributeGroupListVo;
import com.yi.core.commodity.domain.vo.AttributeGroupVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.commodity.domain.entity.AttributeGroup;
import com.yi.core.commodity.service.IAttributeGroupService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 属性组
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/attributeGroup")
public class AttributeGroupController {

	private final Logger LOG = LoggerFactory.getLogger(AttributeGroupController.class);

	@Resource
	private IAttributeGroupService attributeGroupService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<AttributeGroupListVo> queryAttributeGroup(@RequestBody Pagination<AttributeGroup> query) {
		Page<AttributeGroupListVo> page = attributeGroupService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewAttributeGroup(@RequestParam("id") int attributeGroupId) {
		try {
			AttributeGroupVo attributeGroup = attributeGroupService.getVoById(attributeGroupId);
			return RestUtils.successWhenNotNull(attributeGroup);
		} catch (BusinessException ex) {
			LOG.error("get AttributeGroup failure : id=attributeGroupId", ex);
			return RestUtils.error("get AttributeGroup failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addAttributeGroup(@RequestBody AttributeGroup attributeGroup) {
		try {
			AttributeGroup dbAttributeGroup = attributeGroupService.addAttributeGroup(attributeGroup);
			return RestUtils.successWhenNotNull(dbAttributeGroup);
		} catch (BusinessException ex) {
			LOG.error("add AttributeGroup failure : attributeGroup", attributeGroup, ex);
			return RestUtils.error("add AttributeGroup failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateAttributeGroup(@RequestBody AttributeGroup attributeGroup) {
		try {
			AttributeGroup dbAttributeGroup = attributeGroupService.updateAttributeGroup(attributeGroup);
			return RestUtils.successWhenNotNull(dbAttributeGroup);
		} catch (BusinessException ex) {
			LOG.error("update AttributeGroup failure : attributeGroup", attributeGroup, ex);
			return RestUtils.error("update AttributeGroup failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeAttributeGroupById(@RequestParam("id") int attributeGroupId) {
		try {
			attributeGroupService.removeAttributeGroupById(attributeGroupId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove AttributeGroup failure : id=attributeGroupId", ex);
			return RestUtils.error("remove AttributeGroup failure : " + ex.getMessage());
		}
	}
}