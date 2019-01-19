/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.activity.controller;

import com.yi.core.activity.domain.bo.NationalGroupRecordBo;
import com.yi.core.activity.domain.entity.NationalGroupRecord;
import com.yi.core.activity.domain.vo.NationalGroupRecordListVo;
import com.yi.core.activity.domain.vo.NationalGroupRecordVo;

import com.yi.core.activity.service.INationalGroupRecordService;
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
@RequestMapping(value = "/nationalGroupRecord")
public class NationalGroupRecordController {

    private final Logger LOG = LoggerFactory.getLogger(NationalGroupRecordController.class);


    @Resource
    private INationalGroupRecordService nationalGroupRecordService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<NationalGroupRecordListVo> queryNationalGroupRecord(@RequestBody Pagination<NationalGroupRecord> query) {
    Page<NationalGroupRecordListVo> page = nationalGroupRecordService.queryListVo(query);
        return page;
        }

        /**
        * 查看对象
        **/
        @RequestMapping(value = "getById", method = RequestMethod.GET)
        public RestResult viewNationalGroupRecord(@RequestParam("id") int nationalGroupRecordId) {
        try {
        NationalGroupRecordVo nationalGroupRecord = nationalGroupRecordService.getNationalGroupRecordVoById(nationalGroupRecordId);
        return RestUtils.successWhenNotNull(nationalGroupRecord);
        } catch (BusinessException ex) {
        LOG.error("get NationalGroupRecord failure : id=nationalGroupRecordId", ex);
        return RestUtils.error("get NationalGroupRecord failure : " + ex.getMessage());
        }
        }


        /**
        * 保存新增对象
        **/
        @RequestMapping(value = "add", method = RequestMethod.POST)
        public RestResult addNationalGroupRecord(@RequestBody NationalGroupRecordBo nationalGroupRecord) {
        try {
        NationalGroupRecordVo dbNationalGroupRecord = nationalGroupRecordService.addNationalGroupRecord(nationalGroupRecord);
        return RestUtils.successWhenNotNull(dbNationalGroupRecord);
        } catch (BusinessException ex) {
        LOG.error("add NationalGroupRecord failure : nationalGroupRecord", nationalGroupRecord, ex);
        return RestUtils.error("add NationalGroupRecord failure : " + ex.getMessage());
        }
        }

        /**
        * 保存更新对象
        **/
        @RequestMapping(value = "update", method = RequestMethod.POST)
        public RestResult updateNationalGroupRecord(@RequestBody NationalGroupRecordBo nationalGroupRecord) {
        try {
        NationalGroupRecordVo dbNationalGroupRecord = nationalGroupRecordService.updateNationalGroupRecord(nationalGroupRecord);
        return RestUtils.successWhenNotNull(dbNationalGroupRecord);
        } catch (BusinessException ex) {
        LOG.error("update NationalGroupRecord failure : nationalGroupRecord", nationalGroupRecord, ex);
        return RestUtils.error("update NationalGroupRecord failure : " + ex.getMessage());
        }
        }

        /**
        * 删除对象
        **/
        @RequestMapping(value = "removeById", method = RequestMethod.GET)
        public RestResult removeNationalGroupRecordById(@RequestParam("id") int nationalGroupRecordId) {
        try {
        nationalGroupRecordService.removeNationalGroupRecordById(nationalGroupRecordId);
        return RestUtils.success(true);
        } catch (Exception ex) {
        LOG.error("remove NationalGroupRecord failure : id=nationalGroupRecordId", ex);
        return RestUtils.error("remove NationalGroupRecord failure : " + ex.getMessage());
        }
        }
}