/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.supplier.web.supplier.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.yi.core.supplier.domain.bo.SupplierBo;
import com.yi.supplier.web.auth.jwt.JwtSupplierToken;
import com.yi.supplier.web.auth.jwt.SupplierToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.supplier.domain.entity.Supplier;
import com.yi.core.supplier.domain.vo.SupplierListVo;
import com.yi.core.supplier.domain.vo.SupplierVo;
import com.yi.core.supplier.service.ISupplierService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 供应商
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/supplier")
public class SupplierController {

    private final Logger LOG = LoggerFactory.getLogger(SupplierController.class);

    @Resource
    private ISupplierService supplierService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<SupplierListVo> querySupplier(@RequestBody Pagination<Supplier> query) {
        Page<SupplierListVo> page = supplierService.queryListVo(query);
        return page;
    }


    /**
     * 查看对象
     **/
    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public RestResult viewSupplier(@RequestParam("id") int supplierId) {
        try {
            SupplierVo supplier = supplierService.getSupplierVoById(supplierId);
            return RestUtils.successWhenNotNull(supplier);
        } catch (BusinessException ex) {
            LOG.error("get Supplier failure : id=supplierId", ex);
            return RestUtils.error("get Supplier failure : " + ex.getMessage());
        }
    }

    /**
     * 保存新增对象
     **/
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public RestResult addSupplier(@RequestBody SupplierBo supplier) {
        try {
            SupplierVo supplierListVo = supplierService.addSupplier(supplier);
            return RestUtils.successWhenNotNull(supplierListVo);
        } catch (BusinessException ex) {
            LOG.error("add Supplier failure : supplier", supplier, ex);
            return RestUtils.error("add Supplier failure : " + ex.getMessage());
        }
    }

    /**
     * 保存更新对象
     **/
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public RestResult updateSupplier(@RequestBody SupplierBo supplier) {
        try {
            //Supplier dbSupplier = supplierService.updateSupplier(supplier);
            SupplierVo supplierListVo = supplierService.updateSupplier(supplier);
            return RestUtils.successWhenNotNull(supplierListVo);
        } catch (BusinessException ex) {
            LOG.error("update Supplier failure : supplier", supplier, ex);
            return RestUtils.error("update Supplier failure : " + ex.getMessage());
        }
    }

    /**
     * 删除对象
     **/
    @RequestMapping(value = "removeById", method = RequestMethod.GET)
    public RestResult removeSupplierById(@RequestParam("id") int supplierId) {
        try {
            supplierService.removeSupplierById(supplierId);
            return RestUtils.success(true);
        } catch (Exception ex) {
            LOG.error("remove Supplier failure : id=supplierId", ex);
            return RestUtils.error("remove Supplier failure : " + ex.getMessage());
        }
    }

    /**
     * 获取当前供应商信息
     */
    @RequestMapping(value = "getSupplier", method = RequestMethod.GET)
    public RestResult viewSupplier(HttpServletRequest request) {
        try {
            SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
            SupplierVo supplier = supplierService.getSupplierVoById(supplierToken.getId());
            return RestUtils.successWhenNotNull(supplier);
        } catch (BusinessException ex) {
            LOG.error("get Supplier failure : id=supplierId", ex);
            return RestUtils.error("get Supplier failure : " + ex.getMessage());
        }
    }

    // /**
    // * 供应商概况
    // *
    // * @return
    // */
    // @RequestMapping(value = "getList", method = RequestMethod.GET)
    // public RestResult getList() {
    // try {
    // int[] supplierSum = supplierService.getSupplierProfile();
    // return RestUtils.success(supplierSum);
    // } catch (Exception ex) {
    // LOG.error("供应商概况异常", ex);
    // return RestUtils.error("出错啦！请及时联系管理员: " + ex.getMessage());
    // }
    // }
    //
    // /**
    // * 平台数据统计
    // *
    // * @return
    // */
    // @RequestMapping(value = "getPlatformdata", method = RequestMethod.GET)
    // public RestResult getPlatformdata() {
    // try {
    // PlatformDataVo platformDataVo = supplierService.getPlatformdata();
    // return RestUtils.success(platformDataVo);
    // } catch (Exception ex) {
    // LOG.error("平台数据统计异常", ex);
    // return RestUtils.error("出错啦！请及时联系管理员: " + ex.getMessage());
    // }
    // }
    //
    // /**
    // * 供应商销售列表
    // */
    //
    // @RequestMapping(value = "salesList", method = RequestMethod.GET)
    // public RestResult getSalesList(@RequestParam("id") int id) {
    // try {
    //
    // return RestUtils.success(supplierService.getSalesList(id));
    // } catch (Exception ex) {
    // LOG.error("供应商销售列表异常啦", ex);
    // return RestUtils.error("出错啦！请及时联系管理员: " + ex.getMessage());
    // }
    // }


    @RequestMapping(value = "updateShelf", method = RequestMethod.GET)
    public RestResult updateShelf(@RequestParam("supplierId") int supplierId) {
        try {
            SupplierVo supplier = supplierService.banKai(supplierId);
            return RestUtils.success(supplier);
        } catch (Exception ex) {
            LOG.error("remove Recommend failure : id=recommendId", ex);
            return RestUtils.error("remove Recommend failure : " + ex.getMessage());
        }
    }
}