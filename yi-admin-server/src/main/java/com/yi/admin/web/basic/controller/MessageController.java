/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.basic.controller;

import com.yi.core.basic.domain.bo.MessageBo;
import com.yi.core.basic.domain.entity.Message;
import com.yi.core.basic.domain.vo.MessageListVo;
import com.yi.core.basic.domain.vo.MessageVo;


import com.yi.core.basic.service.IMessageService;
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
@RequestMapping(value = "/message")
public class MessageController {

    private final Logger LOG = LoggerFactory.getLogger(MessageController.class);


    @Resource
    private IMessageService messageService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<MessageListVo> queryMessage(@RequestBody Pagination<Message> query) {
        Page<MessageListVo> page = messageService.queryListVo(query);
        return page;
    }

    /**
     * 查看对象
     **/
    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public RestResult viewMessage(@RequestParam("id") int messageId) {
        try {
            MessageVo message = messageService.getMessageVoById(messageId);
            return RestUtils.successWhenNotNull(message);
        } catch (BusinessException ex) {
            LOG.error("get Message failure : id=messageId", ex);
            return RestUtils.error("get Message failure : " + ex.getMessage());
        }
    }


    /**
     * 保存新增对象
     **/
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public RestResult addMessage(@RequestBody MessageBo message) {
        try {
            MessageVo dbMessage = messageService.addMessage(message);
            return RestUtils.successWhenNotNull(dbMessage);
        } catch (BusinessException ex) {
            LOG.error("add Message failure : message", message, ex);
            return RestUtils.error("add Message failure : " + ex.getMessage());
        }
    }

    /**
     * 保存更新对象
     **/
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public RestResult updateMessage(@RequestBody MessageBo message) {
        try {
            MessageVo dbMessage = messageService.updateMessage(message);
            return RestUtils.successWhenNotNull(dbMessage);
        } catch (BusinessException ex) {
            LOG.error("update Message failure : message", message, ex);
            return RestUtils.error("update Message failure : " + ex.getMessage());
        }
    }

    /**
     * 删除对象
     **/
    @RequestMapping(value = "removeById", method = RequestMethod.GET)
    public RestResult removeMessageById(@RequestParam("id") int messageId) {
        try {
            messageService.removeMessageById(messageId);
            return RestUtils.success(true);
        } catch (Exception ex) {
            LOG.error("remove Message failure : id=messageId", ex);
            return RestUtils.error("remove Message failure : " + ex.getMessage());
        }
    }


    /**
     * 启用对象
     **/
    @RequestMapping(value = "enable", method = RequestMethod.GET)
    public RestResult enable(@RequestParam("id") int messageId) {
        try {
            return RestUtils.success(messageService.enable(messageId));
        } catch (Exception ex) {
            LOG.error("remove Message failure : id=messageId", ex);
            return RestUtils.error("remove Message failure : " + ex.getMessage());
        }
    }



    /**
     * 禁用对象
     **/
    @RequestMapping(value = "disable", method = RequestMethod.GET)
    public RestResult disable(@RequestParam("id") int messageId) {
        try {
            return RestUtils.success( messageService.disable(messageId));
        } catch (Exception ex) {
            LOG.error("remove Message failure : id=messageId", ex);
            return RestUtils.error("remove Message failure : " + ex.getMessage());
        }
    }



}