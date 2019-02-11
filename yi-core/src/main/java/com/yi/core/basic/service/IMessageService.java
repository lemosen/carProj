package com.yi.core.basic.service;/*
									* Powered By [yihz-framework]
									* Web Site: yihz
									* Since 2018 - 2018
									*/

import org.springframework.data.domain.Page;

import com.yi.core.basic.domain.bo.MessageBo;
import com.yi.core.basic.domain.entity.Message;
import com.yi.core.basic.domain.vo.MessageListVo;
import com.yi.core.basic.domain.vo.MessageVo;
import com.yihz.common.persistence.Pagination;

/**
 * *
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public interface IMessageService {

	Page<Message> query(Pagination<Message> query);

	/**
	 * 创建Message
	 **/
	MessageVo addMessage(MessageBo message);

	/**
	 * 更新Message
	 **/
	MessageVo updateMessage(MessageBo message);

	/**
	 * 删除Message
	 **/
	void removeMessageById(int messageId);

	/**
	 * 根据ID得到MessageVo
	 **/
	MessageVo getMessageVoById(int messageId);

	/**
	 * 根据ID得到MessageListVo
	 **/
	MessageVo getListVoById(int messageId);

	/**
	 * 分页查询: Message
	 **/
	Page<MessageListVo> queryListVo(Pagination<Message> query);

	/**
	 * 启用
	 * 
	 * @param messageId
	 * @return
	 */
	MessageVo enable(int messageId);

	/**
	 * 禁用
	 * 
	 * @param messageId
	 * @return
	 */
	MessageVo disable(int messageId);
}
