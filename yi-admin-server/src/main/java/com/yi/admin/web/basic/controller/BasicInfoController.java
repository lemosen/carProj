/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.basic.controller;

import com.yi.core.basic.domain.bo.BasicInfoBo;
import com.yi.core.basic.domain.entity.BasicInfo;
import com.yi.core.basic.domain.vo.BasicInfoListVo;
import com.yi.core.basic.domain.vo.BasicInfoVo;


import com.yi.core.basic.service.IBasicInfoService;
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
@RequestMapping(value = "/basicInfo")
public class BasicInfoController {

    private final Logger LOG = LoggerFactory.getLogger(BasicInfoController.class);


    @Resource
    private IBasicInfoService basicInfoService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<BasicInfoListVo> queryBasicInfo(@RequestBody Pagination<BasicInfo> query) {
        Page<BasicInfoListVo> page = basicInfoService.queryListVo(query);
        return page;
    }

    /**
     * 查看对象
     **/
    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public RestResult viewBasicInfo(@RequestParam("id") int basicInfoId) {
        try {
            BasicInfoVo basicInfo = basicInfoService.getBasicInfoVoById(basicInfoId);
            return RestUtils.successWhenNotNull(basicInfo);
        } catch (BusinessException ex) {
            LOG.error("get BasicInfo failure : id=basicInfoId", ex);
            return RestUtils.error("get BasicInfo failure : " + ex.getMessage());
        }
    }


    /**
     * 保存新增对象
     **/
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public RestResult addBasicInfo(@RequestBody BasicInfoBo basicInfo) {
        try {
            BasicInfoVo dbBasicInfo = basicInfoService.addBasicInfo(basicInfo);
            return RestUtils.successWhenNotNull(dbBasicInfo);
        } catch (BusinessException ex) {
            LOG.error("add BasicInfo failure : basicInfo", basicInfo, ex);
            return RestUtils.error("add BasicInfo failure : " + ex.getMessage());
        }
    }

    /**
     * 保存更新对象
     **/
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public RestResult updateBasicInfo(@RequestBody BasicInfoBo basicInfo) {
        try {
            BasicInfoVo dbBasicInfo = basicInfoService.updateBasicInfo(basicInfo);
            return RestUtils.successWhenNotNull(dbBasicInfo);
        } catch (BusinessException ex) {
            LOG.error("update BasicInfo failure : basicInfo", basicInfo, ex);
            return RestUtils.error("update BasicInfo failure : " + ex.getMessage());
        }
    }

    /**
     * 删除对象
     **/
    @RequestMapping(value = "removeById", method = RequestMethod.GET)
    public RestResult removeBasicInfoById(@RequestParam("id") int basicInfoId) {
        try {
            basicInfoService.removeBasicInfoById(basicInfoId);
            return RestUtils.success(true);
        } catch (Exception ex) {
            LOG.error("remove BasicInfo failure : id=basicInfoId", ex);
            return RestUtils.error("remove BasicInfo failure : " + ex.getMessage());
        }
    }

    /**
     * 查询打开页面显示的数据
     **/
    @RequestMapping(value = "getAll", method = RequestMethod.POST)
    public RestResult getAll() {
        try {
            return RestUtils.success(basicInfoService.getAll());
        } catch (Exception ex) {
            LOG.error("remove BasicInfo failure : id=basicInfoId", ex);
            return RestUtils.error("remove BasicInfo failure : " + ex.getMessage());
        }
    }


}