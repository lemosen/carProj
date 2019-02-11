/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.supplier.web.supplier.controller;

import com.yi.core.supplier.domain.bo.SupplierAccountBo;
import com.yi.core.supplier.domain.entity.SupplierAccount;
import com.yi.core.supplier.domain.vo.SupplierAccountListVo;
import com.yi.core.supplier.domain.vo.SupplierAccountVo;

import com.yi.core.supplier.service.ISupplierAccountService;
import com.yi.supplier.web.auth.jwt.JwtSupplierToken;
import com.yi.supplier.web.auth.jwt.SupplierToken;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/supplierAccount")
public class SupplierAccountController {

    private final Logger LOG = LoggerFactory.getLogger(SupplierAccountController.class);


    @Resource
    private ISupplierAccountService supplierAccountService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<SupplierAccountListVo> querySupplierAccount(@RequestBody Pagination<SupplierAccount> query) {
        Page<SupplierAccountListVo> page = supplierAccountService.queryListVo(query);
        return page;
    }

    /**
     * 查看对象
     **/
    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public RestResult viewSupplierAccount(@RequestParam("id") int supplierAccountId) {
        try {
            SupplierAccountVo supplierAccount = supplierAccountService.getSupplierAccountVoById(supplierAccountId);
            return RestUtils.successWhenNotNull(supplierAccount);
        } catch (BusinessException ex) {
            LOG.error("get SupplierAccount failure : id=supplierAccountId", ex);
            return RestUtils.error("get SupplierAccount failure : " + ex.getMessage());
        }
    }


    /**
     * 保存新增对象
     **/
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public RestResult addSupplierAccount(@RequestBody SupplierAccountBo supplierAccount) {
        try {
            SupplierAccountVo dbSupplierAccount = supplierAccountService.addSupplierAccount(supplierAccount);
            return RestUtils.successWhenNotNull(dbSupplierAccount);
        } catch (BusinessException ex) {
            LOG.error("add SupplierAccount failure : supplierAccount", supplierAccount, ex);
            return RestUtils.error("add SupplierAccount failure : " + ex.getMessage());
        }
    }

    /**
     * 保存更新对象
     **/
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public RestResult updateSupplierAccount(@RequestBody SupplierAccountBo supplierAccount) {
        try {
            SupplierAccountVo dbSupplierAccount = supplierAccountService.updateSupplierAccount(supplierAccount);
            return RestUtils.successWhenNotNull(dbSupplierAccount);
        } catch (BusinessException ex) {
            LOG.error("update SupplierAccount failure : supplierAccount", supplierAccount, ex);
            return RestUtils.error("update SupplierAccount failure : " + ex.getMessage());
        }
    }

    /**
     * 删除对象
     **/
    @RequestMapping(value = "removeById", method = RequestMethod.GET)
    public RestResult removeSupplierAccountById(@RequestParam("id") int supplierAccountId) {
        try {
            supplierAccountService.removeSupplierAccountById(supplierAccountId);
            return RestUtils.success(true);
        } catch (Exception ex) {
            LOG.error("remove SupplierAccount failure : id=supplierAccountId", ex);
            return RestUtils.error("remove SupplierAccount failure : " + ex.getMessage());
        }
    }

    /**
     * 查询该供应商的账户
     **/
    @RequestMapping(value = "getBySupplier", method = RequestMethod.GET)
    public RestResult getBySupplier(HttpServletRequest request) {
        try {
            SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
            return RestUtils.success(supplierAccountService.getBySupplier(supplierToken.getId()));
        } catch (Exception ex) {
            LOG.error("remove SupplierAccount failure : id=supplierAccountId", ex);
            return RestUtils.error("remove SupplierAccount failure : " + ex.getMessage());
        }
    }



}