/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.supplier.web.order.controller;


import com.yi.core.order.domain.bo.LogisticsAddressBo;
import com.yi.core.order.domain.entity.LogisticsAddress;
import com.yi.core.order.domain.vo.LogisticsAddressListVo;
import com.yi.core.order.domain.vo.LogisticsAddressVo;
import com.yi.core.order.service.ILogisticsAddressService;
import com.yi.core.supplier.domain.bo.SupplierBo;
import com.yi.core.supplier.service.ISupplierService;
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
@RequestMapping(value = "/logisticsAddress")
public class LogisticsAddressController {

    private final Logger LOG = LoggerFactory.getLogger(LogisticsAddressController.class);


    @Resource
    private ILogisticsAddressService logisticsAddressService;

    @Resource
    private ISupplierService supplierService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<LogisticsAddressListVo> queryLogisticsAddress(@RequestBody Pagination<LogisticsAddress> query,
                                                              HttpServletRequest request) {
        SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
        query.getParams().put("supplier.id", supplierToken.getId());
        Page<LogisticsAddressListVo> page = logisticsAddressService.queryListVo(query);
        return page;
    }

    /**
     * 查看对象
     **/
    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public RestResult viewLogisticsAddress(@RequestParam("id") int logisticsAddressId) {
        try {
            LogisticsAddressVo logisticsAddress = logisticsAddressService.getLogisticsAddressVoById(logisticsAddressId);
            return RestUtils.successWhenNotNull(logisticsAddress);
        } catch (BusinessException ex) {
            LOG.error("get LogisticsAddress failure : id=logisticsAddressId", ex);
            return RestUtils.error("get LogisticsAddress failure : " + ex.getMessage());
        }
    }


    /**
     * 保存新增对象
     **/
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public RestResult addLogisticsAddress(@RequestBody LogisticsAddressBo logisticsAddress, HttpServletRequest request) {
        try {
            SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
            SupplierBo supplierBo = supplierService.getSupplierBoById(supplierToken.getId());
            logisticsAddress.setSupplier(supplierBo);
            LogisticsAddressListVo dbLogisticsAddress = logisticsAddressService.addLogisticsAddress(logisticsAddress);
            return RestUtils.successWhenNotNull(dbLogisticsAddress);
        } catch (BusinessException ex) {
            LOG.error("add LogisticsAddress failure : logisticsAddress", logisticsAddress, ex);
            return RestUtils.error("add LogisticsAddress failure : " + ex.getMessage());
        }
    }

    /**
     * 保存更新对象
     **/
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public RestResult updateLogisticsAddress(@RequestBody LogisticsAddressBo logisticsAddress) {
        try {
            LogisticsAddressListVo dbLogisticsAddress = logisticsAddressService.updateLogisticsAddress
                    (logisticsAddress);
            return RestUtils.successWhenNotNull(dbLogisticsAddress);
        } catch (BusinessException ex) {
            LOG.error("update LogisticsAddress failure : logisticsAddress", logisticsAddress, ex);
            return RestUtils.error("update LogisticsAddress failure : " + ex.getMessage());
        }
    }

    /**
     * 删除对象
     **/
    @RequestMapping(value = "removeById", method = RequestMethod.GET)
    public RestResult removeLogisticsAddressById(@RequestParam("id") int logisticsAddressId) {
        try {
            logisticsAddressService.removeLogisticsAddressById(logisticsAddressId);
            return RestUtils.success(true);
        } catch (Exception ex) {
            LOG.error("remove LogisticsAddress failure : id=logisticsAddressId", ex);
            return RestUtils.error("remove LogisticsAddress failure : " + ex.getMessage());
        }
    }
}