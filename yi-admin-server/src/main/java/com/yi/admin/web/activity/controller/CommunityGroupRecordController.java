/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.activity.controller;

import com.yi.core.activity.domain.bo.CommunityGroupRecordBo;
import com.yi.core.activity.domain.entity.CommunityGroupRecord;
import com.yi.core.activity.domain.vo.CommunityGroupRecordListVo;
import com.yi.core.activity.domain.vo.CommunityGroupRecordVo;


import com.yi.core.activity.service.ICommunityGroupRecordService;
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
@RequestMapping(value = "/communityGroupRecord")
public class CommunityGroupRecordController {

    private final Logger LOG = LoggerFactory.getLogger(CommunityGroupRecordController.class);


    @Resource
    private ICommunityGroupRecordService communityGroupRecordService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<CommunityGroupRecordListVo> queryCommunityGroupRecord(@RequestBody Pagination<CommunityGroupRecord> query) {
    Page<CommunityGroupRecordListVo> page = communityGroupRecordService.queryListVo(query);
        return page;
        }

        /**
        * 查看对象
        **/
        @RequestMapping(value = "getById", method = RequestMethod.GET)
        public RestResult viewCommunityGroupRecord(@RequestParam("id") int communityGroupRecordId) {
        try {
        CommunityGroupRecordVo communityGroupRecord = communityGroupRecordService.getCommunityGroupRecordVoById(communityGroupRecordId);
        return RestUtils.successWhenNotNull(communityGroupRecord);
        } catch (BusinessException ex) {
        LOG.error("get CommunityGroupRecord failure : id=communityGroupRecordId", ex);
        return RestUtils.error("get CommunityGroupRecord failure : " + ex.getMessage());
        }
        }


        /**
        * 保存新增对象
        **/
        @RequestMapping(value = "add", method = RequestMethod.POST)
        public RestResult addCommunityGroupRecord(@RequestBody CommunityGroupRecordBo communityGroupRecord) {
        try {
        CommunityGroupRecordVo dbCommunityGroupRecord = communityGroupRecordService.addCommunityGroupRecord(communityGroupRecord);
        return RestUtils.successWhenNotNull(dbCommunityGroupRecord);
        } catch (BusinessException ex) {
        LOG.error("add CommunityGroupRecord failure : communityGroupRecord", communityGroupRecord, ex);
        return RestUtils.error("add CommunityGroupRecord failure : " + ex.getMessage());
        }
        }

        /**
        * 保存更新对象
        **/
        @RequestMapping(value = "update", method = RequestMethod.POST)
        public RestResult updateCommunityGroupRecord(@RequestBody CommunityGroupRecordBo communityGroupRecord) {
        try {
        CommunityGroupRecordVo dbCommunityGroupRecord = communityGroupRecordService.updateCommunityGroupRecord(communityGroupRecord);
        return RestUtils.successWhenNotNull(dbCommunityGroupRecord);
        } catch (BusinessException ex) {
        LOG.error("update CommunityGroupRecord failure : communityGroupRecord", communityGroupRecord, ex);
        return RestUtils.error("update CommunityGroupRecord failure : " + ex.getMessage());
        }
        }

        /**
        * 删除对象
        **/
        @RequestMapping(value = "removeById", method = RequestMethod.GET)
        public RestResult removeCommunityGroupRecordById(@RequestParam("id") int communityGroupRecordId) {
        try {
        communityGroupRecordService.removeCommunityGroupRecordById(communityGroupRecordId);
        return RestUtils.success(true);
        } catch (Exception ex) {
        LOG.error("remove CommunityGroupRecord failure : id=communityGroupRecordId", ex);
        return RestUtils.error("remove CommunityGroupRecord failure : " + ex.getMessage());
        }
        }
}