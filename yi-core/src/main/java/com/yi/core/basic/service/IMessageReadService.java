package com.yi.core.basic.service;/*
									* Powered By [yihz-framework]
									* Web Site: yihz
									* Since 2018 - 2018
									*/

import org.springframework.data.domain.Page;

import com.yi.core.basic.domain.bo.MessageReadBo;
import com.yi.core.basic.domain.entity.MessageRead;
import com.yi.core.basic.domain.vo.MessageReadListVo;
import com.yi.core.basic.domain.vo.MessageReadVo;
import com.yihz.common.persistence.Pagination;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface IMessageReadService {

	Page<MessageRead> query(Pagination<MessageRead> query);

	/**
	 * 创建MessageRead
	 **/
	MessageReadVo addMessageRead(MessageReadBo messageRead);

	/**
	 * 更新MessageRead
	 **/
	MessageReadVo updateMessageRead(MessageReadBo messageRead);

	/**
	 * 删除MessageRead
	 **/
	void removeMessageReadById(int messageReadId);

	/**
	 * 根据ID得到MessageReadVo
	 **/
	MessageReadVo getMessageReadVoById(int messageReadId);

	/**
	 * 根据ID得到MessageReadListVo
	 **/
	MessageReadVo getListVoById(int messageReadId);

	/**
	 * 分页查询: MessageRead
	 **/
	Page<MessageReadListVo> queryListVo(Pagination<MessageRead> query);

}
