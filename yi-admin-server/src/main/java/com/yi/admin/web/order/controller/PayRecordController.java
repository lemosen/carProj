/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.order.controller;

import com.yi.core.order.domain.bo.PayRecordBo;
import com.yi.core.order.domain.entity.PayRecord;
import com.yi.core.order.domain.vo.PayRecordListVo;
import com.yi.core.order.domain.vo.PayRecordVo;

import com.yi.core.order.service.IPayRecordService;
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
@RestController
@RequestMapping(value = "/payRecord")
public class PayRecordController {

    private final Logger LOG = LoggerFactory.getLogger(PayRecordController.class);


    @Resource
    private IPayRecordService payRecordService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<PayRecord> queryPayRecord(@RequestBody Pagination<PayRecord> query) {
    Page<PayRecord> page = payRecordService.query(query);
        return page;
        }

        /**
        * 查看对象
        **/
        @RequestMapping(value = "getById", method = RequestMethod.GET)
        public RestResult viewPayRecord(@RequestParam("id") int payRecordId) {
        try {
        PayRecordVo payRecord = payRecordService.getPayRecordVoById(payRecordId);
        return RestUtils.successWhenNotNull(payRecord);
        } catch (BusinessException ex) {
        LOG.error("get PayRecord failure : id=payRecordId", ex);
        return RestUtils.error("get PayRecord failure : " + ex.getMessage());
        }
        }


        /**
        * 保存新增对象
        **/
        @RequestMapping(value = "add", method = RequestMethod.POST)
        public RestResult addPayRecord(@RequestBody PayRecordBo payRecord) {
        try {
        PayRecordListVo dbPayRecord = payRecordService.addPayRecord(payRecord);
        return RestUtils.successWhenNotNull(dbPayRecord);
        } catch (BusinessException ex) {
        LOG.error("add PayRecord failure : payRecord", payRecord, ex);
        return RestUtils.error("add PayRecord failure : " + ex.getMessage());
        }
        }

        /**
        * 保存更新对象
        **/
        @RequestMapping(value = "update", method = RequestMethod.POST)
        public RestResult updatePayRecord(@RequestBody PayRecordBo payRecord) {
        try {
        PayRecordVo dbPayRecord = payRecordService.updatePayRecord(payRecord);
        return RestUtils.successWhenNotNull(dbPayRecord);
        } catch (BusinessException ex) {
        LOG.error("update PayRecord failure : payRecord", payRecord, ex);
        return RestUtils.error("update PayRecord failure : " + ex.getMessage());
        }
        }

        /**
        * 删除对象
        **/
        @RequestMapping(value = "removeById", method = RequestMethod.GET)
        public RestResult removePayRecordById(@RequestParam("id") int payRecordId) {
        try {
        payRecordService.removePayRecordById(payRecordId);
        return RestUtils.success(true);
        } catch (Exception ex) {
        LOG.error("remove PayRecord failure : id=payRecordId", ex);
        return RestUtils.error("remove PayRecord failure : " + ex.getMessage());
        }
        }
}