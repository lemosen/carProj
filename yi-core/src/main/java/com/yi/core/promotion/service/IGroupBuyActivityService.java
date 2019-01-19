/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.promotion.service;

import org.springframework.data.domain.Page;

import com.yi.core.promotion.domain.bo.GroupBuyActivityBo;
import com.yi.core.promotion.domain.entity.GroupBuyActivity;
import com.yi.core.promotion.domain.listVo.GroupBuyActivityListVo;
import com.yi.core.promotion.domain.vo.GroupBuyActivityVo;
import com.yihz.common.persistence.Pagination;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IGroupBuyActivityService {

	/**
	 * 分页查询: GroupBuyActivity
	 * 
	 * @param query
	 * @return
	 */
	Page<GroupBuyActivity> query(Pagination<GroupBuyActivity> query);

	/**
	 * 分页查询: GroupBuyActivity
	 **/
	Page<GroupBuyActivityListVo> queryListVo(Pagination<GroupBuyActivity> query);

	/**
	 * 分页查询: GroupBuyActivity
	 **/
	Page<GroupBuyActivityListVo> queryListVoForApp(Pagination<GroupBuyActivity> query);

	/**
	 * 创建GroupBuyActivity
	 **/
	GroupBuyActivity addGroupBuyActivity(GroupBuyActivity groupBuyActivity);

	/**
	 * 创建GroupBuyActivity
	 **/
	GroupBuyActivityListVo addGroupBuyActivity(GroupBuyActivityBo groupBuyActivity);

	/**
	 * 更新GroupBuyActivity
	 **/
	GroupBuyActivity updateGroupBuyActivity(GroupBuyActivity groupBuyActivity);

	/**
	 * 更新GroupBuyActivity
	 **/
	GroupBuyActivityListVo updateGroupBuyActivity(GroupBuyActivityBo groupBuyActivity);

	/**
	 * 删除GroupBuyActivity
	 **/
	void removeGroupBuyActivityById(int groupBuyActivityId);

	/**
	 * 根据ID得到GroupBuyActivity
	 **/
	GroupBuyActivity getById(int groupBuyActivityId);

	/**
	 * 根据ID得到GroupBuyActivityBo
	 **/
	GroupBuyActivityBo getBoById(int groupBuyActivityId);

	/**
	 * 根据ID得到GroupBuyActivityVo
	 **/
	GroupBuyActivityVo getVoById(int groupBuyActivityId);

	/**
	 * 根据ID得到GroupBuyActivityListVo
	 **/
	GroupBuyActivityListVo getListVoById(int groupBuyActivityId);

}
