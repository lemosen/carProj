/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.admin.web.activity.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.activity.domain.bo.RewardBo;
import com.yi.core.activity.domain.entity.Reward;
import com.yi.core.activity.domain.vo.RewardListVo;
import com.yi.core.activity.domain.vo.RewardVo;
import com.yi.core.activity.service.IRewardService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 奖励
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/reward")
public class RewardController {

	private final Logger LOG = LoggerFactory.getLogger(RewardController.class);

	@Resource
	private IRewardService rewardService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<RewardListVo> queryReward(@RequestBody Pagination<Reward> query) {
		Page<RewardListVo> page = rewardService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addReward(@RequestBody RewardBo rewardBo) {
		try {
			RewardListVo rewardListVo = rewardService.addReward(rewardBo);
			return RestUtils.successWhenNotNull(rewardListVo);
		} catch (BusinessException ex) {
			LOG.error("add Reward failure : {}", rewardBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateReward(@RequestBody RewardBo rewardBo) {
		try {
			RewardListVo rewardListVo = rewardService.updateReward(rewardBo);
			return RestUtils.successWhenNotNull(rewardListVo);
		} catch (BusinessException ex) {
			LOG.error("update Reward failure : {}", rewardBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeRewardById(@RequestParam("id") int rewardId) {
		try {
			rewardService.removeRewardById(rewardId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Reward failure : id={}", rewardId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getRewardVoById(@RequestParam("id") int rewardId) {
		try {
			RewardVo rewardVo = rewardService.getRewardVoById(rewardId);
			return RestUtils.successWhenNotNull(rewardVo);
		} catch (BusinessException ex) {
			LOG.error("get Reward failure : id={}", rewardId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getRewardBoById(@RequestParam("id") int rewardId) {
		try {
			RewardBo rewardBo = rewardService.getRewardBoById(rewardId);
			return RestUtils.successWhenNotNull(rewardBo);
		} catch (BusinessException ex) {
			LOG.error("get Reward failure : id={}", rewardId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}