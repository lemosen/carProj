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

import com.yi.core.basic.dao.LoginRecordDao;
import com.yi.core.basic.domain.bo.LoginRecordBo;
import com.yi.core.basic.domain.entity.LoginRecord;
import com.yi.core.basic.domain.entity.LoginRecord_;
import com.yi.core.basic.domain.simple.LoginRecordSimple;
import com.yi.core.basic.domain.vo.LoginRecordListVo;
import com.yi.core.basic.domain.vo.LoginRecordVo;
import com.yi.core.basic.service.ILoginRecordService;
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
public class LoginRecordServiceImpl implements ILoginRecordService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(LoginRecordServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private LoginRecordDao loginRecordDao;

	private EntityListVoBoSimpleConvert<LoginRecord, LoginRecordBo, LoginRecordVo, LoginRecordSimple, LoginRecordListVo> loginRecordConvert;

	@Override
	public LoginRecord getLoginRecordById(int loginRecordId) {
		return loginRecordDao.getOne(loginRecordId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LoginRecordVo getLoginRecordVoById(int loginRecordId) {

		return loginRecordConvert.toVo(this.loginRecordDao.getOne(loginRecordId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LoginRecordListVo getLoginRecordListVoById(int loginRecordId) {
		return loginRecordConvert.toListVo(this.loginRecordDao.getOne(loginRecordId));
	}

	@Override
	public LoginRecord addLoginRecord(LoginRecord loginRecord) {
		return loginRecordDao.save(loginRecord);
	}

	@Override
	public LoginRecordVo addLoginRecord(LoginRecordBo loginRecordBo) {
		return loginRecordConvert.toVo(loginRecordDao.save(loginRecordConvert.toEntity(loginRecordBo)));
	}

	@Override
	public LoginRecord updateLoginRecord(LoginRecord loginRecord) {

		LoginRecord dbLoginRecord = loginRecordDao.getOne(loginRecord.getId());
		AttributeReplication.copying(loginRecord, dbLoginRecord, LoginRecord_.guid, LoginRecord_.member, LoginRecord_.loginIp, LoginRecord_.loginSource, LoginRecord_.createTime);
		return dbLoginRecord;
	}

	@Override
	public LoginRecordVo updateLoginRecord(LoginRecordBo loginRecordBo) {
		LoginRecord dbLoginRecord = loginRecordDao.getOne(loginRecordBo.getId());
		AttributeReplication.copying(loginRecordBo, dbLoginRecord, LoginRecord_.guid, LoginRecord_.member, LoginRecord_.loginIp, LoginRecord_.loginSource, LoginRecord_.createTime);
		return loginRecordConvert.toVo(dbLoginRecord);
	}

	@Override
	public void removeLoginRecordById(int loginRecordId) {
		loginRecordDao.deleteById(loginRecordId);
	}

	@Override
	public Page<LoginRecord> query(Pagination<LoginRecord> query) {
		query.setEntityClazz(LoginRecord.class);
		Page<LoginRecord> page = loginRecordDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<LoginRecordListVo> queryListVo(Pagination<LoginRecord> query) {

		Page<LoginRecord> pages = this.query(query);

		List<LoginRecordListVo> vos = loginRecordConvert.toListVos(pages.getContent());
		return new PageImpl<LoginRecordListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.loginRecordConvert = new EntityListVoBoSimpleConvert<LoginRecord, LoginRecordBo, LoginRecordVo, LoginRecordSimple, LoginRecordListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<LoginRecord, LoginRecordVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<LoginRecord, LoginRecordVo>(beanConvertManager) {
					@Override
					protected void postConvert(LoginRecordVo LoginRecordVo, LoginRecord LoginRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<LoginRecord, LoginRecordListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<LoginRecord, LoginRecordListVo>(beanConvertManager) {
					@Override
					protected void postConvert(LoginRecordListVo LoginRecordListVo, LoginRecord LoginRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<LoginRecord, LoginRecordBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<LoginRecord, LoginRecordBo>(beanConvertManager) {
					@Override
					protected void postConvert(LoginRecordBo LoginRecordBo, LoginRecord LoginRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<LoginRecordBo, LoginRecord> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<LoginRecordBo, LoginRecord>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<LoginRecord, LoginRecordSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<LoginRecord, LoginRecordSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<LoginRecordSimple, LoginRecord> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<LoginRecordSimple, LoginRecord>(beanConvertManager) {
					@Override
					public LoginRecord convert(LoginRecordSimple LoginRecordSimple) throws BeansException {
						return loginRecordDao.getOne(LoginRecordSimple.getId());
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
