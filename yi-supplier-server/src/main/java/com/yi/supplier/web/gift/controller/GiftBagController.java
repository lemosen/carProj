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

import com.yi.core.gift.domain.bo.GiftBagBo;
import com.yi.core.gift.domain.entity.GiftBag;
import com.yi.core.gift.domain.vo.GiftBagListVo;
import com.yi.core.gift.domain.vo.GiftBagVo;
import com.yi.core.gift.service.IGiftBagService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 礼包
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/giftBag")
public class GiftBagController {

	private final Logger LOG = LoggerFactory.getLogger(GiftBagController.class);

	@Resource
	private IGiftBagService giftBagService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<GiftBagListVo> queryGiftBag(@RequestBody Pagination<GiftBag> query) {
		Page<GiftBagListVo> page = giftBagService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addGiftBag(@RequestBody GiftBagBo giftBagBo) {
		try {
			GiftBagVo giftBagVo = giftBagService.addGiftBag(giftBagBo);
			return RestUtils.successWhenNotNull(giftBagVo);
		} catch (BusinessException ex) {
			LOG.error("add GiftBag failure : giftBagBo", giftBagBo, ex);
			return RestUtils.error("add GiftBag failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateGiftBag(@RequestBody GiftBagBo giftBagBo) {
		try {
			GiftBagVo giftBagVo = giftBagService.updateGiftBag(giftBagBo);
			return RestUtils.successWhenNotNull(giftBagVo);
		} catch (BusinessException ex) {
			LOG.error("update GiftBag failure : giftBagBo", giftBagBo, ex);
			return RestUtils.error("update GiftBag failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeGiftBagById(@RequestParam("id") int giftBagId) {
		try {
			giftBagService.removeGiftBagById(giftBagId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove GiftBag failure : id=giftBagId", ex);
			return RestUtils.error("remove GiftBag failure : " + ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getGiftBagBoById(@RequestParam("id") int giftBagId) {
		try {
			GiftBagBo giftBagBo = giftBagService.getBoById(giftBagId);
			return RestUtils.successWhenNotNull(giftBagBo);
		} catch (BusinessException ex) {
			LOG.error("get GiftBag failure : id=giftBagId", ex);
			return RestUtils.error("get GiftBag failure : " + ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getVoById", method = RequestMethod.GET)
	public RestResult getGiftBagVoById(@RequestParam("id") int giftBagId) {
		try {
			GiftBagVo giftBagVo = giftBagService.getVoById(giftBagId);
			return RestUtils.successWhenNotNull(giftBagVo);
		} catch (BusinessException ex) {
			LOG.error("get GiftBag failure : id=giftBagId", ex);
			return RestUtils.error("get GiftBag failure : " + ex.getMessage());
		}
	}
}