/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.supplier.web.order.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.yi.core.supplier.domain.bo.SupplierBo;
import com.yi.core.supplier.service.ISupplierService;
import com.yi.supplier.web.auth.jwt.JwtSupplierToken;
import com.yi.supplier.web.auth.jwt.SupplierToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.order.domain.bo.FreightTemplateConfigBo;
import com.yi.core.order.domain.entity.FreightTemplateConfig;
import com.yi.core.order.domain.vo.FreightTemplateConfigListVo;
import com.yi.core.order.domain.vo.FreightTemplateConfigVo;
import com.yi.core.order.service.IFreightTemplateConfigService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 运费模板配置
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/freightTemplateConfig")
public class FreightTemplateConfigController {

	private final Logger LOG = LoggerFactory.getLogger(FreightTemplateConfigController.class);

	@Resource
	private ISupplierService supplierService;

	@Resource
	private IFreightTemplateConfigService freightTemplateConfigService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<FreightTemplateConfigListVo> queryFreightTemplateConfig(@RequestBody Pagination<FreightTemplateConfig> query) {
		Page<FreightTemplateConfigListVo> page = freightTemplateConfigService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addFreightTemplateConfig(@RequestBody FreightTemplateConfigBo freightTemplateConfigBo, HttpServletRequest request) {
		try {
			SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
			SupplierBo supplierBo = supplierService.getSupplierBoById(supplierToken.getId());
			freightTemplateConfigBo.setSupplier(supplierBo);
			FreightTemplateConfigVo freightTemplateConfigVo = freightTemplateConfigService.addFreightTemplateConfig(freightTemplateConfigBo);
			return RestUtils.successWhenNotNull(freightTemplateConfigVo);
		} catch (BusinessException ex) {
			LOG.error("add FreightTemplateConfig failure : freightTemplateConfigBo", freightTemplateConfigBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateFreightTemplateConfig(@RequestBody FreightTemplateConfigBo freightTemplateConfigBo, HttpServletRequest request) {
		try {
			SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
			SupplierBo supplierBo = supplierService.getSupplierBoById(supplierToken.getId());
			freightTemplateConfigBo.setSupplier(supplierBo);
			FreightTemplateConfigVo freightTemplateConfigVo = freightTemplateConfigService.updateFreightTemplateConfig(freightTemplateConfigBo);
			return RestUtils.successWhenNotNull(freightTemplateConfigVo);
		} catch (BusinessException ex) {
			LOG.error("update FreightTemplateConfig failure : freightTemplateConfigBo", freightTemplateConfigBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeFreightTemplateConfigById(@RequestParam("id") int freightTemplateConfigId) {
		try {
			freightTemplateConfigService.removeFreightTemplateConfigById(freightTemplateConfigId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove FreightTemplateConfig failure : id=freightTemplateConfigId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getFreightTemplateConfigVoById(@RequestParam("id") int freightTemplateConfigId) {
		try {
			FreightTemplateConfigVo freightTemplateConfigVo = freightTemplateConfigService.getVoById(freightTemplateConfigId);
			return RestUtils.successWhenNotNull(freightTemplateConfigVo);
		} catch (BusinessException ex) {
			LOG.error("get FreightTemplateConfig failure : id=freightTemplateConfigId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}
}