/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.basic.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.basic.domain.bo.AddressBo;
import com.yi.core.basic.domain.entity.Address;
import com.yi.core.basic.domain.vo.AddressListVo;
import com.yi.core.basic.domain.vo.AddressVo;
import com.yi.core.basic.service.IAddressService;
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
@RequestMapping(value = "/address")
public class AddressController {

	private final Logger LOG = LoggerFactory.getLogger(AddressController.class);

	@Resource
	private IAddressService addressService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<Address> queryAddress(@RequestBody Pagination<Address> query) {
		Page<Address> page = addressService.query(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewAddress(@RequestParam("id") int addressId) {
		try {
			AddressVo address = addressService.getAddressVoById(addressId);
			return RestUtils.successWhenNotNull(address);
		} catch (BusinessException ex) {
			LOG.error("get Address failure : id=addressId", ex);
			return RestUtils.error("get Address failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addAddress(@RequestBody AddressBo address) {
		try {
			AddressVo dbAddress = addressService.addAddress(address);
			return RestUtils.successWhenNotNull(dbAddress);
		} catch (BusinessException ex) {
			LOG.error("add Address failure : address", address, ex);
			return RestUtils.error("add Address failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateAddress(@RequestBody AddressBo address) {
		try {
			AddressVo dbAddress = addressService.updateAddress(address);
			return RestUtils.successWhenNotNull(dbAddress);
		} catch (BusinessException ex) {
			LOG.error("update Address failure : address", address, ex);
			return RestUtils.error("update Address failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeAddressById(@RequestParam("id") int addressId) {
		try {
			addressService.removeAddressById(addressId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Address failure : id=addressId", ex);
			return RestUtils.error("remove Address failure : " + ex.getMessage());
		}
	}
}