/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.member.controller;

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

import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.domain.vo.MemberLevelListVo;
import com.yi.core.member.domain.vo.MemberListVo;
import com.yi.core.member.domain.vo.MemberVo;
import com.yi.core.member.service.IMemberLevelService;
import com.yi.core.member.service.IMemberService;
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
@RequestMapping(value = "/member")
public class MemberController {

	private final Logger LOG = LoggerFactory.getLogger(MemberController.class);

	@Resource
	private IMemberService memberService;

	@Resource
	private IMemberLevelService memberLevelService;


	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<MemberListVo> queryMember(@RequestBody Pagination<Member> query) {
		Page<MemberListVo> page = memberService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewMember(@RequestParam("id") int memberId) {
		try {
			MemberVo member = memberService.getMemberVoById(memberId);
			return RestUtils.successWhenNotNull(member);
		} catch (BusinessException ex) {
			LOG.error("get Member failure : id=memberId", ex);
			return RestUtils.error("get Member failure : " + ex.getMessage());
		}
	}

	/**
	 * 会员等级
	 **/
	@RequestMapping(value = "memberLevel", method = RequestMethod.GET)
	public List<MemberLevelListVo> memberLevel() {
			return memberLevelService.queryAll();
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addMember(@RequestBody Member member) {
		try {
			MemberVo dbMember = memberService.addMember(member);
			return RestUtils.successWhenNotNull(dbMember);
		} catch (BusinessException ex) {
			LOG.error("add Member failure : member", member, ex);
			return RestUtils.error("add Member failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateMember(@RequestBody Member member) {
		try {
			MemberVo dbMember = memberService.updateMember(member);
			return RestUtils.successWhenNotNull(dbMember);
		} catch (BusinessException ex) {
			LOG.error("update Member failure : member", member, ex);
			return RestUtils.error("update Member failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeMemberById(@RequestParam("id") int memberId) {
		try {
			memberService.removeMemberById(memberId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Member failure : id=memberId", ex);
			return RestUtils.error("remove Member failure : " + ex.getMessage());
		}
	}
	/**
	 * 禁用启用会员
	 **/
	@RequestMapping(value = "prohibition", method = RequestMethod.GET)
	public RestResult updateState(@RequestParam("memberId")int memberId) {
		try {
		     MemberVo member=memberService.updateState(memberId);
			return RestUtils.success(member);
		} catch (Exception ex) {
			LOG.error("remove Member failure : id=memberId", ex);
			return RestUtils.error("remove Member failure : " + ex.getMessage());
		}
	}

	/**
	 * 取消vip
	 **/
	@RequestMapping(value = "updataVipNo", method = RequestMethod.GET)
	public RestResult updataVipNo(@RequestParam("memberId") int memberId) {
		try {
			return RestUtils.success(memberService.updataVipNo(memberId));
		} catch (Exception ex) {
			LOG.error("remove Member failure : id=memberId", ex);
			return RestUtils.error("remove Member failure : " + ex.getMessage());
		}
	}/**
	 * 成为vip
	 **/
	@RequestMapping(value = "updataVipYes", method = RequestMethod.GET)
	public RestResult updataVipYes(@RequestParam("memberId") int memberId) {
		try {

			return RestUtils.success(memberService.updataVipYes(memberId));
		} catch (Exception ex) {
			LOG.error("remove Member failure : id=memberId", ex);
			return RestUtils.error("remove Member failure : " + ex.getMessage());
		}
	}




}