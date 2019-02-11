package com.yi.webserver.web.basic;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.basic.service.IBasicRuleService;
import com.yi.core.basic.service.IQuestionService;
import com.yi.core.basic.service.IQuestionTypeService;
import com.yihz.common.json.RestResult;
import com.yihz.common.utils.web.RestUtils;

/**
 * 规则信息
 * 
 * @author xuyh
 *
 */
@RestController
@RequestMapping(value = "/ruleInfo")
public class RuleInfoController {
	private final Logger LOG = LoggerFactory.getLogger(AreaController.class);

	@Resource
	private IQuestionTypeService questionTypeService;

	@Resource
	private IQuestionService questionService;

	@Resource
	private IBasicRuleService basicRuleService;

	/**
	 * 问题类型列表
	 */
	@RequestMapping(value = "getQuestionType", method = RequestMethod.GET)
	public RestResult getQuestionType() {
		try {
			return RestUtils.success(questionTypeService.getAll());
		} catch (Exception e) {
			LOG.error("getQuestionType error:{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 回答问题列表
	 */
	@RequestMapping(value = "getQuestion", method = RequestMethod.GET)
	public RestResult getQuestion(@RequestParam("id") Integer id) {
		try {
			return RestUtils.success(questionService.getQuestion(id));
		} catch (Exception e) {
			LOG.error("getQuestionType error:" + e.getMessage());
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 基础规则
	 */
	@RequestMapping(value = "getBasicRule", method = RequestMethod.GET)
	public RestResult getBasicRule() {
		try {
			return RestUtils.success(basicRuleService.getAll());
		} catch (Exception e) {
			LOG.error("getBasicRule error:" + e.getMessage());
			return RestUtils.error(e.getMessage());
		}
	}

}
