/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.cms.controller;

import javax.annotation.Resource;

import com.yi.core.cms.domain.bo.RecommendBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.cms.domain.entity.Recommend;
import com.yi.core.cms.domain.vo.RecommendListVo;
import com.yi.core.cms.domain.vo.RecommendVo;
import com.yi.core.cms.service.IRecommendService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 推荐位
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/recommend")
public class RecommendController {

	private final Logger LOG = LoggerFactory.getLogger(RecommendController.class);

	@Resource
	private IRecommendService recommendService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<RecommendListVo> queryRecommend(@RequestBody Pagination<Recommend> query) {
		Page<RecommendListVo> page = recommendService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewRecommend(@RequestParam("id") int recommendId) {
		try {
			RecommendVo recommend = recommendService.getRecommendVoById(recommendId);
			return RestUtils.successWhenNotNull(recommend);
		} catch (BusinessException ex) {
			LOG.error("get Recommend failure : id=recommendId", ex);
			return RestUtils.error("get Recommend failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addRecommend(@RequestBody RecommendBo recommend) {
		try {
			RecommendVo dbRecommend = recommendService.addRecommend(recommend);
			return RestUtils.successWhenNotNull(dbRecommend);
		} catch (BusinessException ex) {
			LOG.error("add Recommend failure : recommend", recommend, ex);
			return RestUtils.error("add Recommend failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateRecommend(@RequestBody RecommendBo recommend) {
		try {
			RecommendVo dbRecommend = recommendService.updateRecommend(recommend);
			return RestUtils.successWhenNotNull(dbRecommend);
		} catch (BusinessException ex) {
			LOG.error("update Recommend failure : recommend", recommend, ex);
			return RestUtils.error("update Recommend failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeRecommendById(@RequestParam("id") int recommendId) {
		try {
			recommendService.removeRecommendById(recommendId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Recommend failure : id=recommendId", ex);
			return RestUtils.error("remove Recommend failure : " + ex.getMessage());
		}
	}


	@RequestMapping(value = "updateStateDisable", method = RequestMethod.GET)
	public RestResult updateStateDisable(@RequestParam("recommendId") int recommendId) {
		try {
			RecommendVo restResult=recommendService.updateStateDisable(recommendId);
			return RestUtils.success(restResult);
		} catch (Exception ex) {
			LOG.error("remove Recommend failure : id=recommendId", ex);
			return RestUtils.error("remove Recommend failure : " + ex.getMessage());
		}
	}

	@RequestMapping(value = "updateStateEnable", method = RequestMethod.GET)
	public RestResult updateStateEnable(@RequestParam("recommendId") int recommendId) {
		try {
			RecommendVo restResult=recommendService.updateStateEnable(recommendId);
			return RestUtils.success(restResult);
		} catch (Exception ex) {
			LOG.error("remove Recommend failure : id=recommendId", ex);
			return RestUtils.error("remove Recommend failure : " + ex.getMessage());
		}
	}

}