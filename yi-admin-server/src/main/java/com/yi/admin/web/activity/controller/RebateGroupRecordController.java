/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.activity.controller;

import com.yi.core.activity.domain.bo.RebateGroupRecordBo;
import com.yi.core.activity.domain.entity.RebateGroupRecord;
import com.yi.core.activity.domain.vo.RebateGroupRecordListVo;
import com.yi.core.activity.domain.vo.RebateGroupRecordVo;


import com.yi.core.activity.service.IRebateGroupRecordService;
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
@RequestMapping(value = "/rebateGroupRecord")
public class RebateGroupRecordController {

    private final Logger LOG = LoggerFactory.getLogger(RebateGroupRecordController.class);


    @Resource
    private IRebateGroupRecordService rebateGroupRecordService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<RebateGroupRecordListVo> queryRebateGroupRecord(@RequestBody Pagination<RebateGroupRecord> query) {
    Page<RebateGroupRecordListVo> page = rebateGroupRecordService.queryListVo(query);
        return page;
        }

        /**
        * 查看对象
        **/
        @RequestMapping(value = "getById", method = RequestMethod.GET)
        public RestResult viewRebateGroupRecord(@RequestParam("id") int rebateGroupRecordId) {
        try {
        RebateGroupRecordVo rebateGroupRecord = rebateGroupRecordService.getRebateGroupRecordVoById(rebateGroupRecordId);
        return RestUtils.successWhenNotNull(rebateGroupRecord);
        } catch (BusinessException ex) {
        LOG.error("get RebateGroupRecord failure : id=rebateGroupRecordId", ex);
        return RestUtils.error("get RebateGroupRecord failure : " + ex.getMessage());
        }
        }


        /**
        * 保存新增对象
        **/
        @RequestMapping(value = "add", method = RequestMethod.POST)
        public RestResult addRebateGroupRecord(@RequestBody RebateGroupRecordBo rebateGroupRecord) {
        try {
        RebateGroupRecordVo dbRebateGroupRecord = rebateGroupRecordService.addRebateGroupRecord(rebateGroupRecord);
        return RestUtils.successWhenNotNull(dbRebateGroupRecord);
        } catch (BusinessException ex) {
        LOG.error("add RebateGroupRecord failure : rebateGroupRecord", rebateGroupRecord, ex);
        return RestUtils.error("add RebateGroupRecord failure : " + ex.getMessage());
        }
        }

        /**
        * 保存更新对象
        **/
        @RequestMapping(value = "update", method = RequestMethod.POST)
        public RestResult updateRebateGroupRecord(@RequestBody RebateGroupRecordBo rebateGroupRecord) {
        try {
        RebateGroupRecordVo dbRebateGroupRecord = rebateGroupRecordService.updateRebateGroupRecord(rebateGroupRecord);
        return RestUtils.successWhenNotNull(dbRebateGroupRecord);
        } catch (BusinessException ex) {
        LOG.error("update RebateGroupRecord failure : rebateGroupRecord", rebateGroupRecord, ex);
        return RestUtils.error("update RebateGroupRecord failure : " + ex.getMessage());
        }
        }

        /**
        * 删除对象
        **/
        @RequestMapping(value = "removeById", method = RequestMethod.GET)
        public RestResult removeRebateGroupRecordById(@RequestParam("id") int rebateGroupRecordId) {
        try {
        rebateGroupRecordService.removeRebateGroupRecordById(rebateGroupRecordId);
        return RestUtils.success(true);
        } catch (Exception ex) {
        LOG.error("remove RebateGroupRecord failure : id=rebateGroupRecordId", ex);
        return RestUtils.error("remove RebateGroupRecord failure : " + ex.getMessage());
        }
        }
}