/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.member.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.member.domain.bo.MemberCommissionBo;
import com.yi.core.member.domain.entity.MemberCommission;
import com.yi.core.member.domain.vo.MemberCommissionListVo;
import com.yi.core.member.domain.vo.MemberCommissionVo;
import com.yi.core.member.service.IMemberCommissionService;
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
@RequestMapping(value = "/memberCommission")
public class MemberCommissionController {

	private final Logger LOG = LoggerFactory.getLogger(MemberCommissionController.class);

	@Resource
	private IMemberCommissionService memberCommissionService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<MemberCommissionListVo> queryMemberCommission(@RequestBody Pagination<MemberCommission> query) {
		Page<MemberCommissionListVo> page = memberCommissionService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addMemberCommission(@RequestBody MemberCommissionBo memberCommissionBo) {
		try {
			MemberCommissionListVo memberCommissionListVo = memberCommissionService.addMemberCommission(memberCommissionBo);
			return RestUtils.successWhenNotNull(memberCommissionListVo);
		} catch (BusinessException ex) {
			LOG.error("add MemberCommission failure : memberCommissionBo", memberCommissionBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateMemberCommission(@RequestBody MemberCommissionBo memberCommissionBo) {
		try {
			MemberCommissionListVo memberCommissionListVo = memberCommissionService.updateMemberCommission(memberCommissionBo);
			return RestUtils.successWhenNotNull(memberCommissionListVo);
		} catch (BusinessException ex) {
			LOG.error("update MemberCommission failure : memberCommissionBo", memberCommissionBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeMemberCommissionById(@RequestParam("id") int memberCommissionId) {
		try {
			memberCommissionService.removeMemberCommissionById(memberCommissionId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove MemberCommission failure : id={}", memberCommissionId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getMemberCommissionVoById(@RequestParam("id") int memberCommissionId) {
		try {
			MemberCommissionVo memberCommissionVo = memberCommissionService.getMemberCommissionVoById(memberCommissionId);
			return RestUtils.successWhenNotNull(memberCommissionVo);
		} catch (BusinessException ex) {
			LOG.error("get MemberCommission failure : id={}", memberCommissionId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getMemberCommissionBoById(@RequestParam("id") int memberCommissionId) {
		try {
			MemberCommissionBo memberCommissionBo = memberCommissionService.getMemberCommissionBoById(memberCommissionId);
			return RestUtils.successWhenNotNull(memberCommissionBo);
		} catch (BusinessException ex) {
			LOG.error("get MemberCommission failure : id={}", memberCommissionId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}