/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.activity.controller;

import com.yi.core.activity.domain.bo.NationalGroupBo;
import com.yi.core.activity.domain.entity.NationalGroup;
import com.yi.core.activity.domain.vo.NationalGroupListVo;
import com.yi.core.activity.domain.vo.NationalGroupVo;

import com.yi.core.activity.service.INationalGroupService;
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
@RequestMapping(value = "/nationalGroup")
public class NationalGroupController {

    private final Logger LOG = LoggerFactory.getLogger(NationalGroupController.class);


    @Resource
    private INationalGroupService nationalGroupService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<NationalGroupListVo> queryNationalGroup(@RequestBody Pagination<NationalGroup> query) {
    Page<NationalGroupListVo> page = nationalGroupService.queryListVo(query);
        return page;
        }

        /**
        * 查看对象
        **/
        @RequestMapping(value = "getById", method = RequestMethod.GET)
        public RestResult viewNationalGroup(@RequestParam("id") int nationalGroupId) {
        try {
        NationalGroupVo nationalGroup = nationalGroupService.getNationalGroupVoById(nationalGroupId);
        return RestUtils.successWhenNotNull(nationalGroup);
        } catch (BusinessException ex) {
        LOG.error("get NationalGroup failure : id=nationalGroupId", ex);
        return RestUtils.error("get NationalGroup failure : " + ex.getMessage());
        }
        }


        /**
        * 保存新增对象
        **/
        @RequestMapping(value = "add", method = RequestMethod.POST)
        public RestResult addNationalGroup(@RequestBody NationalGroupBo nationalGroup) {
        try {
        NationalGroupVo dbNationalGroup = nationalGroupService.addNationalGroup(nationalGroup);
        return RestUtils.successWhenNotNull(dbNationalGroup);
        } catch (BusinessException ex) {
        LOG.error("add NationalGroup failure : nationalGroup", nationalGroup, ex);
        return RestUtils.error("add NationalGroup failure : " + ex.getMessage());
        }
        }

        /**
        * 保存更新对象
        **/
        @RequestMapping(value = "update", method = RequestMethod.POST)
        public RestResult updateNationalGroup(@RequestBody NationalGroupBo nationalGroup) {
        try {
        NationalGroupVo dbNationalGroup = nationalGroupService.updateNationalGroup(nationalGroup);
        return RestUtils.successWhenNotNull(dbNationalGroup);
        } catch (BusinessException ex) {
        LOG.error("update NationalGroup failure : nationalGroup", nationalGroup, ex);
        return RestUtils.error("update NationalGroup failure : " + ex.getMessage());
        }
        }

        /**
        * 删除对象
        **/
        @RequestMapping(value = "removeById", method = RequestMethod.GET)
        public RestResult removeNationalGroupById(@RequestParam("id") int nationalGroupId) {
        try {
        nationalGroupService.removeNationalGroupById(nationalGroupId);
        return RestUtils.success(true);
        } catch (Exception ex) {
        LOG.error("remove NationalGroup failure : id=nationalGroupId", ex);
        return RestUtils.error("remove NationalGroup failure : " + ex.getMessage());
        }
        }
}