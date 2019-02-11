/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.activity.controller;

import com.yi.core.activity.domain.bo.SeckillBo;
import com.yi.core.activity.domain.entity.Seckill;
import com.yi.core.activity.domain.vo.SeckillListVo;
import com.yi.core.activity.domain.vo.SeckillVo;


import com.yi.core.activity.service.ISeckillService;
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
@RequestMapping(value = "/seckill")
public class SeckillController {

    private final Logger LOG = LoggerFactory.getLogger(SeckillController.class);


    @Resource
    private ISeckillService seckillService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<SeckillListVo> querySeckill(@RequestBody Pagination<Seckill> query) {
    Page<SeckillListVo> page = seckillService.queryListVo(query);
        return page;
        }

        /**
        * 查看对象
        **/
        @RequestMapping(value = "getById", method = RequestMethod.GET)
        public RestResult viewSeckill(@RequestParam("id") int seckillId) {
        try {
        SeckillVo seckill = seckillService.getSeckillVoById(seckillId);
        return RestUtils.successWhenNotNull(seckill);
        } catch (BusinessException ex) {
        LOG.error("get Seckill failure : id=seckillId", ex);
        return RestUtils.error("get Seckill failure : " + ex.getMessage());
        }
        }


        /**
        * 保存新增对象
        **/
        @RequestMapping(value = "add", method = RequestMethod.POST)
        public RestResult addSeckill(@RequestBody SeckillBo seckill) {
        try {
        SeckillVo dbSeckill = seckillService.addSeckill(seckill);
        return RestUtils.successWhenNotNull(dbSeckill);
        } catch (BusinessException ex) {
        LOG.error("add Seckill failure : seckill", seckill, ex);
        return RestUtils.error("add Seckill failure : " + ex.getMessage());
        }
        }

        /**
        * 保存更新对象
        **/
        @RequestMapping(value = "update", method = RequestMethod.POST)
        public RestResult updateSeckill(@RequestBody SeckillBo seckill) {
        try {
        SeckillVo dbSeckill = seckillService.updateSeckill(seckill);
        return RestUtils.successWhenNotNull(dbSeckill);
        } catch (BusinessException ex) {
        LOG.error("update Seckill failure : seckill", seckill, ex);
        return RestUtils.error("update Seckill failure : " + ex.getMessage());
        }
        }

        /**
        * 删除对象
        **/
        @RequestMapping(value = "removeById", method = RequestMethod.GET)
        public RestResult removeSeckillById(@RequestParam("id") int seckillId) {
        try {
        seckillService.removeSeckillById(seckillId);
        return RestUtils.success(true);
        } catch (Exception ex) {
        LOG.error("remove Seckill failure : id=seckillId", ex);
        return RestUtils.error("remove Seckill failure : " + ex.getMessage());
        }
        }
}