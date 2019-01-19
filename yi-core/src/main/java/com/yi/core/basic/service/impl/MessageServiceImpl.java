/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.basic.BasicEnum;
import com.yi.core.basic.dao.MessageDao;
import com.yi.core.basic.domain.bo.MessageBo;
import com.yi.core.basic.domain.entity.Message;
import com.yi.core.basic.domain.entity.Message_;
import com.yi.core.basic.domain.simple.MessageSimple;
import com.yi.core.basic.domain.vo.MessageListVo;
import com.yi.core.basic.domain.vo.MessageVo;
import com.yi.core.basic.service.IMessageService;
import com.yi.core.common.Deleted;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 消息
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class MessageServiceImpl implements IMessageService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private MessageDao messageDao;

	private EntityListVoBoSimpleConvert<Message, MessageBo, MessageVo, MessageSimple, MessageListVo> messageConvert;

	/**
	 * 分页查询Message
	 **/
	@Override
	public Page<Message> query(Pagination<Message> query) {
		query.setEntityClazz(Message.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			// 排序
			list1.add(criteriaBuilder.desc(root.get(Message_.createTime)));
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Message_.deleted), Deleted.DEL_FALSE)));
			// Object memberId = query.getParams().get("member.id");
			// if (memberId != null) {
			// Join<Message, MessageRead> messageReadJoin = root.join("messageReads",
			// JoinType.LEFT);
			// list.add(criteriaBuilder
			// .or(criteriaBuilder.equal(messageReadJoin.get(MessageRead_.member).get(Member_.id),
			// memberId)));
			//
			// }
		}));
		Page<Message> page = messageDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 创建Message
	 **/
	@Override
	public MessageVo addMessage(MessageBo messageBo) {
		Message message = messageConvert.toEntity(messageBo);
		message.setMessageType(BasicEnum.MESSAGE_TYPE_SYSTEM.getCode());
		return messageConvert.toVo(messageDao.save(message));
	}

	/**
	 * 更新Message
	 **/
	@Override
	public MessageVo updateMessage(MessageBo messageBo) {
		Message dbMessage = messageDao.getOne(messageBo.getId());
		AttributeReplication.copying(messageConvert.toEntity(messageBo), dbMessage, Message_.title, Message_.content, Message_.messageType, Message_.sort, Message_.state);
		return messageConvert.toVo(dbMessage);
	}

	/**
	 * 删除Message
	 **/
	@Override
	public void removeMessageById(int id) {
		messageDao.deleteById(id);
	}

	/**
	 * 根据ID得到Message
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MessageVo getMessageVoById(int id) {

		return messageConvert.toVo(this.messageDao.getOne(id));
	}

	/**
	 * 根据ID得到MessageListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MessageVo getListVoById(int id) {
		return messageConvert.toVo(this.messageDao.getOne(id));
	}

	/**
	 * 分页查询: Message
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<MessageListVo> queryListVo(Pagination<Message> query) {
		Page<Message> pages = this.query(query);

		List<MessageListVo> vos = messageConvert.toListVos(pages.getContent());
		return new PageImpl<MessageListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.messageConvert = new EntityListVoBoSimpleConvert<Message, MessageBo, MessageVo, MessageSimple, MessageListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Message, MessageVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Message, MessageVo>(beanConvertManager) {
					@Override
					protected void postConvert(MessageVo MessageVo, Message Message) {

					}
				};
			}

			@Override
			protected BeanConvert<Message, MessageListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Message, MessageListVo>(beanConvertManager) {
					@Override
					protected void postConvert(MessageListVo MessageListVo, Message Message) {

					}
				};
			}

			@Override
			protected BeanConvert<Message, MessageBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Message, MessageBo>(beanConvertManager) {
					@Override
					protected void postConvert(MessageBo MessageBo, Message Message) {

					}
				};
			}

			@Override
			protected BeanConvert<MessageBo, Message> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MessageBo, Message>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Message, MessageSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Message, MessageSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<MessageSimple, Message> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MessageSimple, Message>(beanConvertManager) {
					@Override
					public Message convert(MessageSimple MessageSimple) throws BeansException {
						return messageDao.getOne(MessageSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}

	/**
	 * 启用
	 * 
	 * @param messageId
	 * @return
	 */
	@Override
	public MessageVo enable(int messageId) {
		Message message = messageDao.getOne(messageId);
		message.setState(BasicEnum.STATE_ENABLE.getCode());
		return messageConvert.toVo(message);
	}

	/**
	 * 禁用
	 * 
	 * @param messageId
	 * @return
	 */
	@Override
	public MessageVo disable(int messageId) {
		Message message = messageDao.getOne(messageId);
		message.setState(BasicEnum.STATE_DISABLE.getCode());
		return messageConvert.toVo(message);
	}

}
