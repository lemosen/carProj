/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.basic.controller;

import com.yi.core.basic.domain.bo.MessageReadBo;
import com.yi.core.basic.domain.entity.MessageRead;
import com.yi.core.basic.domain.vo.MessageReadListVo;
import com.yi.core.basic.domain.vo.MessageReadVo;


import com.yi.core.basic.service.IMessageReadService;
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
@RequestMapping(value = "/messageRead")
public class MessageReadController {

    private final Logger LOG = LoggerFactory.getLogger(MessageReadController.class);


    @Resource
    private IMessageReadService messageReadService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<MessageReadListVo> queryMessageRead(@RequestBody Pagination<MessageRead> query) {
    Page<MessageReadListVo> page = messageReadService.queryListVo(query);
        return page;
        }

        /**
        * 查看对象
        **/
        @RequestMapping(value = "getById", method = RequestMethod.GET)
        public RestResult viewMessageRead(@RequestParam("id") int messageReadId) {
        try {
        MessageReadVo messageRead = messageReadService.getMessageReadVoById(messageReadId);
        return RestUtils.successWhenNotNull(messageRead);
        } catch (BusinessException ex) {
        LOG.error("get MessageRead failure : id=messageReadId", ex);
        return RestUtils.error("get MessageRead failure : " + ex.getMessage());
        }
        }


        /**
        * 保存新增对象
        **/
        @RequestMapping(value = "add", method = RequestMethod.POST)
        public RestResult addMessageRead(@RequestBody MessageReadBo messageRead) {
        try {
        MessageReadVo dbMessageRead = messageReadService.addMessageRead(messageRead);
        return RestUtils.successWhenNotNull(dbMessageRead);
        } catch (BusinessException ex) {
        LOG.error("add MessageRead failure : messageRead", messageRead, ex);
        return RestUtils.error("add MessageRead failure : " + ex.getMessage());
        }
        }

        /**
        * 保存更新对象
        **/
        @RequestMapping(value = "update", method = RequestMethod.POST)
        public RestResult updateMessageRead(@RequestBody MessageReadBo messageRead) {
        try {
        MessageReadVo dbMessageRead = messageReadService.updateMessageRead(messageRead);
        return RestUtils.successWhenNotNull(dbMessageRead);
        } catch (BusinessException ex) {
        LOG.error("update MessageRead failure : messageRead", messageRead, ex);
        return RestUtils.error("update MessageRead failure : " + ex.getMessage());
        }
        }

        /**
        * 删除对象
        **/
        @RequestMapping(value = "removeById", method = RequestMethod.GET)
        public RestResult removeMessageReadById(@RequestParam("id") int messageReadId) {
        try {
        messageReadService.removeMessageReadById(messageReadId);
        return RestUtils.success(true);
        } catch (Exception ex) {
        LOG.error("remove MessageRead failure : id=messageReadId", ex);
        return RestUtils.error("remove MessageRead failure : " + ex.getMessage());
        }
        }
}