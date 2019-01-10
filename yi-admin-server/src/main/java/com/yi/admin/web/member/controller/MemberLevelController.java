/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.member.controller;

import javax.annotation.Resource;

import com.yi.core.member.domain.vo.MemberLevelListVo;
import com.yi.core.member.domain.vo.MemberLevelVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.member.domain.entity.MemberLevel;
import com.yi.core.member.service.IMemberLevelService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 会员等级
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/memberLevel")
public class MemberLevelController {

	private final Logger LOG = LoggerFactory.getLogger(MemberLevelController.class);

	@Resource
	private IMemberLevelService memberLevelService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<MemberLevelListVo> queryMemberLevel(@RequestBody Pagination<MemberLevel> query) {
		Page<MemberLevelListVo> page = memberLevelService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewMemberLevel(@RequestParam("id") int memberLevelId) {
		try {
			MemberLevelVo memberLevel = memberLevelService.getMemberLevelVoById(memberLevelId);
			return RestUtils.successWhenNotNull(memberLevel);
		} catch (BusinessException ex) {
			LOG.error("get MemberLevel failure : id=memberLevelId", ex);
			return RestUtils.error("get MemberLevel failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addMemberLevel(@RequestBody MemberLevel memberLevel) {
		try {
			MemberLevelVo dbMemberLevel = memberLevelService.addMemberLevel(memberLevel);
			return RestUtils.successWhenNotNull(dbMemberLevel);
		} catch (BusinessException ex) {
			LOG.error("add MemberLevel failure : memberLevel", memberLevel, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateMemberLevel(@RequestBody MemberLevel memberLevel) {
		try {
			MemberLevelVo dbMemberLevel = memberLevelService.updateMemberLevel(memberLevel);
			return RestUtils.successWhenNotNull(dbMemberLevel);
		} catch (BusinessException ex) {
			LOG.error("update MemberLevel failure : memberLevel", memberLevel, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeMemberLevelById(@RequestParam("id") int memberLevelId) {
		try {
			memberLevelService.removeMemberLevelById(memberLevelId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove MemberLevel failure : id=memberLevelId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

//	/**
//	 * 查询会员名称
//	 **/
//	@RequestMapping(value = "onlyName", method = RequestMethod.GET)
//	public RestResult onlyName(@RequestParam("name") String name) {
//		try {
//			return RestUtils.success(memberLevelService.onlyName(name));
//		} catch (Exception ex) {
//			LOG.error("remove MemberLevel failure : id=memberLevelId", ex);
//			return RestUtils.error("remove MemberLevel failure : " + ex.getMessage());
//		}
//	}
}