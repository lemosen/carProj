/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.activity.controller;

import com.yi.core.activity.domain.bo.SeckillRecordBo;
import com.yi.core.activity.domain.entity.SeckillRecord;
import com.yi.core.activity.domain.vo.SeckillRecordListVo;
import com.yi.core.activity.domain.vo.SeckillRecordVo;


import com.yi.core.activity.service.ISeckillRecordService;
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
@RequestMapping(value = "/seckillRecord")
public class SeckillRecordController {

    private final Logger LOG = LoggerFactory.getLogger(SeckillRecordController.class);


    @Resource
    private ISeckillRecordService seckillRecordService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<SeckillRecordListVo> querySeckillRecord(@RequestBody Pagination<SeckillRecord> query) {
    Page<SeckillRecordListVo> page = seckillRecordService.queryListVo(query);
        return page;
        }

        /**
        * 查看对象
        **/
        @RequestMapping(value = "getById", method = RequestMethod.GET)
        public RestResult viewSeckillRecord(@RequestParam("id") int seckillRecordId) {
        try {
        SeckillRecordVo seckillRecord = seckillRecordService.getSeckillRecordVoById(seckillRecordId);
        return RestUtils.successWhenNotNull(seckillRecord);
        } catch (BusinessException ex) {
        LOG.error("get SeckillRecord failure : id=seckillRecordId", ex);
        return RestUtils.error("get SeckillRecord failure : " + ex.getMessage());
        }
        }


        /**
        * 保存新增对象
        **/
        @RequestMapping(value = "add", method = RequestMethod.POST)
        public RestResult addSeckillRecord(@RequestBody SeckillRecordBo seckillRecord) {
        try {
        SeckillRecordVo dbSeckillRecord = seckillRecordService.addSeckillRecord(seckillRecord);
        return RestUtils.successWhenNotNull(dbSeckillRecord);
        } catch (BusinessException ex) {
        LOG.error("add SeckillRecord failure : seckillRecord", seckillRecord, ex);
        return RestUtils.error("add SeckillRecord failure : " + ex.getMessage());
        }
        }

        /**
        * 保存更新对象
        **/
        @RequestMapping(value = "update", method = RequestMethod.POST)
        public RestResult updateSeckillRecord(@RequestBody SeckillRecordBo seckillRecord) {
        try {
        SeckillRecordVo dbSeckillRecord = seckillRecordService.updateSeckillRecord(seckillRecord);
        return RestUtils.successWhenNotNull(dbSeckillRecord);
        } catch (BusinessException ex) {
        LOG.error("update SeckillRecord failure : seckillRecord", seckillRecord, ex);
        return RestUtils.error("update SeckillRecord failure : " + ex.getMessage());
        }
        }

        /**
        * 删除对象
        **/
        @RequestMapping(value = "removeById", method = RequestMethod.GET)
        public RestResult removeSeckillRecordById(@RequestParam("id") int seckillRecordId) {
        try {
        seckillRecordService.removeSeckillRecordById(seckillRecordId);
        return RestUtils.success(true);
        } catch (Exception ex) {
        LOG.error("remove SeckillRecord failure : id=seckillRecordId", ex);
        return RestUtils.error("remove SeckillRecord failure : " + ex.getMessage());
        }
        }
}