/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.supplier.web.finance.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.finance.domain.entity.PlatformSaleStat;
import com.yi.core.finance.domain.vo.PlatformSaleStatListVo;
import com.yi.core.finance.domain.vo.PlatformSaleStatVo;
import com.yi.core.finance.service.IPlatformSaleStatService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/platformSaleStat")
public class PlatformSaleStatController {

    private final Logger LOG = LoggerFactory.getLogger(PlatformSaleStatController.class);

    @Resource
    private IPlatformSaleStatService platformSaleStatService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<PlatformSaleStatListVo> queryPlatformSaleStat(@RequestBody Pagination<PlatformSaleStat> query) {
        Page<PlatformSaleStatListVo> page = platformSaleStatService.queryListVo(query);
        return page;
    }

    /**
     * 查看对象
     **/
    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public RestResult viewPlatformSaleStat(@RequestParam("id") int platformSaleStatId) {
        try {
            PlatformSaleStatVo platformSaleStat = platformSaleStatService.getPlatformSaleStatVoById(platformSaleStatId);
            return RestUtils.successWhenNotNull(platformSaleStat);
        } catch (BusinessException ex) {
            LOG.error("get PlatformSaleStat failure : id=platformSaleStatId", ex);
            return RestUtils.error("get PlatformSaleStat failure : " + ex.getMessage());
        }
    }

    /**
     * 保存新增对象
     **/
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public RestResult addPlatformSaleStat(@RequestBody PlatformSaleStat platformSaleStat) {
        try {
            PlatformSaleStatVo dbPlatformSaleStat = platformSaleStatService.addPlatformSaleStat(platformSaleStat);
            return RestUtils.successWhenNotNull(dbPlatformSaleStat);
        } catch (BusinessException ex) {
            LOG.error("add PlatformSaleStat failure : platformSaleStat", platformSaleStat, ex);
            return RestUtils.error("add PlatformSaleStat failure : " + ex.getMessage());
        }
    }

    /**
     * 保存更新对象
     **/
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public RestResult updatePlatformSaleStat(@RequestBody PlatformSaleStat platformSaleStat) {
        try {
            PlatformSaleStatVo dbPlatformSaleStat = platformSaleStatService.updatePlatformSaleStat(platformSaleStat);
            return RestUtils.successWhenNotNull(dbPlatformSaleStat);
        } catch (BusinessException ex) {
            LOG.error("update PlatformSaleStat failure : platformSaleStat", platformSaleStat, ex);
            return RestUtils.error("update PlatformSaleStat failure : " + ex.getMessage());
        }
    }

    /**
     * 删除对象
     **/
    @RequestMapping(value = "removeById", method = RequestMethod.GET)
    public RestResult removePlatformSaleStatById(@RequestParam("id") int platformSaleStatId) {
        try {
            platformSaleStatService.removePlatformSaleStatById(platformSaleStatId);
            return RestUtils.success(true);
        } catch (Exception ex) {
            LOG.error("remove PlatformSaleStat failure : id=platformSaleStatId", ex);
            return RestUtils.error("remove PlatformSaleStat failure : " + ex.getMessage());
        }
    }

}