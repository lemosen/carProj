/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import com.yi.core.commodity.domain.entity.Comment_;
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
import com.yi.core.basic.dao.IntegralRecordDao;
import com.yi.core.basic.domain.bo.IntegralRecordBo;
import com.yi.core.basic.domain.entity.IntegralRecord;
import com.yi.core.basic.domain.entity.IntegralRecord_;
import com.yi.core.basic.domain.entity.IntegralTask;
import com.yi.core.basic.domain.simple.IntegralRecordSimple;
import com.yi.core.basic.domain.vo.IntegralRecordListVo;
import com.yi.core.basic.domain.vo.IntegralRecordVo;
import com.yi.core.basic.service.IIntegralRecordService;
import com.yi.core.basic.service.IIntegralTaskService;
import com.yi.core.member.domain.entity.Account;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.service.IAccountService;
import com.yi.core.member.service.IMemberService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class IntegralRecordServiceImpl implements IIntegralRecordService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(IntegralRecordServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private IntegralRecordDao integralRecordDao;

	@Resource
	private IMemberService memberService;

	@Resource
	private IIntegralTaskService integralTaskService;

	@Resource
	private IAccountService accountService;

	private EntityListVoBoSimpleConvert<IntegralRecord, IntegralRecordBo, IntegralRecordVo, IntegralRecordSimple, IntegralRecordListVo> integralRecordConvert;

	/**
	 * 分页查询IntegralRecord
	 **/
	@Override
	public Page<IntegralRecord> query(Pagination<IntegralRecord> query) {
		query.setEntityClazz(IntegralRecord.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list1.add(criteriaBuilder.desc(root.get(IntegralRecord_.createTime)));
		}));
		Page<IntegralRecord> page = integralRecordDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 创建IntegralRecord
	 **/
	@Override
	public IntegralRecordVo addIntegralRecord(IntegralRecordBo integralRecordBo) {
		return integralRecordConvert.toVo(integralRecordDao.save(integralRecordConvert.toEntity(integralRecordBo)));
	}

	/**
	 * 创建IntegralRecord
	 **/
	@Override
	public IntegralRecordVo addIntegralRecord(IntegralRecord integralRecord) {
		return integralRecordConvert.toVo(integralRecordDao.save(integralRecord));
	}

	/**
	 * 更新IntegralRecord
	 **/
	@Override
	public IntegralRecordVo updateIntegralRecord(IntegralRecordBo integralRecordBo) {
		IntegralRecord dbIntegralRecord = integralRecordDao.getOne(integralRecordBo.getId());
		AttributeReplication.copying(integralRecordBo, dbIntegralRecord, IntegralRecord_.guid, IntegralRecord_.member, IntegralRecord_.operateType, IntegralRecord_.operateIntegral,
				IntegralRecord_.currentIntegral, IntegralRecord_.createTime);
		return integralRecordConvert.toVo(dbIntegralRecord);
	}

	/**
	 * 删除IntegralRecord
	 **/
	@Override
	public void removeIntegralRecordById(int id) {
		integralRecordDao.deleteById(id);
	}

	/**
	 * 根据ID得到IntegralRecord
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IntegralRecordVo getIntegralRecordVoById(int id) {

		return integralRecordConvert.toVo(this.integralRecordDao.getOne(id));
	}

	/**
	 * 根据ID得到IntegralRecordListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IntegralRecordVo getListVoById(int id) {
		return integralRecordConvert.toVo(this.integralRecordDao.getOne(id));
	}

	/**
	 * 分页查询: IntegralRecord
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<IntegralRecordListVo> queryListVo(Pagination<IntegralRecord> query) {

		Page<IntegralRecord> pages = this.query(query);

		List<IntegralRecordListVo> vos = integralRecordConvert.toListVos(pages.getContent());
		return new PageImpl<IntegralRecordListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.integralRecordConvert = new EntityListVoBoSimpleConvert<IntegralRecord, IntegralRecordBo, IntegralRecordVo, IntegralRecordSimple, IntegralRecordListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<IntegralRecord, IntegralRecordVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralRecord, IntegralRecordVo>(beanConvertManager) {
					@Override
					protected void postConvert(IntegralRecordVo IntegralRecordVo, IntegralRecord IntegralRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<IntegralRecord, IntegralRecordListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralRecord, IntegralRecordListVo>(beanConvertManager) {
					@Override
					protected void postConvert(IntegralRecordListVo IntegralRecordListVo, IntegralRecord IntegralRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<IntegralRecord, IntegralRecordBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralRecord, IntegralRecordBo>(beanConvertManager) {
					@Override
					protected void postConvert(IntegralRecordBo IntegralRecordBo, IntegralRecord IntegralRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<IntegralRecordBo, IntegralRecord> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralRecordBo, IntegralRecord>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<IntegralRecord, IntegralRecordSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralRecord, IntegralRecordSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<IntegralRecordSimple, IntegralRecord> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralRecordSimple, IntegralRecord>(beanConvertManager) {
					@Override
					public IntegralRecord convert(IntegralRecordSimple IntegralRecordSimple) throws BeansException {
						return integralRecordDao.getOne(IntegralRecordSimple.getId());
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
	 * 根据任务类型新增积分
	 * 
	 * @param memberId
	 * @param taskType
	 */
	@Override
	public void addIntegralRecordByTaskType(Integer memberId, BasicEnum taskType) {
		if (memberId != null && taskType != null) {
			Member dbMember = memberService.getMemberById(memberId);
			// 获取积分任务
			IntegralTask dbIntegralTask = integralTaskService.getIntegralTaskByType(taskType.getCode());
			if (dbMember != null && dbIntegralTask != null) {
				// 封装积分记录数据
				IntegralRecord integralRecord = new IntegralRecord();
				integralRecord.setMember(dbMember);
				integralRecord.setTaskType(dbIntegralTask.getTaskType());
				integralRecord.setTaskName(dbIntegralTask.getTaskName());
				integralRecord.setOperateType(BasicEnum.OPERATE_TYPE_ADD.getCode());
				integralRecord.setOperateIntegral(dbIntegralTask.getGrowthValue());
				integralRecord.setCurrentIntegral(dbMember.getAccount().getIntegral());
				integralRecordDao.save(integralRecord);
				// 修改会员账户积分
				Account account = dbMember.getAccount();
				account.setIntegral(account.getIntegral() + dbIntegralTask.getGrowthValue());
				account.setResidualIntegral(account.getResidualIntegral() + dbIntegralTask.getGrowthValue());
				accountService.updateAccount(account);
			}
		}
	}

	/**
	 * 根据任务类型新增积分
	 * 
	 * @param memberId
	 * @param taskType
	 */
	@Override
	public void addIntegralRecordByTaskType(Member member, BasicEnum taskType) {
		if (member != null && taskType != null) {
			// 获取积分任务
			IntegralTask dbIntegralTask = integralTaskService.getIntegralTaskByType(taskType.getCode());
			if (member != null && dbIntegralTask != null) {
				// 封装积分记录数据
				IntegralRecord integralRecord = new IntegralRecord();
				integralRecord.setMember(member);
				integralRecord.setTaskType(dbIntegralTask.getTaskType());
				integralRecord.setTaskName(dbIntegralTask.getTaskName());
				integralRecord.setOperateType(BasicEnum.OPERATE_TYPE_ADD.getCode());
				integralRecord.setOperateIntegral(dbIntegralTask.getGrowthValue());
				integralRecord.setCurrentIntegral(member.getAccount().getIntegral());
				integralRecordDao.save(integralRecord);
				// 修改会员账户积分
				Account account = member.getAccount();
				account.setIntegral(account.getIntegral() + dbIntegralTask.getGrowthValue());
				account.setResidualIntegral(account.getResidualIntegral() + dbIntegralTask.getGrowthValue());
				accountService.updateAccount(account);
			}
		}
	}

	/**
	 * 订单操作新增积分
	 * 
	 * @param memberId
	 * @param orderIntegral
	 * @param taskName
	 */
	@Override
	public Account addIntegralRecordByOrder(Member member, BigDecimal orderIntegral, BasicEnum taskType) {
		if (member != null && orderIntegral != null && orderIntegral.compareTo(BigDecimal.ZERO) > 0 && taskType != null) {
			// 封装积分记录数据
			IntegralRecord integralRecord = new IntegralRecord();
			integralRecord.setMember(member);
			integralRecord.setTaskType(taskType.getCode());
			integralRecord.setTaskName(taskType.getValue());
			integralRecord.setOperateType(BasicEnum.OPERATE_TYPE_ADD.getCode());
			integralRecord.setOperateIntegral(orderIntegral.intValue());
			integralRecord.setCurrentIntegral(member.getAccount().getIntegral());
			integralRecordDao.save(integralRecord);
			// 修改会员账户积分
			Account account = member.getAccount();
			account.setIntegral(account.getIntegral() + orderIntegral.intValue());
			account.setResidualIntegral(account.getResidualIntegral() + orderIntegral.intValue());
			return accountService.updateAccount(account);
		}
		return null;
	}

}
