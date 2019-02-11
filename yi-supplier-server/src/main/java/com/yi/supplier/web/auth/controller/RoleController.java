/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.supplier.web.auth.controller;

import javax.annotation.Resource;

import com.yi.core.auth.domain.bo.RoleBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.yi.core.auth.domain.entity.Role;
import com.yi.core.auth.domain.vo.RoleListVo;
import com.yi.core.auth.domain.vo.RoleVo;
import com.yi.core.auth.service.IRoleService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 角色
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/role")
public class RoleController {

	private final Logger LOG = LoggerFactory.getLogger(RoleController.class);

	@Resource
	private IRoleService roleService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<RoleListVo> queryRole(@RequestBody Pagination<Role> query) {
		Page<RoleListVo> page = roleService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewRole(@RequestParam("id") int roleId) {
		try {
			RoleVo role = roleService.getRoleVoById(roleId);
			return RestUtils.successWhenNotNull(role);
		} catch (BusinessException ex) {
			LOG.error("get Role failure : id=roleId", ex);
			return RestUtils.error("get Role failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addRole(@RequestBody RoleBo roleBo) {
		try {
			RoleVo dbRole = roleService.addRole(roleBo);
			return RestUtils.successWhenNotNull(dbRole);
		} catch (BusinessException ex) {
			LOG.error("add Role failure : role", roleBo, ex);
			return RestUtils.error("add Role failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateRole(@RequestBody Role role) {
		try {
			Role dbRole = roleService.updateRole(role);
			return RestUtils.successWhenNotNull(dbRole);
		} catch (BusinessException ex) {
			LOG.error("update Role failure : role", role, ex);
			return RestUtils.error("update Role failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeRoleById(@RequestParam("id") int roleId) {
		try {
			roleService.removeRoleById(roleId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Role failure : id=roleId", ex);
			return RestUtils.error("remove Role failure : " + ex.getMessage());
		}
	}

	/**
	 * 添加权限
	 **/
	@RequestMapping(value = "jurisdiction", method = RequestMethod.POST)
	public RestResult jurisdiction(@RequestBody Role role) {
		try {
			Role role1 = roleService.jurisdiction(role);
			return RestUtils.success(role1);
		} catch (Exception ex) {
			LOG.error("remove Role failure : id=roleId", ex);
			return RestUtils.error("remove Role failure : " + ex.getMessage());
		}
	}
}