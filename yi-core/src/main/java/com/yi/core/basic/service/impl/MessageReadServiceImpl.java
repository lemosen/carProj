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

import com.yi.core.basic.dao.MessageReadDao;
import com.yi.core.basic.domain.bo.MessageReadBo;
import com.yi.core.basic.domain.entity.MessageRead;
import com.yi.core.basic.domain.entity.MessageRead_;
import com.yi.core.basic.domain.simple.MessageReadSimple;
import com.yi.core.basic.domain.vo.MessageReadListVo;
import com.yi.core.basic.domain.vo.MessageReadVo;
import com.yi.core.basic.service.IMessageReadService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class MessageReadServiceImpl implements IMessageReadService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(MessageReadServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private MessageReadDao messageReadDao;

	private EntityListVoBoSimpleConvert<MessageRead, MessageReadBo, MessageReadVo, MessageReadSimple, MessageReadListVo> messageReadConvert;

	/**
	 * 分页查询MessageRead
	 **/
	@Override
	public Page<MessageRead> query(Pagination<MessageRead> query) {
		query.setEntityClazz(MessageRead.class);
		Page<MessageRead> page = messageReadDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 创建MessageRead
	 **/
	@Override
	public MessageReadVo addMessageRead(MessageReadBo messageReadBo) {
		return messageReadConvert.toVo(messageReadDao.save(messageReadConvert.toEntity(messageReadBo)));
	}

	/**
	 * 更新MessageRead
	 **/
	@Override
	public MessageReadVo updateMessageRead(MessageReadBo messageReadBo) {
		MessageRead dbMessageRead = messageReadDao.getOne(messageReadBo.getId());
		AttributeReplication.copying(messageReadConvert.toEntity(messageReadBo), dbMessageRead, MessageRead_.guid, MessageRead_.message, MessageRead_.member,
				MessageRead_.readTime);
		return messageReadConvert.toVo(dbMessageRead);
	}

	/**
	 * 删除MessageRead
	 **/
	@Override
	public void removeMessageReadById(int id) {
		messageReadDao.deleteById(id);
	}

	/**
	 * 根据ID得到MessageRead
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MessageReadVo getMessageReadVoById(int id) {

		return messageReadConvert.toVo(this.messageReadDao.getOne(id));
	}

	/**
	 * 根据ID得到MessageReadListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MessageReadVo getListVoById(int id) {
		return messageReadConvert.toVo(this.messageReadDao.getOne(id));
	}

	/**
	 * 分页查询: MessageRead
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<MessageReadListVo> queryListVo(Pagination<MessageRead> query) {

		Page<MessageRead> pages = this.query(query);

		List<MessageReadListVo> vos = messageReadConvert.toListVos(pages.getContent());
		return new PageImpl<MessageReadListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.messageReadConvert = new EntityListVoBoSimpleConvert<MessageRead, MessageReadBo, MessageReadVo, MessageReadSimple, MessageReadListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<MessageRead, MessageReadVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MessageRead, MessageReadVo>(beanConvertManager) {
					@Override
					protected void postConvert(MessageReadVo MessageReadVo, MessageRead MessageRead) {

					}
				};
			}

			@Override
			protected BeanConvert<MessageRead, MessageReadListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MessageRead, MessageReadListVo>(beanConvertManager) {
					@Override
					protected void postConvert(MessageReadListVo MessageReadListVo, MessageRead MessageRead) {

					}
				};
			}

			@Override
			protected BeanConvert<MessageRead, MessageReadBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MessageRead, MessageReadBo>(beanConvertManager) {
					@Override
					protected void postConvert(MessageReadBo MessageReadBo, MessageRead MessageRead) {

					}
				};
			}

			@Override
			protected BeanConvert<MessageReadBo, MessageRead> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MessageReadBo, MessageRead>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<MessageRead, MessageReadSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MessageRead, MessageReadSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<MessageReadSimple, MessageRead> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MessageReadSimple, MessageRead>(beanConvertManager) {
					@Override
					public MessageRead convert(MessageReadSimple MessageReadSimple) throws BeansException {
						return messageReadDao.getOne(MessageReadSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}
}
