package com.yi.webserver.web.common;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.basic.service.IAreaService;

/**
 * 对外 公共方法
 * 
 * @author xuyh
 *
 */
@RestController
@RequestMapping("common")
public class CommonController {

	@Resource
	private IAreaService areaService;

}
