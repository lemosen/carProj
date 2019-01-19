/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.supplier.web.commodity.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.commodity.domain.bo.CategoryBo;
import com.yi.core.commodity.domain.entity.Category;
import com.yi.core.commodity.domain.vo.CategoryListVo;
import com.yi.core.commodity.domain.vo.CategoryVo;
import com.yi.core.commodity.service.ICategoryService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 商品分类
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/category")
public class CategoryController {

	private final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

	@Resource
	private ICategoryService categoryService;

	/**
	 * 获取所有分类
	 **/
	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public RestResult getAll() {
		try {
			return RestUtils.successWhenNotNull(categoryService.getAll());
		} catch (BusinessException ex) {
			LOG.error("getAll Category failure : category", ex);
			return RestUtils.error("add Category failure : " + ex.getMessage());
		}
	}

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<CategoryListVo> queryCategory(@RequestBody Pagination<Category> query) {
		Page<CategoryListVo> page = categoryService.queryListVo(query);

		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewCategory(@RequestParam("id") int categoryId) {
		try {
			CategoryVo category = categoryService.getCategoryVoById(categoryId);
			return RestUtils.successWhenNotNull(category);
		} catch (BusinessException ex) {
			LOG.error("get Category failure : id=categoryId", ex);
			return RestUtils.error("get Category failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addCategory(@RequestBody CategoryBo categoryBo) {
		try {
			CategoryVo dbCategory = categoryService.addCategory(categoryBo);
			return RestUtils.successWhenNotNull(dbCategory);
		} catch (BusinessException ex) {
			LOG.error("add Category failure : category", categoryBo, ex);
			return RestUtils.error("add Category failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateCategory(@RequestBody CategoryBo category) {
		try {
			CategoryVo dbCategory = categoryService.updateCategory(category);
			return RestUtils.successWhenNotNull(dbCategory);
		} catch (BusinessException ex) {
			LOG.error("update Category failure : category", category, ex);
			return RestUtils.error("update Category failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeCategoryById(@RequestParam("id") int categoryId) {
		try {
			categoryService.removeCategoryById(categoryId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Category failure : id=categoryId", ex);
			return RestUtils.error("remove Category failure : " + ex.getMessage());
		}
	}
}