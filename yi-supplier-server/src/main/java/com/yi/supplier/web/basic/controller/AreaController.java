/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.supplier.web.basic.controller;

import com.yi.core.basic.domain.bo.AreaBo;
import com.yi.core.basic.domain.entity.Area;
import com.yi.core.basic.domain.vo.AreaListVo;
import com.yi.core.basic.domain.vo.AreaVo;
import com.yi.core.basic.service.IAreaService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 省市区 基础数据
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/area")
public class AreaController {

	private final Logger LOG = LoggerFactory.getLogger(AreaController.class);

	@Resource
	private IAreaService areaService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<AreaListVo> queryArea(@RequestBody Pagination<Area> query) {
		Page<AreaListVo> page = areaService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewArea(@RequestParam("id") int areaId) {
		try {
			AreaVo area = areaService.getAreaVoById(areaId);
			return RestUtils.successWhenNotNull(area);
		} catch (BusinessException ex) {
			LOG.error("get Area failure : id=areaId", ex);
			return RestUtils.error("get Area failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addArea(@RequestBody AreaBo area) {
		try {
			AreaVo dbArea = areaService.addArea(area);
			return RestUtils.successWhenNotNull(dbArea);
		} catch (BusinessException ex) {
			LOG.error("add Area failure : area", area, ex);
			return RestUtils.error("add Area failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateArea(@RequestBody AreaBo area) {
		try {
			AreaVo dbArea = areaService.updateArea(area);
			return RestUtils.successWhenNotNull(dbArea);
		} catch (BusinessException ex) {
			LOG.error("update Area failure : area", area, ex);
			return RestUtils.error("update Area failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeAreaById(@RequestParam("id") int areaId) {
		try {
			areaService.removeAreaById(areaId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Area failure : id=areaId", ex);
			return RestUtils.error("remove Area failure : " + ex.getMessage());
		}
	}

	/**
	 * 查询地区所有数据
	 */
	@RequestMapping(value = "getAreas", method = RequestMethod.GET)
	public RestResult getAreas(@RequestParam("parentId") Integer parentId) {
		try {
			List<Area> dbAreas = areaService.getAreasByParentId(parentId);
			return RestUtils.success(dbAreas);
		} catch (Exception ex) {
			LOG.error("getAreas failure :", ex);
			return RestUtils.error("getAreas failure : " + ex.getMessage());
		}
	}

	/**
	 * 查询地区所有数据
	 */
	@RequestMapping(value = "getAllAreas", method = RequestMethod.GET)
	public RestResult getAllAreas() {
		try {
			List<AreaListVo> dbAreas = areaService.getAllAreas();
			return RestUtils.success(dbAreas);
		} catch (Exception ex) {
			LOG.error("getAreas failure :", ex);
			return RestUtils.error("getAreas failure : " + ex.getMessage());
		}
	}

	/**
	 * 查询省
	 */
	@RequestMapping(value = "getProvinces", method = RequestMethod.GET)
	public RestResult getProvinces() {
		try {
			return RestUtils.success(areaService.getProvinces());
		} catch (Exception ex) {
			LOG.error("getProvinces failure :", ex);
			return RestUtils.error("getProvinces failure : " + ex.getMessage());
		}
	}
}