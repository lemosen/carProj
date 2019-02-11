/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.supplier.web.supplier.controller;

import com.yi.core.supplier.domain.bo.SupplierAccountRecordBo;
import com.yi.core.supplier.domain.entity.SupplierAccountRecord;
import com.yi.core.supplier.domain.vo.SupplierAccountRecordListVo;
import com.yi.core.supplier.domain.vo.SupplierAccountRecordVo;
import com.yi.core.supplier.service.ISupplierAccountRecordService;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yihz.common.exception.BusinessException;
import javax.annotation.Resource;


/**
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/supplierAccountRecord")
public class SupplierAccountRecordController{

    private final Logger LOG = LoggerFactory.getLogger(SupplierAccountRecordController.class);

    @Resource
    private ISupplierAccountRecordService supplierAccountRecordService;

    /**
    * 分页查询
    */
    @RequestMapping(value="query",method = RequestMethod.POST)
    public Page<SupplierAccountRecordListVo> querySupplierAccountRecord(@RequestBody Pagination<SupplierAccountRecord> query ) {
        Page<SupplierAccountRecordListVo> page = supplierAccountRecordService.queryListVo(query);
        return page;
    }

    /**
    * 保存新增对象
    **/
    @RequestMapping(value="add",method = RequestMethod.POST)
    public RestResult addSupplierAccountRecord(@RequestBody SupplierAccountRecordBo supplierAccountRecordBo) {
        try {
            SupplierAccountRecordListVo supplierAccountRecordListVo = supplierAccountRecordService.addSupplierAccountRecord(supplierAccountRecordBo);
            return RestUtils.successWhenNotNull(supplierAccountRecordListVo);
        } catch (BusinessException ex) {
            LOG.error("add SupplierAccountRecord failure : supplierAccountRecordBo", supplierAccountRecordBo, ex);
            return RestUtils.error("add SupplierAccountRecord failure : " + ex.getMessage());
        }
    }

    /**
    * 保存更新对象
    **/
    @RequestMapping(value="update",method = RequestMethod.POST)
    public RestResult updateSupplierAccountRecord(@RequestBody SupplierAccountRecordBo supplierAccountRecordBo)  {
        try {
            SupplierAccountRecordListVo supplierAccountRecordListVo = supplierAccountRecordService.updateSupplierAccountRecord(supplierAccountRecordBo);
            return RestUtils.successWhenNotNull(supplierAccountRecordListVo);
        } catch (BusinessException ex) {
            LOG.error("update SupplierAccountRecord failure : supplierAccountRecordBo", supplierAccountRecordBo, ex);
            return RestUtils.error("update SupplierAccountRecord failure : " + ex.getMessage());
        }
    }

    /**
    *删除对象
    **/
    @RequestMapping(value="removeById",method = RequestMethod.GET)
    public RestResult removeSupplierAccountRecordById(@RequestParam("id") int supplierAccountRecordId) {
        try{
            supplierAccountRecordService.removeSupplierAccountRecordById(supplierAccountRecordId);
            return RestUtils.success(true);
        }catch (Exception ex){
            LOG.error("remove SupplierAccountRecord failure : id=supplierAccountRecordId", ex);
            return RestUtils.error("remove SupplierAccountRecord failure : " + ex.getMessage());
        }
    }

    /**
    * 获取编辑对象
    **/
    @RequestMapping(value="getBoById", method = RequestMethod.GET)
    public RestResult getSupplierAccountRecordBoById(@RequestParam("id") int supplierAccountRecordId)  {
        try {
            SupplierAccountRecordBo supplierAccountRecordBo = supplierAccountRecordService.getSupplierAccountRecordBoById(supplierAccountRecordId);
            return RestUtils.successWhenNotNull(supplierAccountRecordBo);
        } catch (BusinessException ex) {
            LOG.error("get SupplierAccountRecord failure : id=supplierAccountRecordId", ex);
            return RestUtils.error("get SupplierAccountRecord failure : " + ex.getMessage());
        }
    }

    /**
    * 查看对象详情
    **/
    @RequestMapping(value="getVoById", method = RequestMethod.GET)
    public RestResult getSupplierAccountRecordVoById(@RequestParam("id") int supplierAccountRecordId)  {
        try {
            SupplierAccountRecordVo supplierAccountRecordVo = supplierAccountRecordService.getSupplierAccountRecordVoById(supplierAccountRecordId);
            return RestUtils.successWhenNotNull(supplierAccountRecordVo);
        } catch (BusinessException ex) {
            LOG.error("get SupplierAccountRecord failure : id=supplierAccountRecordId", ex);
            return RestUtils.error("get SupplierAccountRecord failure : " + ex.getMessage());
        }
    }
 }