/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.basic.controller;

import com.yi.core.basic.domain.bo.IntegralTaskBo;
import com.yi.core.basic.domain.entity.IntegralTask;
import com.yi.core.basic.domain.vo.IntegralTaskListVo;
import com.yi.core.basic.domain.vo.IntegralTaskVo;
import com.yi.core.basic.service.IIntegralTaskService;
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
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/integralTask")
public class IntegralTaskController {

    private final Logger LOG = LoggerFactory.getLogger(IntegralTaskController.class);


    @Resource
    private IIntegralTaskService integralTaskService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<IntegralTaskListVo> queryIntegralTask(@RequestBody Pagination<IntegralTask> query) {
        Page<IntegralTaskListVo> page = integralTaskService.queryListVo(query);
        return page;
    }

    /**
     * 查看对象
     **/
    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public RestResult viewIntegralTask(@RequestParam("id") int integralTaskId) {
        try {
            IntegralTaskVo integralTask = integralTaskService.getIntegralTaskVoById(integralTaskId);
            return RestUtils.successWhenNotNull(integralTask);
        } catch (BusinessException ex) {
            LOG.error("get IntegralTask failure : id=integralTaskId", ex);
            return RestUtils.error("get IntegralTask failure : " + ex.getMessage());
        }
    }


    /**
     * 保存新增对象
     **/
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public RestResult addIntegralTask(@RequestBody IntegralTaskBo integralTask) {
        try {
            IntegralTaskVo dbIntegralTask = integralTaskService.addIntegralTask(integralTask);
            return RestUtils.successWhenNotNull(dbIntegralTask);
        } catch (BusinessException ex) {
            LOG.error("add IntegralTask failure : integralTask", integralTask, ex);
            return RestUtils.error("add IntegralTask failure : " + ex.getMessage());
        }
    }

    /**
     * 保存更新对象
     **/
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public RestResult updateIntegralTask(@RequestBody IntegralTaskBo integralTask) {
        try {
            IntegralTaskVo dbIntegralTask = integralTaskService.updateIntegralTask(integralTask);
            return RestUtils.successWhenNotNull(dbIntegralTask);
        } catch (BusinessException ex) {
            LOG.error("update IntegralTask failure : integralTask", integralTask, ex);
            return RestUtils.error("update IntegralTask failure : " + ex.getMessage());
        }
    }

    /**
     * 删除对象
     **/
    @RequestMapping(value = "removeById", method = RequestMethod.GET)
    public RestResult removeIntegralTaskById(@RequestParam("id") int integralTaskId) {
        try {
            integralTaskService.removeIntegralTaskById(integralTaskId);
            return RestUtils.success(true);
        } catch (Exception ex) {
            LOG.error("remove IntegralTask failure : id=integralTaskId", ex);
            return RestUtils.error("remove IntegralTask failure : " + ex.getMessage());
        }
    }

    /**
     * 修改成长值
     **/
    @RequestMapping(value = "updateGrowthValue", method = RequestMethod.GET)
    public RestResult updateGrowthValue(@RequestParam("id") int integralTaskId,@RequestParam("growthValue") int growthValue) {
        try {
            IntegralTaskVo integralTask = integralTaskService.updateGrowthValue(integralTaskId,growthValue);
            return RestUtils.successWhenNotNull(integralTask);
        } catch (BusinessException ex) {
            LOG.error("update DailyTask failure : dailyTask", integralTaskId, ex);
            LOG.error("update DailyTask failure : dailyTask", growthValue, ex);
            return RestUtils.error("update DailyTask failure : " + ex.getMessage());
        }
    }


    /**
     * 修改状态
     **/
    @RequestMapping(value = "updateState", method = RequestMethod.GET)
    public RestResult updateState(@RequestParam("id") int integralTaskId) {
        try {
            IntegralTaskVo integralTask = integralTaskService.updateState(integralTaskId);
            return RestUtils.successWhenNotNull(integralTask);
        } catch (BusinessException ex) {
            LOG.error("update DailyTask failure : dailyTask", integralTaskId, ex);
            return RestUtils.error("update DailyTask failure : " + ex.getMessage());
        }
    }


}