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

import com.yi.core.activity.domain.bo.PrizeBo;
import com.yi.core.activity.domain.entity.Prize;
import com.yi.core.activity.domain.vo.PrizeListVo;
import com.yi.core.activity.domain.vo.PrizeVo;
import com.yi.core.activity.service.IPrizeService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 奖品
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/prize")
public class PrizeController {

	private final Logger LOG = LoggerFactory.getLogger(PrizeController.class);

	@Resource
	private IPrizeService prizeService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<PrizeListVo> queryPrize(@RequestBody Pagination<Prize> query) {
		Page<PrizeListVo> page = prizeService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addPrize(@RequestBody PrizeBo prizeBo) {
		try {
			PrizeListVo prizeListVo = prizeService.addPrize(prizeBo);
			return RestUtils.successWhenNotNull(prizeListVo);
		} catch (BusinessException ex) {
			LOG.error("add Prize failure : {}", prizeBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updatePrize(@RequestBody PrizeBo prizeBo) {
		try {
			PrizeListVo prizeListVo = prizeService.updatePrize(prizeBo);
			return RestUtils.successWhenNotNull(prizeListVo);
		} catch (BusinessException ex) {
			LOG.error("update Prize failure : {}", prizeBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removePrizeById(@RequestParam("id") int prizeId) {
		try {
			prizeService.removePrizeById(prizeId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Prize failure : id={}", prizeId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getPrizeVoById(@RequestParam("id") int prizeId) {
		try {
			PrizeVo prizeVo = prizeService.getPrizeVoById(prizeId);
			return RestUtils.successWhenNotNull(prizeVo);
		} catch (BusinessException ex) {
			LOG.error("get Prize failure : id={}", prizeId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getPrizeBoById(@RequestParam("id") int prizeId) {
		try {
			PrizeBo prizeBo = prizeService.getPrizeBoById(prizeId);
			return RestUtils.successWhenNotNull(prizeBo);
		} catch (BusinessException ex) {
			LOG.error("get Prize failure : id={}", prizeId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}