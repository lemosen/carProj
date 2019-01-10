/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.supplier.web.commodity.controller;

import javax.annotation.Resource;

import com.yi.core.commodity.domain.vo.BrandListVo;
import com.yi.core.commodity.domain.vo.BrandVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.commodity.domain.entity.Brand;
import com.yi.core.commodity.service.IBrandService;
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
@RequestMapping(value = "/brand")
public class BrandController {

	private final Logger LOG = LoggerFactory.getLogger(BrandController.class);

	@Resource
	private IBrandService brandService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<BrandListVo> queryBrand(@RequestBody Pagination<Brand> query) {
		Page<BrandListVo> page = brandService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewBrand(@RequestParam("id") int brandId) {
		try {
			BrandVo brand = brandService.getBrandVoById(brandId);
			return RestUtils.successWhenNotNull(brand);
		} catch (BusinessException ex) {
			LOG.error("get Brand failure : id=brandId", ex);
			return RestUtils.error("get Brand failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addBrand(@RequestBody Brand brand) {
		try {
			Brand dbBrand = brandService.addBrand(brand);
			return RestUtils.successWhenNotNull(dbBrand);
		} catch (BusinessException ex) {
			LOG.error("add Brand failure : brand", brand, ex);
			return RestUtils.error("add Brand failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateBrand(@RequestBody Brand brand) {
		try {
			Brand dbBrand = brandService.updateBrand(brand);
			return RestUtils.successWhenNotNull(dbBrand);
		} catch (BusinessException ex) {
			LOG.error("update Brand failure : brand", brand, ex);
			return RestUtils.error("update Brand failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeBrandById(@RequestParam("id") int brandId) {
		try {
			brandService.removeBrandById(brandId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Brand failure : id=brandId", ex);
			return RestUtils.error("remove Brand failure : " + ex.getMessage());
		}
	}
}