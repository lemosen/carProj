/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.activity.controller;

import com.yi.core.activity.domain.bo.RebateGroupBo;
import com.yi.core.activity.domain.entity.RebateGroup;
import com.yi.core.activity.domain.vo.RebateGroupListVo;
import com.yi.core.activity.domain.vo.RebateGroupVo;


import com.yi.core.activity.service.IRebateGroupService;
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
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Deprecated
@RestController
@RequestMapping(value = "/rebateGroup")
public class RebateGroupController {

    private final Logger LOG = LoggerFactory.getLogger(RebateGroupController.class);


    @Resource
    private IRebateGroupService rebateGroupService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<RebateGroupListVo> queryRebateGroup(@RequestBody Pagination<RebateGroup> query) {
    Page<RebateGroupListVo> page = rebateGroupService.queryListVo(query);
        return page;
        }

        /**
        * 查看对象
        **/
        @RequestMapping(value = "getById", method = RequestMethod.GET)
        public RestResult viewRebateGroup(@RequestParam("id") int rebateGroupId) {
        try {
        RebateGroupVo rebateGroup = rebateGroupService.getRebateGroupVoById(rebateGroupId);
        return RestUtils.successWhenNotNull(rebateGroup);
        } catch (BusinessException ex) {
        LOG.error("get RebateGroup failure : id=rebateGroupId", ex);
        return RestUtils.error("get RebateGroup failure : " + ex.getMessage());
        }
        }


        /**
        * 保存新增对象
        **/
        @RequestMapping(value = "add", method = RequestMethod.POST)
        public RestResult addRebateGroup(@RequestBody RebateGroupBo rebateGroup) {
        try {
        RebateGroupVo dbRebateGroup = rebateGroupService.addRebateGroup(rebateGroup);
        return RestUtils.successWhenNotNull(dbRebateGroup);
        } catch (BusinessException ex) {
        LOG.error("add RebateGroup failure : rebateGroup", rebateGroup, ex);
        return RestUtils.error("add RebateGroup failure : " + ex.getMessage());
        }
        }

        /**
        * 保存更新对象
        **/
        @RequestMapping(value = "update", method = RequestMethod.POST)
        public RestResult updateRebateGroup(@RequestBody RebateGroupBo rebateGroup) {
        try {
        RebateGroupVo dbRebateGroup = rebateGroupService.updateRebateGroup(rebateGroup);
        return RestUtils.successWhenNotNull(dbRebateGroup);
        } catch (BusinessException ex) {
        LOG.error("update RebateGroup failure : rebateGroup", rebateGroup, ex);
        return RestUtils.error("update RebateGroup failure : " + ex.getMessage());
        }
        }

        /**
        * 删除对象
        **/
        @RequestMapping(value = "removeById", method = RequestMethod.GET)
        public RestResult removeRebateGroupById(@RequestParam("id") int rebateGroupId) {
        try {
        rebateGroupService.removeRebateGroupById(rebateGroupId);
        return RestUtils.success(true);
        } catch (Exception ex) {
        LOG.error("remove RebateGroup failure : id=rebateGroupId", ex);
        return RestUtils.error("remove RebateGroup failure : " + ex.getMessage());
        }
        }
}