/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.supplier.web.gift.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.gift.domain.bo.GiftBo;
import com.yi.core.gift.domain.entity.Gift;
import com.yi.core.gift.domain.vo.GiftListVo;
import com.yi.core.gift.domain.vo.GiftVo;
import com.yi.core.gift.service.IGiftService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 *礼物
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/gift")
public class GiftController {

	private final Logger LOG = LoggerFactory.getLogger(GiftController.class);

	@Resource
	private IGiftService giftService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<GiftListVo> queryGift(@RequestBody Pagination<Gift> query) {
		Page<GiftListVo> page = giftService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addGift(@RequestBody GiftBo giftBo) {
		try {
			GiftVo giftVo = giftService.addGift(giftBo);
			return RestUtils.successWhenNotNull(giftVo);
		} catch (BusinessException ex) {
			LOG.error("add Gift failure : giftBo", giftBo, ex);
			return RestUtils.error("add Gift failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateGift(@RequestBody GiftBo giftBo) {
		try {
			GiftVo giftVo = giftService.updateGift(giftBo);
			return RestUtils.successWhenNotNull(giftVo);
		} catch (BusinessException ex) {
			LOG.error("update Gift failure : giftBo", giftBo, ex);
			return RestUtils.error("update Gift failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeGiftById(@RequestParam("id") int giftId) {
		try {
			giftService.removeGiftById(giftId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Gift failure : id=giftId", ex);
			return RestUtils.error("remove Gift failure : " + ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getGiftBoById(@RequestParam("id") int giftId) {
		try {
			GiftBo giftBo = giftService.getGiftBoById(giftId);
			return RestUtils.successWhenNotNull(giftBo);
		} catch (BusinessException ex) {
			LOG.error("get Gift failure : id=giftId", ex);
			return RestUtils.error("get Gift failure : " + ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getVoById", method = RequestMethod.GET)
	public RestResult getGiftVoById(@RequestParam("id") int giftId) {
		try {
			GiftVo giftVo = giftService.getGiftVoById(giftId);
			return RestUtils.successWhenNotNull(giftVo);
		} catch (BusinessException ex) {
			LOG.error("get Gift failure : id=giftId", ex);
			return RestUtils.error("get Gift failure : " + ex.getMessage());
		}
	}
}