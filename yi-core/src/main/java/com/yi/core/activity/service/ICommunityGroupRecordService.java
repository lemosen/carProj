/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service;

import org.springframework.data.domain.Page;

import com.yi.core.activity.domain.bo.CommunityGroupRecordBo;
import com.yi.core.activity.domain.entity.CommunityGroupRecord;
import com.yi.core.activity.domain.vo.CommunityGroupRecordListVo;
import com.yi.core.activity.domain.vo.CommunityGroupRecordVo;
import com.yihz.common.persistence.Pagination;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface ICommunityGroupRecordService {

	Page<CommunityGroupRecord> query(Pagination<CommunityGroupRecord> query);

	/**
	 * 创建CommunityGroupRecord
	 **/
	CommunityGroupRecordVo addCommunityGroupRecord(CommunityGroupRecordBo communityGroupRecord);

	/**
	 * 更新CommunityGroupRecord
	 **/
	CommunityGroupRecordVo updateCommunityGroupRecord(CommunityGroupRecordBo communityGroupRecord);

	/**
	 * 删除CommunityGroupRecord
	 **/
	void removeCommunityGroupRecordById(int communityGroupRecordId);

	/**
	 * 根据ID得到CommunityGroupRecordVo
	 **/
	CommunityGroupRecordVo getCommunityGroupRecordVoById(int communityGroupRecordId);

	/**
	 * 根据ID得到CommunityGroupRecordListVo
	 **/
	CommunityGroupRecordVo getListVoById(int communityGroupRecordId);

	/**
	 * 分页查询: CommunityGroupRecord
	 **/
	Page<CommunityGroupRecordListVo> queryListVo(Pagination<CommunityGroupRecord> query);

}
