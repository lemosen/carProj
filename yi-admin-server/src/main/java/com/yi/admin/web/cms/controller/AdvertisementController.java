/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.cms.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.cms.domain.bo.AdvertisementBo;
import com.yi.core.cms.domain.entity.Advertisement;
import com.yi.core.cms.domain.vo.AdvertisementListVo;
import com.yi.core.cms.domain.vo.AdvertisementVo;
import com.yi.core.cms.service.IAdvertisementService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 广告位
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/advertisement")
public class AdvertisementController {

	private final Logger LOG = LoggerFactory.getLogger(AdvertisementController.class);

	@Resource
	private IAdvertisementService advertisementService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<AdvertisementListVo> queryAdvertisement(@RequestBody Pagination<Advertisement> query) {
		Page<AdvertisementListVo> page = advertisementService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewAdvertisement(@RequestParam("id") int advertisementId) {
		try {
			AdvertisementVo advertisement = advertisementService.getAdvertisementVoById(advertisementId);
			return RestUtils.successWhenNotNull(advertisement);
		} catch (BusinessException ex) {
			LOG.error("get Advertisement failure : id=advertisementId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addAdvertisement(@RequestBody AdvertisementBo advertisement) {
		try {
			AdvertisementVo dbAdvertisement = advertisementService.addAdvertisement(advertisement);
			return RestUtils.successWhenNotNull(dbAdvertisement);
		} catch (BusinessException ex) {
			LOG.error("add Advertisement failure : advertisement", advertisement, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateAdvertisement(@RequestBody AdvertisementBo advertisement) {
		try {
			AdvertisementVo dbAdvertisement = advertisementService.updateAdvertisement(advertisement);
			return RestUtils.successWhenNotNull(dbAdvertisement);
		} catch (BusinessException ex) {
			LOG.error("update Advertisement failure : advertisement", advertisement, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeAdvertisementById(@RequestParam("id") int advertisementId) {
		try {
			advertisementService.removeAdvertisementById(advertisementId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Advertisement failure : id=advertisementId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	@RequestMapping(value = "updateStateDisable", method = RequestMethod.GET)
	public RestResult updateStateDisable(@RequestParam("advertisementId") int advertisementId) {
		try {
			AdvertisementVo restResult = advertisementService.updateStateDisable(advertisementId);
			return RestUtils.success(restResult);
		} catch (Exception ex) {
			LOG.error("remove Advertisement failure : id=advertisementId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	@RequestMapping(value = "updateStateEnable", method = RequestMethod.GET)
	public RestResult updateStateEnable(@RequestParam("advertisementId") int advertisementId) {
		try {
			AdvertisementVo restResult = advertisementService.updateStateEnable(advertisementId);
			return RestUtils.success(restResult);
		} catch (Exception ex) {
			LOG.error("remove Advertisement failure : id=advertisementId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}