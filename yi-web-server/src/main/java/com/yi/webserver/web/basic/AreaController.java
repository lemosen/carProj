package com.yi.webserver.web.basic;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.basic.domain.entity.Area;
import com.yi.core.basic.domain.vo.AreaListVo;
import com.yi.core.basic.service.IAreaService;
import com.yihz.common.json.RestResult;
import com.yihz.common.utils.web.RestUtils;

@RestController
@RequestMapping(value = "/area")
public class AreaController {

	private final Logger LOG = LoggerFactory.getLogger(AreaController.class);

	@Resource
	private IAreaService areaService;
//todo getAllAreas getAreas整合成一个接口返回地区树
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
			return RestUtils.error(ex.getMessage());
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
			return RestUtils.error(ex.getMessage());
		}
	}

}
