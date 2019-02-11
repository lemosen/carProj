/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.basic.controller;

import com.yi.core.basic.domain.bo.BasicRuleBo;
import com.yi.core.basic.domain.entity.BasicRule;
import com.yi.core.basic.domain.vo.BasicRuleListVo;
import com.yi.core.basic.domain.vo.BasicRuleVo;


import com.yi.core.basic.service.IBasicRuleService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/basicRule")
public class BasicRuleController {

    private final Logger LOG = LoggerFactory.getLogger(BasicRuleController.class);


    @Resource
    private IBasicRuleService basicRuleService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<BasicRuleListVo> queryBasicRule(@RequestBody Pagination<BasicRule> query) {
        Page<BasicRuleListVo> page = basicRuleService.queryListVo(query);
        return page;
    }

    /**
     * 查看对象
     **/
    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public RestResult viewBasicRule(@RequestParam("id") int basicRuleId) {
        try {
            BasicRuleVo basicRule = basicRuleService.getBasicRuleVoById(basicRuleId);
            return RestUtils.successWhenNotNull(basicRule);
        } catch (BusinessException ex) {
            LOG.error("get BasicRule failure : id=basicRuleId", ex);
            return RestUtils.error("get BasicRule failure : " + ex.getMessage());
        }
    }


    /**
     * 保存新增对象
     **/
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public RestResult addBasicRule(@RequestBody BasicRuleBo basicRule) {
        try {
            BasicRuleVo dbBasicRule = basicRuleService.addBasicRule(basicRule);
            return RestUtils.successWhenNotNull(dbBasicRule);
        } catch (BusinessException ex) {
            LOG.error("add BasicRule failure : basicRule", basicRule, ex);
            return RestUtils.error("add BasicRule failure : " + ex.getMessage());
        }
    }

    /**
     * 保存更新对象
     **/
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public RestResult updateBasicRule(@RequestBody BasicRuleBo basicRule) {
        try {
            BasicRuleVo dbBasicRule = basicRuleService.updateBasicRule(basicRule);
            return RestUtils.successWhenNotNull(dbBasicRule);
        } catch (BusinessException ex) {
            LOG.error("update BasicRule failure : basicRule", basicRule, ex);
            return RestUtils.error("update BasicRule failure : " + ex.getMessage());
        }
    }

    /**
     * 删除对象
     **/
    @RequestMapping(value = "removeById", method = RequestMethod.GET)
    public RestResult removeBasicRuleById(@RequestParam("id") int basicRuleId) {
        try {
            basicRuleService.removeBasicRuleById(basicRuleId);
            return RestUtils.success(true);
        } catch (Exception ex) {
            LOG.error("remove BasicRule failure : id=basicRuleId", ex);
            return RestUtils.error("remove BasicRule failure : " + ex.getMessage());
        }
    }

    /**
     * 查看初始类容
     **/
    @RequestMapping(value = "getAll", method = RequestMethod.POST)
    public RestResult getAll() {
        try {

            return RestUtils.success( basicRuleService.getAll());
        } catch (Exception ex) {
            LOG.error("remove BasicRule failure : id=basicRuleId", ex);
            return RestUtils.error("remove BasicRule failure : " + ex.getMessage());
        }
    }
}