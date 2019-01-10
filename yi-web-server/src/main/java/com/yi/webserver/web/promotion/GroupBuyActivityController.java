/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.webserver.web.promotion;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.promotion.domain.entity.GroupBuyActivity;
import com.yi.core.promotion.domain.listVo.GroupBuyActivityListVo;
import com.yi.core.promotion.domain.vo.GroupBuyActivityVo;
import com.yi.core.promotion.service.IGroupBuyActivityService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 团购活动
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/groupBuyActivity")
public class GroupBuyActivityController {

	private final Logger LOG = LoggerFactory.getLogger(GroupBuyActivityController.class);

	@Resource
	private IGroupBuyActivityService groupBuyActivityService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<GroupBuyActivityListVo> queryGroupBuyActivity(@RequestBody Pagination<GroupBuyActivity> query) {
		Page<GroupBuyActivityListVo> page = groupBuyActivityService.queryListVoForApp(query);
		return page;
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getVoById", method = RequestMethod.GET)
	public RestResult getGroupBuyActivityVoById(@RequestParam("id") int groupBuyActivityId) {
		try {
			GroupBuyActivityVo groupBuyActivityVo = groupBuyActivityService.getVoById(groupBuyActivityId);
			return RestUtils.successWhenNotNull(groupBuyActivityVo);
		} catch (BusinessException ex) {
			LOG.error("get GroupBuyActivity failure : id={}", groupBuyActivityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}
}