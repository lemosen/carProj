package com.yi.webserver.web.cms;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.cms.CmsEnum;
import com.yi.core.cms.service.IAdvertisementService;
import com.yi.core.cms.service.IRecommendService;
import com.yihz.common.json.RestResult;
import com.yihz.common.utils.web.RestUtils;

/**
 * 内容管理相关
 * 
 * @author xuyh
 *
 */
@RestController
@RequestMapping(value = "/cms")
public class CmsController {

	private final Logger LOG = LoggerFactory.getLogger(CmsController.class);

	@Resource
	private IAdvertisementService advertisementService;

	@Resource
	private IRecommendService recommendService;

	/**
	 * 获取轮播图
	 * 
	 * @return
	 */
	@RequestMapping(value = "getBanner", method = RequestMethod.GET)
	public RestResult getBanner() {
		try {
			// 轮播图
			return RestUtils.success(advertisementService.getAdvertisementListVoForApp(CmsEnum.POSITION_TYPE_BANNER.getCode()));
		} catch (Exception e) {
			LOG.error("getBanner error:{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 根据位置获取推荐数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "getRecommends", method = RequestMethod.GET)
	public RestResult getRecommends(@RequestParam("positionType") Integer positionType, @RequestParam(name = "city", required = false) String city) {
		try {
			return RestUtils.success(recommendService.queryRecommends(positionType, city));
		} catch (Exception e) {
			LOG.error("getRecommends error{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

}
