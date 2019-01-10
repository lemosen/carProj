/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.basic.controller;

import javax.annotation.Resource;

import com.yi.core.basic.domain.vo.SmsRecordListVo;
import com.yi.core.basic.domain.vo.SmsRecordVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.basic.domain.entity.SmsRecord;
import com.yi.core.basic.service.ISmsRecordService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/smsRecord")
public class SmsRecordController {

    private final Logger LOG = LoggerFactory.getLogger(SmsRecordController.class);


    @Resource
    private ISmsRecordService smsRecordService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<SmsRecordListVo> querySmsRecord(@RequestBody Pagination<SmsRecord> query) {
    Page<SmsRecordListVo> page = smsRecordService.queryListVo(query);
        return page;
        }

        /**
        * 查看对象
        **/
        @RequestMapping(value = "getById", method = RequestMethod.GET)
        public RestResult viewSmsRecord(@RequestParam("id") int smsRecordId) {
        try {
        SmsRecordVo smsRecord = smsRecordService.getSmsRecordById(smsRecordId);
        return RestUtils.successWhenNotNull(smsRecord);
        } catch (BusinessException ex) {
        LOG.error("get SmsRecord failure : id=smsRecordId", ex);
        return RestUtils.error("get SmsRecord failure : " + ex.getMessage());
        }
        }


        /**
        * 保存新增对象
        **/
        @RequestMapping(value = "add", method = RequestMethod.POST)
        public RestResult addSmsRecord(@RequestBody SmsRecord smsRecord) {
        try {
        SmsRecordVo dbSmsRecord = smsRecordService.addSmsRecord(smsRecord);
        return RestUtils.successWhenNotNull(dbSmsRecord);
        } catch (BusinessException ex) {
        LOG.error("add SmsRecord failure : smsRecord", smsRecord, ex);
        return RestUtils.error("add SmsRecord failure : " + ex.getMessage());
        }
        }

        /**
        * 保存更新对象
        **/
        @RequestMapping(value = "update", method = RequestMethod.POST)
        public RestResult updateSmsRecord(@RequestBody SmsRecord smsRecord) {
        try {
        SmsRecordVo dbSmsRecord = smsRecordService.updateSmsRecord(smsRecord);
        return RestUtils.successWhenNotNull(dbSmsRecord);
        } catch (BusinessException ex) {
        LOG.error("update SmsRecord failure : smsRecord", smsRecord, ex);
        return RestUtils.error("update SmsRecord failure : " + ex.getMessage());
        }
        }

        /**
        * 删除对象
        **/
        @RequestMapping(value = "removeById", method = RequestMethod.GET)
        public RestResult removeSmsRecordById(@RequestParam("id") int smsRecordId) {
        try {
        smsRecordService.removeSmsRecordById(smsRecordId);
        return RestUtils.success(true);
        } catch (Exception ex) {
        LOG.error("remove SmsRecord failure : id=smsRecordId", ex);
        return RestUtils.error("remove SmsRecord failure : " + ex.getMessage());
        }
        }
}