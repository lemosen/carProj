/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.auth.controller;

import javax.annotation.Resource;

import com.yi.core.auth.domain.bo.RescBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.auth.domain.entity.Resc;
import com.yi.core.auth.domain.vo.RescListVo;
import com.yi.core.auth.domain.vo.RescVo;
import com.yi.core.auth.service.IRescService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 资源
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/resc")
public class RescController {

	private final Logger LOG = LoggerFactory.getLogger(RescController.class);

	@Resource
	private IRescService rescService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<RescListVo> queryResc(@RequestBody Pagination<Resc> query) {
		Page<RescListVo> page = rescService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewResc(@RequestParam("id") int rescId) {
		try {
			RescVo resc = rescService.getRescVoById(rescId);
			return RestUtils.successWhenNotNull(resc);
		} catch (BusinessException ex) {
			LOG.error("get Resc failure : id=rescId", ex);
			return RestUtils.error("get Resc failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addResc(@RequestBody RescBo resc) {
		try {
			RescVo dbResc = rescService.addResc(resc);
			return RestUtils.successWhenNotNull(dbResc);
		} catch (BusinessException ex) {
			LOG.error("add Resc failure : resc", resc, ex);
			return RestUtils.error("add Resc failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateResc(@RequestBody RescBo resc) {
		try {
			RescVo dbResc = rescService.updateResc(resc);
			return RestUtils.successWhenNotNull(dbResc);
		} catch (BusinessException ex) {
			LOG.error("update Resc failure : resc", resc, ex);
			return RestUtils.error("update Resc failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeRescById(@RequestParam("id") int rescId) {
		try {
			rescService.removeRescById(rescId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Resc failure : id=rescId", ex);
			return RestUtils.error("remove Resc failure : " + ex.getMessage());
		}
	}

	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public RestResult getAll() {
		try {
			return RestUtils.successWhenNotNull(rescService.getRescTree());
		} catch (BusinessException ex) {
			LOG.error("getAll Resc failure : Resc", ex);
			return RestUtils.error(ex.getMessage());
		}
	}
}