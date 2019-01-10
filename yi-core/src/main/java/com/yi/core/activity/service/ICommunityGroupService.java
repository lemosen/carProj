/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service;

import org.springframework.data.domain.Page;

import com.yi.core.activity.domain.bo.CommunityGroupBo;
import com.yi.core.activity.domain.entity.CommunityGroup;
import com.yi.core.activity.domain.vo.CommunityGroupListVo;
import com.yi.core.activity.domain.vo.CommunityGroupVo;
import com.yihz.common.persistence.Pagination;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface ICommunityGroupService {

	/**
	 * 分页查询: CommunityGroup
	 * 
	 * @param query
	 * @return
	 */
	Page<CommunityGroup> query(Pagination<CommunityGroup> query);

	/**
	 * 分页查询: CommunityGroup
	 **/
	Page<CommunityGroupListVo> queryListVo(Pagination<CommunityGroup> query);

	/**
	 * 创建CommunityGroup
	 **/
	CommunityGroupVo addCommunityGroup(CommunityGroupBo communityGroup);

	/**
	 * 更新CommunityGroup
	 **/
	CommunityGroupVo updateCommunityGroup(CommunityGroupBo communityGroup);

	/**
	 * 删除CommunityGroup
	 **/
	void removeCommunityGroupById(int communityGroupId);

	/**
	 * 根据ID得到CommunityGroupVo
	 **/
	CommunityGroupVo getCommunityGroupVoById(int communityGroupId);

	/**
	 * 根据ID得到CommunityGroupListVo
	 **/
	CommunityGroupVo getListVoById(int communityGroupId);

}
