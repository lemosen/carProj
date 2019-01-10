/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.activity.dao.RewardDao;
import com.yi.core.activity.domain.bo.RewardBo;
import com.yi.core.activity.domain.entity.Reward;
import com.yi.core.activity.domain.entity.Reward_;
import com.yi.core.activity.domain.simple.RewardSimple;
import com.yi.core.activity.domain.vo.RewardListVo;
import com.yi.core.activity.domain.vo.RewardVo;
import com.yi.core.activity.service.IRewardService;
import com.yi.core.common.Deleted;
import com.yi.core.common.NumberGenerateUtils;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 奖励
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class RewardServiceImpl implements IRewardService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(RewardServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private RewardDao rewardDao;

	private EntityListVoBoSimpleConvert<Reward, RewardBo, RewardVo, RewardSimple, RewardListVo> rewardConvert;

	/**
	 * 分页查询Reward
	 **/
	@Override
	public Page<Reward> query(Pagination<Reward> query) {
		query.setEntityClazz(Reward.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Reward_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(Reward_.createTime)));
		}));
		Page<Reward> page = rewardDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: Reward
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<RewardListVo> queryListVo(Pagination<Reward> query) {
		Page<Reward> pages = this.query(query);
		List<RewardListVo> vos = rewardConvert.toListVos(pages.getContent());
		return new PageImpl<RewardListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 分页查询: Reward
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<RewardListVo> queryListVoForApp(Pagination<Reward> query) {
		query.setEntityClazz(Reward.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Reward_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(Reward_.createTime)));
		}));
		Page<Reward> pages = rewardDao.findAll(query, query.getPageRequest());
		List<RewardListVo> vos = rewardConvert.toListVos(pages.getContent());
		return new PageImpl<RewardListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建Reward
	 **/
	@Override
	public Reward addReward(Reward reward) {
		if (reward == null || reward.getRewardType() == null || CollectionUtils.isEmpty(reward.getPrizes())) {
			throw new BusinessException("提交数据不能为空");
		}
		reward.setCode(NumberGenerateUtils.generateRewardNo());
		return rewardDao.save(reward);
	}

	/**
	 * 创建Reward
	 **/
	@Override
	public RewardListVo addReward(RewardBo rewardBo) {
		return rewardConvert.toListVo(this.addReward(rewardConvert.toEntity(rewardBo)));
	}

	/**
	 * 更新Reward
	 **/
	@Override
	public Reward updateReward(Reward reward) {
		Reward dbReward = this.getRewardById(reward.getId());
		AttributeReplication.copying(reward, dbReward, Reward_.name, Reward_.rewardType, Reward_.signDays, Reward_.state, Reward_.remark);
		dbReward.setPrizes(reward.getPrizes());
		return dbReward;
	}

	/**
	 * 更新Reward
	 **/
	@Override
	public RewardListVo updateReward(RewardBo rewardBo) {
		Reward dbReward = this.updateReward(rewardConvert.toEntity(rewardBo));
		return rewardConvert.toListVo(dbReward);
	}

	/**
	 * 删除Reward
	 **/
	@Override
	public void removeRewardById(int id) {
		Reward dbReward = this.getRewardById(id);
		if (dbReward != null) {
			dbReward.setDeleted(Deleted.DEL_FALSE);
			dbReward.setDelTime(new Date());
			dbReward.setPrizes(null);
		}
	}

	/**
	 * 根据ID得到Reward
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Reward getRewardById(int id) {
		if (rewardDao.existsById(id)) {
			return this.rewardDao.getOne(id);
		}
		return null;
	}

	/**
	 * 根据ID得到RewardBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RewardBo getRewardBoById(int id) {
		return rewardConvert.toBo(this.getRewardById(id));
	}

	/**
	 * 根据ID得到RewardVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RewardVo getRewardVoById(int id) {
		return rewardConvert.toVo(this.getRewardById(id));
	}

	/**
	 * 根据ID得到RewardListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RewardListVo getListVoById(int id) {
		return rewardConvert.toListVo(this.getRewardById(id));
	}

	protected void initConvert() {
		this.rewardConvert = new EntityListVoBoSimpleConvert<Reward, RewardBo, RewardVo, RewardSimple, RewardListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Reward, RewardVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Reward, RewardVo>(beanConvertManager) {
					@Override
					protected void postConvert(RewardVo rewardVo, Reward reward) {
					}
				};
			}

			@Override
			protected BeanConvert<Reward, RewardListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Reward, RewardListVo>(beanConvertManager) {
					@Override
					protected void postConvert(RewardListVo rewardListVo, Reward reward) {
					}
				};
			}

			@Override
			protected BeanConvert<Reward, RewardBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Reward, RewardBo>(beanConvertManager) {
					@Override
					protected void postConvert(RewardBo rewardBo, Reward reward) {
					}
				};
			}

			@Override
			protected BeanConvert<RewardBo, Reward> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RewardBo, Reward>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Reward, RewardSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Reward, RewardSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<RewardSimple, Reward> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RewardSimple, Reward>(beanConvertManager) {
					@Override
					public Reward convert(RewardSimple rewardSimple) throws BeansException {
						return rewardDao.getOne(rewardSimple.getId());
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
