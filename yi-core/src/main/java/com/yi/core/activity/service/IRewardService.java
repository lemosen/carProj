/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.service;

import org.springframework.data.domain.Page;

import com.yi.core.activity.domain.bo.RewardBo;
import com.yi.core.activity.domain.entity.Reward;
import com.yi.core.activity.domain.vo.RewardListVo;
import com.yi.core.activity.domain.vo.RewardVo;
import com.yihz.common.persistence.Pagination;

/**
 * 奖励
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IRewardService {

	/**
	 * 分页查询: Reward
	 **/
	Page<Reward> query(Pagination<Reward> query);

	/**
	 * 分页查询: Reward
	 **/
	Page<RewardListVo> queryListVo(Pagination<Reward> query);

	/**
	 * 分页查询: Reward
	 **/
	Page<RewardListVo> queryListVoForApp(Pagination<Reward> query);

	/**
	 * 创建Reward
	 **/
	Reward addReward(Reward reward);

	/**
	 * 创建Reward
	 **/
	RewardListVo addReward(RewardBo reward);

	/**
	 * 更新Reward
	 **/
	Reward updateReward(Reward reward);

	/**
	 * 更新Reward
	 **/
	RewardListVo updateReward(RewardBo reward);

	/**
	 * 删除Reward
	 **/
	void removeRewardById(int rewardId);

	/**
	 * 根据ID得到Reward
	 **/
	Reward getRewardById(int rewardId);

	/**
	 * 根据ID得到RewardBo
	 **/
	RewardBo getRewardBoById(int rewardId);

	/**
	 * 根据ID得到RewardVo
	 **/
	RewardVo getRewardVoById(int rewardId);

	/**
	 * 根据ID得到RewardListVo
	 **/
	RewardListVo getListVoById(int rewardId);

}
