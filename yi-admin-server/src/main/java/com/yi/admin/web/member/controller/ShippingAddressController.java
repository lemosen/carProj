/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.member.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.member.domain.entity.ShippingAddress;
import com.yi.core.member.domain.vo.ShippingAddressListVo;
import com.yi.core.member.domain.vo.ShippingAddressVo;
import com.yi.core.member.service.IShippingAddressService;
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
@RequestMapping(value = "/shippingAddress")
public class ShippingAddressController {

	private final Logger LOG = LoggerFactory.getLogger(ShippingAddressController.class);

	@Resource
	private IShippingAddressService shippingAddressService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<ShippingAddressListVo> queryShippingAddress(@RequestBody Pagination<ShippingAddress> query) {
		Page<ShippingAddressListVo> page = shippingAddressService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewShippingAddress(@RequestParam("id") int shippingAddressId) {
		try {
			ShippingAddressVo shippingAddress = shippingAddressService.getShippingAddressVoById(shippingAddressId);
			return RestUtils.successWhenNotNull(shippingAddress);
		} catch (BusinessException ex) {
			LOG.error("get ShippingAddress failure : id=shippingAddressId", ex);
			return RestUtils.error("get ShippingAddress failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addShippingAddress(@RequestBody ShippingAddress shippingAddress) {
		try {
			ShippingAddressVo dbShippingAddress = shippingAddressService.addShippingAddress(shippingAddress);
			return RestUtils.successWhenNotNull(dbShippingAddress);
		} catch (BusinessException ex) {
			LOG.error("add ShippingAddress failure : shippingAddress", shippingAddress, ex);
			return RestUtils.error("add ShippingAddress failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateShippingAddress(@RequestBody ShippingAddress shippingAddress) {
		try {
			ShippingAddressVo dbShippingAddress = shippingAddressService.updateShippingAddress(shippingAddress);
			return RestUtils.successWhenNotNull(dbShippingAddress);
		} catch (BusinessException ex) {
			LOG.error("update ShippingAddress failure : shippingAddress", shippingAddress, ex);
			return RestUtils.error("update ShippingAddress failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeShippingAddressById(@RequestParam("id") int shippingAddressId) {
		try {
			shippingAddressService.removeShippingAddressById(shippingAddressId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove ShippingAddress failure : id=shippingAddressId", ex);
			return RestUtils.error("remove ShippingAddress failure : " + ex.getMessage());
		}
	}
}