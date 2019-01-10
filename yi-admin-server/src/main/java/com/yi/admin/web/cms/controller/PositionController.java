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

import com.yi.core.cms.domain.bo.PositionBo;
import com.yi.core.cms.domain.entity.Position;
import com.yi.core.cms.domain.vo.PositionListVo;
import com.yi.core.cms.domain.vo.PositionVo;
import com.yi.core.cms.service.IPositionService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 位置管理
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/position")
public class PositionController {

	private final Logger LOG = LoggerFactory.getLogger(PositionController.class);

	@Resource
	private IPositionService positionService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<PositionListVo> queryPosition(@RequestBody Pagination<Position> query) {
		Page<PositionListVo> page = positionService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewPosition(@RequestParam("id") int positionId) {
		try {
			PositionVo position = positionService.getPositionVoById(positionId);
			return RestUtils.successWhenNotNull(position);
		} catch (BusinessException ex) {
			LOG.error("get Position failure : id=positionId", ex);
			return RestUtils.error("get Position failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addPosition(@RequestBody PositionBo position) {
		try {
			PositionVo dbPosition = positionService.addPosition(position);
			return RestUtils.successWhenNotNull(dbPosition);
		} catch (BusinessException ex) {
			LOG.error("add Position failure : position", position, ex);
			return RestUtils.error("add Position failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updatePosition(@RequestBody Position position) {
		try {
			PositionVo dbPosition = positionService.updatePosition(position);
			return RestUtils.successWhenNotNull(dbPosition);
		} catch (BusinessException ex) {
			LOG.error("update Position failure : position", position, ex);
			return RestUtils.error("update Position failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removePositionById(@RequestParam("id") int positionId) {
		try {
			positionService.removePositionById(positionId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Position failure : id=positionId", ex);
			return RestUtils.error("remove Position failure : " + ex.getMessage());
		}
	}

	/**
	 * 禁用
	 **/
	@RequestMapping(value = "updateStateDisable", method = RequestMethod.GET)
	public RestResult updateStateDisable(@RequestParam("id") int id) {
		try {
			return RestUtils.success(positionService.updateStateDisable(id));
		} catch (Exception ex) {
			LOG.error("remove Position failure : id=positionId", ex);
			return RestUtils.error("remove Position failure : " + ex.getMessage());
		}
	}

	/**
	 * 启用
	 **/
	@RequestMapping(value = "updateStateEnable", method = RequestMethod.GET)
	public RestResult updateStateEnable(@RequestParam("id") int id) {
		try {
			return RestUtils.success(positionService.updateStateEnable(id));
		} catch (Exception ex) {
			LOG.error("remove Position failure : id=positionId", ex);
			return RestUtils.error("remove Position failure : " + ex.getMessage());
		}
	}

}