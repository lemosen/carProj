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

import com.yi.core.basic.domain.entity.PaymentConfig;
import com.yi.core.basic.domain.vo.PaymentConfigListVo;
import com.yi.core.basic.domain.vo.PaymentConfigVo;
import com.yi.core.basic.service.IPaymentConfigService;
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
@RequestMapping(value = "/paymentConfig")
public class PaymentConfigController {

	private final Logger LOG = LoggerFactory.getLogger(PaymentConfigController.class);

	@Resource
	private IPaymentConfigService paymentConfigService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<PaymentConfigListVo> queryPaymentConfig(@RequestBody Pagination<PaymentConfig> query) {
		Page<PaymentConfigListVo> page = paymentConfigService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewPaymentConfig(@RequestParam("id") int paymentConfigId) {
		try {
			PaymentConfigVo paymentConfig = paymentConfigService.getPaymentConfigVoById(paymentConfigId);
			return RestUtils.successWhenNotNull(paymentConfig);
		} catch (BusinessException ex) {
			LOG.error("get PaymentConfig failure : id=paymentConfigId", ex);
			return RestUtils.error("get PaymentConfig failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addPaymentConfig(@RequestBody PaymentConfig paymentConfig) {
		try {
			PaymentConfigVo dbPaymentConfig = paymentConfigService.addPaymentConfig(paymentConfig);
			return RestUtils.successWhenNotNull(dbPaymentConfig);
		} catch (BusinessException ex) {
			LOG.error("add PaymentConfig failure : paymentConfig", paymentConfig, ex);
			return RestUtils.error("add PaymentConfig failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updatePaymentConfig(@RequestBody PaymentConfig paymentConfig) {
		try {
			PaymentConfigVo dbPaymentConfig = paymentConfigService.updatePaymentConfig(paymentConfig);
			return RestUtils.successWhenNotNull(dbPaymentConfig);
		} catch (BusinessException ex) {
			LOG.error("update PaymentConfig failure : paymentConfig", paymentConfig, ex);
			return RestUtils.error("update PaymentConfig failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removePaymentConfigById(@RequestParam("id") int paymentConfigId) {
		try {
			paymentConfigService.removePaymentConfigById(paymentConfigId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove PaymentConfig failure : id=paymentConfigId", ex);
			return RestUtils.error("remove PaymentConfig failure : " + ex.getMessage());
		}
	}
}