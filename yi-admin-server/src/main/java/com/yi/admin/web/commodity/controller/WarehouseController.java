/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.commodity.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.commodity.domain.bo.WarehouseBo;
import com.yi.core.commodity.domain.entity.Warehouse;
import com.yi.core.commodity.domain.vo.WarehouseListVo;
import com.yi.core.commodity.domain.vo.WarehouseVo;
import com.yi.core.commodity.service.IWarehouseService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 仓库
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/warehouse")
public class WarehouseController {

    private final Logger LOG = LoggerFactory.getLogger(WarehouseController.class);

    @Resource
    private IWarehouseService warehouseService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<WarehouseListVo> queryWarehouse(@RequestBody Pagination<Warehouse> query) {
        Page<WarehouseListVo> page = warehouseService.queryListVo(query);
        return page;
    }

    /**
     * 保存新增对象
     **/
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public RestResult addWarehouse(@RequestBody WarehouseBo warehouseBo) {
        try {
            WarehouseVo warehouseVo = warehouseService.addWarehouse(warehouseBo);
            return RestUtils.successWhenNotNull(warehouseVo);
        } catch (BusinessException ex) {
            LOG.error("add Warehouse failure : warehouseBo", warehouseBo, ex);
            return RestUtils.error(ex.getMessage());
        }
    }

    /**
     * 保存更新对象
     **/
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public RestResult updateWarehouse(@RequestBody WarehouseBo warehouseBo) {
        try {
            WarehouseVo warehouseVo = warehouseService.updateWarehouse(warehouseBo);
            return RestUtils.successWhenNotNull(warehouseVo);
        } catch (BusinessException ex) {
            LOG.error("update Warehouse failure : warehouseBo", warehouseBo, ex);
            return RestUtils.error("update Warehouse failure : " + ex.getMessage());
        }
    }

    /**
     * 删除对象
     **/
    @RequestMapping(value = "removeById", method = RequestMethod.GET)
    public RestResult removeWarehouseById(@RequestParam("id") int warehouseId) {
        try {
            warehouseService.removeWarehouseById(warehouseId);
            return RestUtils.success(true);
        } catch (Exception ex) {
            LOG.error("remove Warehouse failure : id=warehouseId", ex);
            return RestUtils.error("remove Warehouse failure : " + ex.getMessage());
        }
    }

    /**
     * 获取编辑对象
     **/
    @RequestMapping(value = "getBoById", method = RequestMethod.GET)
    public RestResult getWarehouseBoById(@RequestParam("id") int warehouseId) {
        try {
            WarehouseBo warehouseBo = warehouseService.getWarehouseBoById(warehouseId);
            return RestUtils.successWhenNotNull(warehouseBo);
        } catch (BusinessException ex) {
            LOG.error("get Warehouse failure : id=warehouseId", ex);
            return RestUtils.error("get Warehouse failure : " + ex.getMessage());
        }
    }

    /**
     * 查看对象详情
     **/
    @RequestMapping(value = "getVoById", method = RequestMethod.GET)
    public RestResult getWarehouseVoById(@RequestParam("id") int warehouseId) {
        try {
            WarehouseVo warehouseVo = warehouseService.getWarehouseVoById(warehouseId);
            return RestUtils.successWhenNotNull(warehouseVo);
        } catch (BusinessException ex) {
            LOG.error("get Warehouse failure : id=warehouseId", ex);
            return RestUtils.error("get Warehouse failure : " + ex.getMessage());
        }
    }
}