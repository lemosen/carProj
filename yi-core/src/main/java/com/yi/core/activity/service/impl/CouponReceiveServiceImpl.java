/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.dao.CouponReceiveDao;
import com.yi.core.activity.domain.bo.CouponBo;
import com.yi.core.activity.domain.bo.CouponReceiveBo;
import com.yi.core.activity.domain.entity.Coupon;
import com.yi.core.activity.domain.entity.CouponGrantConfig;
import com.yi.core.activity.domain.entity.CouponGrantRecord;
import com.yi.core.activity.domain.entity.CouponGrantStep;
import com.yi.core.activity.domain.entity.CouponReceive;
import com.yi.core.activity.domain.entity.CouponReceive_;
import com.yi.core.activity.domain.simple.CouponReceiveSimple;
import com.yi.core.activity.domain.vo.CouponReceiveListVo;
import com.yi.core.activity.domain.vo.CouponReceiveVo;
import com.yi.core.activity.service.ICouponGrantConfigService;
import com.yi.core.activity.service.ICouponGrantRecordService;
import com.yi.core.activity.service.ICouponReceiveService;
import com.yi.core.activity.service.ICouponService;
import com.yi.core.common.Deleted;
import com.yi.core.member.domain.bo.MemberBo;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.domain.entity.MemberLevel;
import com.yi.core.member.service.IMemberService;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.domain.entity.SaleOrderItem;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.date.CalendarUtils;
import com.yihz.common.utils.date.DateUtils;

/**
 * 优惠券领取表
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class CouponReceiveServiceImpl implements ICouponReceiveService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(CouponReceiveServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private CouponReceiveDao couponReceiveDao;

	@Resource
	private IMemberService memberService;

	@Resource
	private ICouponService couponService;

	@Resource
	private ICouponGrantConfigService couponGrantConfigService;

	@Resource
	private ICouponGrantRecordService couponGrantRecordService;

	private EntityListVoBoSimpleConvert<CouponReceive, CouponReceiveBo, CouponReceiveVo, CouponReceiveSimple, CouponReceiveListVo> couponReceiveConvert;

	@Override
	public Page<CouponReceive> query(Pagination<CouponReceive> query) {
		query.setEntityClazz(CouponReceive.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(CouponReceive_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(CouponReceive_.receiveTime)));
		}));
		Page<CouponReceive> page = couponReceiveDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CouponReceiveListVo> queryListVo(Pagination<CouponReceive> query) {
		Page<CouponReceive> pages = this.query(query);
		List<CouponReceiveListVo> vos = couponReceiveConvert.toListVos(pages.getContent());
		return new PageImpl<CouponReceiveListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CouponReceiveListVo> queryListVoForApp(Pagination<CouponReceive> query) {
		query.setEntityClazz(CouponReceive.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(CouponReceive_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(CouponReceive_.receiveTime)));
		}));
		Page<CouponReceive> pages = couponReceiveDao.findAll(query, query.getPageRequest());
		List<CouponReceiveListVo> vos = couponReceiveConvert.toListVos(pages.getContent());
		vos.forEach(tmp -> {
			if (tmp != null && tmp.getEndTime() != null) {
				tmp.setValidDays(CalendarUtils.daysBetween(tmp.getEndTime(), Calendar.getInstance().getTime()));
			}
		});
		return new PageImpl<CouponReceiveListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponReceive getById(int couponReceiveId) {
		if (couponReceiveDao.existsById(couponReceiveId)) {
			return couponReceiveDao.getOne(couponReceiveId);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponReceiveVo getVoById(int couponReceiveId) {
		return couponReceiveConvert.toVo(this.getById(couponReceiveId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponReceiveListVo getListVoById(int couponReceiveId) {
		return couponReceiveConvert.toListVo(this.getById(couponReceiveId));
	}

	@Override
	public CouponReceive addCouponReceive(CouponReceive couponReceive) {
		if (couponReceive == null || couponReceive.getMember() == null || couponReceive.getCoupon() == null) {
			throw new BusinessException("提交数据不能为空");
		}
		return couponReceiveDao.save(couponReceive);
	}

	@Override
	public CouponReceiveListVo addCouponReceive(CouponReceiveBo couponReceiveBo) {
		return couponReceiveConvert.toListVo(couponReceiveDao.save(couponReceiveConvert.toEntity(couponReceiveBo)));
	}

	@Override
	public CouponReceive updateCouponReceive(CouponReceive couponReceive) {
		CouponReceive dbCouponReceive = couponReceiveDao.getOne(couponReceive.getId());
		AttributeReplication.copying(couponReceive, dbCouponReceive, CouponReceive_.used, CouponReceive_.surplus, CouponReceive_.memberPhone, CouponReceive_.startTime,
				CouponReceive_.endTime, CouponReceive_.state, CouponReceive_.useTime, CouponReceive_.saleOrder, CouponReceive_.orderNo);
		return dbCouponReceive;
	}

	@Override
	public CouponReceiveListVo updateCouponReceive(CouponReceiveBo couponReceiveBo) {
		CouponReceive dbCouponReceive = this.updateCouponReceive(couponReceiveConvert.toEntity(couponReceiveBo));
		return couponReceiveConvert.toListVo(dbCouponReceive);
	}

	@Override
	public void removeCouponReceiveById(int couponReceiveId) {
		CouponReceive dbCouponReceive = this.getById(couponReceiveId);
		if (dbCouponReceive != null) {
			dbCouponReceive.setDeleted(Deleted.DEL_TRUE);
			dbCouponReceive.setDelTime(new Date());
		}
	}

	protected void initConvert() {
		this.couponReceiveConvert = new EntityListVoBoSimpleConvert<CouponReceive, CouponReceiveBo, CouponReceiveVo, CouponReceiveSimple, CouponReceiveListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<CouponReceive, CouponReceiveVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponReceive, CouponReceiveVo>(beanConvertManager) {
					@Override
					protected void postConvert(CouponReceiveVo couponReceiveVo, CouponReceive couponReceive) {
					}
				};
			}

			@Override
			protected BeanConvert<CouponReceive, CouponReceiveListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponReceive, CouponReceiveListVo>(beanConvertManager) {
					@Override
					protected void postConvert(CouponReceiveListVo couponReceiveListVo, CouponReceive couponReceive) {

					}
				};
			}

			@Override
			protected BeanConvert<CouponReceive, CouponReceiveBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponReceive, CouponReceiveBo>(beanConvertManager) {
					@Override
					protected void postConvert(CouponReceiveBo couponReceiveBo, CouponReceive couponReceive) {

					}
				};
			}

			@Override
			protected BeanConvert<CouponReceiveBo, CouponReceive> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponReceiveBo, CouponReceive>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<CouponReceive, CouponReceiveSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponReceive, CouponReceiveSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<CouponReceiveSimple, CouponReceive> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponReceiveSimple, CouponReceive>(beanConvertManager) {
					@Override
					public CouponReceive convert(CouponReceiveSimple CouponReceiveSimple) throws BeansException {
						return couponReceiveDao.getOne(CouponReceiveSimple.getId());
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
	 * 领取优惠券
	 */
	@Override
	public CouponReceiveListVo receiveCoupon(Integer couponId, Integer memberId) {
		if (couponId == null || memberId == null) {
			throw new BusinessException("提交数据不能为空");
		}
		Coupon dbCoupon = couponService.getById(couponId);
		Member dbMember = memberService.getMemberById(memberId);
		if (dbCoupon == null || dbMember == null) {
			throw new BusinessException("提交数据不能为空");
		}
		// 检查优惠券领取数量
		if ((dbCoupon.getReceiveQuantity() + 1) > dbCoupon.getQuantity()) {
			throw new BusinessException("哎呀，已经被领完了，下次早点来哦");
		}
		// 有效类型-时间段，检查是否失效
		if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(dbCoupon.getValidType())) {
			if (dbCoupon.getEndTime().before(new Date())) {
				throw new BusinessException("该优惠券已失效，暂不支持领取");
			}
		}
		// 判断会员等级是否符合标准
		boolean levelFlag = this.checkMemberLevel(dbMember.getMemberLevel(), dbCoupon.getMemberLevels());
		if (!levelFlag) {
			throw new BusinessException("该等级暂不支持领取，请您升级会员等级");
		}
		// 检查该会员领取该优惠券的数量 为空的话是不限制领取
		if (dbCoupon.getLimited() > 0) {
			int receivedQuantity = couponReceiveDao.countByCoupon_idAndMember_id(couponId, memberId);
			if ((receivedQuantity + 1) > dbCoupon.getLimited()) {
				throw new BusinessException("不要贪心哦，您已经领取了该优惠券");
			}
		}
		// 领取优惠券
		CouponReceive couponReceive = new CouponReceive();
		couponReceive.setCoupon(dbCoupon);
		couponReceive.setCouponNo(dbCoupon.getCouponNo());
		couponReceive.setCouponType(dbCoupon.getCouponType());
		couponReceive.setParValue(dbCoupon.getParValue());
		couponReceive.setUsed(BigDecimal.ZERO);
		couponReceive.setSurplus(dbCoupon.getParValue());
		couponReceive.setMember(dbMember);
		couponReceive.setMemberPhone(dbMember.getPhone());
		couponReceive.setReceiveMode(ActivityEnum.RECEIVE_MODE_SELF.getCode());
		couponReceive.setReceiveTime(new Date());
		couponReceive.setState(ActivityEnum.COUPON_USE_STATE_NO_USED.getCode());
		// 有效类型 时间段
		if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(dbCoupon.getValidType())) {
			couponReceive.setStartTime(new Date());
			couponReceive.setEndTime(dbCoupon.getEndTime());
			// 有效类型 固定天数
		} else if (ActivityEnum.VALID_TYPE_FIXED_DAY.getCode().equals(dbCoupon.getValidType())) {
			Date startDate = new Date();
			couponReceive.setStartTime(startDate);
			couponReceive.setEndTime(DateUtils.addDays(startDate, dbCoupon.getFixedDay()));
		}
		CouponReceive dbCouponReceive = couponReceiveDao.save(couponReceive);
		// 减去 优惠券领取数量
		// dbCoupon.setReceiveQuantity(dbCoupon.getReceiveQuantity() + 1);
		couponService.updateReceiveQuantity(dbCoupon.getId(), 1);
		return couponReceiveConvert.toListVo(dbCouponReceive);
	}

	/**
	 * 获取会员的优惠券
	 */
	@Override
	public List<CouponReceive> getMemberCoupons(Integer memberId) {
		if (memberId == null) {
			return null;
		}
		Integer[] couponType = new Integer[] { ActivityEnum.COUPON_TYPE_COUPON.getCode() };
		// 当前时间
		Date now = new Date();
		List<CouponReceive> couponReceives = couponReceiveDao.findByMember_idAndCoupon_CouponTypeInAndStateAndDeletedAndStartTimeBeforeAndEndTimeAfterOrderByParValueDesc(memberId,
				couponType, ActivityEnum.COUPON_USE_STATE_NO_USED.getCode(), Deleted.DEL_FALSE, now, now);
		return couponReceives;
	}

	/**
	 * 获取会员的代金券
	 */
	@Override
	public List<CouponReceive> getMemberVouchers(Integer memberId) {
		if (memberId == null) {
			return null;
		}
		Integer[] couponType = new Integer[] { ActivityEnum.COUPON_TYPE_VOUCHER.getCode() };
		// 当前时间
		Date now = new Date();
		List<CouponReceive> couponReceives = couponReceiveDao.findByMember_idAndCoupon_CouponTypeInAndStateAndDeletedAndStartTimeBeforeAndEndTimeAfterOrderByParValueDesc(memberId,
				couponType, ActivityEnum.COUPON_USE_STATE_NO_USED.getCode(), Deleted.DEL_FALSE, now, now);
		return couponReceives;
	}

	/**
	 * 下单使用优惠券
	 */
	@Override
	public void useCouponsByOrder(List<SaleOrder> saleOrders) {
		if (CollectionUtils.isNotEmpty(saleOrders)) {
			for (SaleOrder saleOrder : saleOrders) {
				if (saleOrder != null) {
					if (CollectionUtils.isNotEmpty(saleOrder.getCoupons())) {
						for (CouponReceive couponReceive : saleOrder.getCoupons()) {
							if (ActivityEnum.COUPON_USE_STATE_NO_USED.getCode().equals(couponReceive.getState())) {
								couponReceive.setSaleOrder(saleOrder);
								couponReceive.setOrderNo(saleOrder.getOrderNo());
								couponReceive.setState(ActivityEnum.COUPON_USE_STATE_ALREADY_USED.getCode());
								couponReceive.setUseTime(saleOrder.getCreateTime());
								CouponReceive dbCouponReceive = this.updateCouponReceive(couponReceive);
								// 更新使用数量
								couponService.updateUseQuantity(dbCouponReceive.getCoupon().getId(), 1);
							}
						}
					}
					if (CollectionUtils.isNotEmpty(saleOrder.getVouchers())) {
						for (CouponReceive couponReceive : saleOrder.getVouchers()) {
							if (ActivityEnum.COUPON_USE_STATE_NO_USED.getCode().equals(couponReceive.getState())) {
								couponReceive.setSaleOrder(saleOrder);
								couponReceive.setOrderNo(saleOrder.getOrderNo());
								couponReceive.setState(ActivityEnum.COUPON_USE_STATE_ALREADY_USED.getCode());
								couponReceive.setUseTime(saleOrder.getCreateTime());
								CouponReceive dbCouponReceive = this.updateCouponReceive(couponReceive);
								// 更新使用数量
								couponService.updateUseQuantity(dbCouponReceive.getCoupon().getId(), 1);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 手工发放优惠券
	 */
	@Override
	public void grantCoupon(CouponReceiveBo couponReceiveBo) {
		if (CollectionUtils.isEmpty(couponReceiveBo.getMembers()) || CollectionUtils.isEmpty(couponReceiveBo.getCoupons())) {
			throw new BusinessException("提交数据不能为空");
		}
		List<CouponReceive> saveCouponReceives = new ArrayList<>();
		for (MemberBo member : couponReceiveBo.getMembers()) {
			if (member != null && member.getId() > 0) {
				Member dbMember = memberService.getMemberById(member.getId());
				if (dbMember == null) {
					continue;
				}
				for (CouponBo coupon : couponReceiveBo.getCoupons()) {
					if (coupon != null && coupon.getId() > 0) {
						Coupon dbCoupon = couponService.getById(coupon.getId());
						if (dbCoupon == null) {
							continue;
						}
						// 检查优惠券领取数量
						if ((dbCoupon.getReceiveQuantity() + 1) > dbCoupon.getQuantity()) {
							continue;
						}
						// 有效类型-时间段，检查是否失效
						if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(dbCoupon.getValidType())) {
							if (dbCoupon.getEndTime().before(new Date())) {
								continue;
							}
						}
						// 判断会员等级是否符合标准
						boolean levelFlag = checkMemberLevel(dbMember.getMemberLevel(), dbCoupon.getMemberLevels());
						if (!levelFlag) {
							continue;
						}
						// 检查该会员领取该优惠券的数量 为空的话是不限制领取
						if (dbCoupon.getLimited() > 0) {
							int receivedQuantity = couponReceiveDao.countByCoupon_idAndMember_id(dbCoupon.getId(), dbMember.getId());
							if ((receivedQuantity + 1) > dbCoupon.getLimited()) {
								continue;
							}
						}
						// 领取优惠券
						CouponReceive couponReceive = new CouponReceive();
						couponReceive.setCoupon(dbCoupon);
						couponReceive.setCouponNo(dbCoupon.getCouponNo());
						couponReceive.setCouponType(dbCoupon.getCouponType());
						couponReceive.setParValue(dbCoupon.getParValue());
						couponReceive.setUsed(BigDecimal.ZERO);
						couponReceive.setSurplus(dbCoupon.getParValue());
						couponReceive.setMember(dbMember);
						couponReceive.setMemberPhone(dbMember.getPhone());
						couponReceive.setReceiveMode(ActivityEnum.RECEIVE_MODE_MANUAL.getCode());
						couponReceive.setReceiveTime(new Date());
						couponReceive.setState(ActivityEnum.COUPON_USE_STATE_NO_USED.getCode());
						// 有效类型 时间段
						if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(dbCoupon.getValidType())) {
							couponReceive.setStartTime(new Date());
							couponReceive.setEndTime(dbCoupon.getEndTime());

						} // 有效类型 固定天数
						else if (ActivityEnum.VALID_TYPE_FIXED_DAY.getCode().equals(dbCoupon.getValidType())) {
							Date startDate = new Date();
							couponReceive.setStartTime(startDate);
							couponReceive.setEndTime(DateUtils.addDays(startDate, dbCoupon.getFixedDay()));
						}
						saveCouponReceives.add(couponReceive);
						// 减去 优惠券领取数量
						couponService.updateReceiveQuantity(dbCoupon.getId(), 1);
					}
				}
			}
		}
		couponReceiveDao.saveAll(saveCouponReceives);
	}

	/**
	 * 分步发放代金券
	 */
	@Override
	public void grantVoucherByStep(Member member, SaleOrder saleOrder, ActivityEnum grantStep) {
		if (member == null || saleOrder == null || CollectionUtils.isEmpty(saleOrder.getSaleOrderItems()) || grantStep == null) {
			LOG.error("分步发放优惠券时，参数为空");
			return;
		}
		for (SaleOrderItem tmpItem : saleOrder.getSaleOrderItems()) {
			if (tmpItem != null && tmpItem.getCommodity() != null && tmpItem.getCommodity().getCouponGrantConfig() != null
					&& tmpItem.getCommodity().getCouponGrantConfig().getCoupon() != null) {
				// 检查会员等级是否符合要求
				boolean levelFlag = this.checkMemberLevel(member.getMemberLevel(), tmpItem.getCommodity().getCouponGrantConfig().getCoupon().getMemberLevels());
				if (levelFlag) {
					CouponGrantConfig tmpGrantConfig = tmpItem.getCommodity().getCouponGrantConfig();
					Coupon tmpCoupon = tmpGrantConfig.getCoupon();
					// 购买商品数量
					for (int i = 0; i < tmpItem.getQuantity(); i++) {
						// 发放策略-一次性发放
						if (ActivityEnum.GRANT_STRATEGY_ONCE.getCode().equals(tmpGrantConfig.getGrantStrategy())) {
							if (grantStep.getCode().equals(tmpGrantConfig.getGrantNode())) {
								// 检查该步骤是否发放过该优惠券
								boolean grantFlag = couponGrantRecordService.checkGrantForOnce(grantStep, member, saleOrder, tmpGrantConfig, tmpItem.getQuantity());
								if (!grantFlag) {
									// 有效类型 时间段-检查是否失效
									if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(tmpCoupon.getValidType())) {
										if (tmpCoupon.getEndTime().before(new Date())) {
											continue;
										}
									}
									// 检查该会员领取该优惠券的数量 为空的话是不限制领取
									if (tmpCoupon.getLimited() > 0) {
										// 统计会员已经领取的数量
										int receivedQuantity = couponReceiveDao.countByCoupon_idAndMember_id(tmpCoupon.getId(), member.getId());
										if ((receivedQuantity + 1) > tmpCoupon.getLimited()) {
											continue;
										}
									}
									// 发放面额
									BigDecimal parValue = tmpGrantConfig.getCoupon().getParValue();
									// 发放优惠券并保存
									CouponGrantRecord dbCouponGrantRecord = couponGrantRecordService.addByGrantStepForOnce(grantStep, member, saleOrder, tmpGrantConfig, parValue);
									// 将发放的代金券的同步到优惠券领取表
									this.addByGrantVoucherForOnce(tmpCoupon, member, dbCouponGrantRecord, parValue);
								}
							}
						} // 发放策略-分批发放
						else if (ActivityEnum.GRANT_STRATEGY_STEP.getCode().equals(tmpGrantConfig.getGrantStrategy())) {
							// 根据发放步骤 发放优惠券
							for (CouponGrantStep tmpStep : tmpItem.getCommodity().getCouponGrantConfig().getCouponGrantSteps()) {
								if (grantStep.getCode().equals(tmpStep.getGrantNode())) {
									// 检查该步骤是否发放过该优惠券
									boolean grantFlag = couponGrantRecordService.checkGrantForStep(grantStep, member, saleOrder, tmpGrantConfig, tmpItem.getQuantity());
									if (!grantFlag) {
										// 有效类型 时间段-检查是否失效
										if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(tmpCoupon.getValidType())) {
											if (tmpCoupon.getEndTime().before(new Date())) {
												continue;
											}
										}
										// 检查该会员领取该优惠券的数量 为空的话是不限制领取
										if (tmpCoupon.getLimited() > 0) {
											// 统计会员已经领取的数量
											int receivedQuantity = couponReceiveDao.countByCoupon_idAndMember_id(tmpCoupon.getId(), member.getId());
											if ((receivedQuantity + 1) > tmpCoupon.getLimited()) {
												continue;
											}
										}
										// 是否第一次发放
										boolean firstGrant = couponGrantRecordService.checkFirstGrant(member, saleOrder, tmpGrantConfig, tmpItem.getQuantity());
										// 发放面额
										BigDecimal parValue = tmpStep.getGrantRate().multiply(tmpGrantConfig.getCoupon().getParValue()).divide(BigDecimal.valueOf(100)).setScale(2,
												BigDecimal.ROUND_UP);
										if (parValue.compareTo(BigDecimal.ZERO) > 0) {
											// 发放优惠券并保存
											CouponGrantRecord dbCouponGrantRecord = couponGrantRecordService.addByGrantStepForStep(grantStep, member, saleOrder, tmpGrantConfig,
													parValue);
											// 将发放的代金券同步到优惠券领取表
											this.addByGrantVoucherForStep(tmpCoupon, member, dbCouponGrantRecord, parValue, firstGrant);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 退款时 收回已经发放的代金券
	 */
	@Override
	public void returnVoucherByRefund(Member member, SaleOrder saleOrder) {
		if (member != null && saleOrder != null) {
			// 查询已经发放的记录
			List<CouponGrantRecord> dbGrantRecords = couponGrantRecordService.getGrantRecordsByMemberAndOrder(member, saleOrder);
			if (CollectionUtils.isNotEmpty(dbGrantRecords)) {
				List<Integer> grantRecordIds = dbGrantRecords.stream().map(e -> e.getId()).collect(Collectors.toList());
				List<CouponReceive> dbCouponReceives = couponReceiveDao.findByCouponGrantRecord_idInAndDeleted(grantRecordIds, Deleted.DEL_FALSE);
				if (CollectionUtils.isNotEmpty(dbCouponReceives)) {
					dbCouponReceives.forEach(tmpCouponReceive -> {
						if (tmpCouponReceive != null && ActivityEnum.COUPON_USE_STATE_NO_USED.getCode().equals(tmpCouponReceive.getState())) {
							tmpCouponReceive.setState(ActivityEnum.COUPON_USE_STATE_REGAIN.getCode());
						}
					});
				}
			}
		}
	}

	@Transactional
	public CouponReceive addByGrantVoucherForOnce(Coupon coupon, Member member, CouponGrantRecord couponGrantRecord, BigDecimal parValue) {
		if (coupon == null || member == null || couponGrantRecord == null) {
			return null;
		}
		// 有效类型-时间段，检查是否失效
		if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(coupon.getValidType())) {
			if (coupon.getEndTime().before(new Date())) {
				return null;
			}
		}
		// 检查优惠券领取数量
		if ((coupon.getReceiveQuantity() + 1) > coupon.getQuantity()) {
			return null;
		}
		// 检查该会员领取该优惠券的数量 为空的话是不限制领取
		if (coupon.getLimited() > 0) {
			// 统计会员已经领取的数量
			int receivedQuantity = couponReceiveDao.countByCoupon_idAndMember_id(coupon.getId(), member.getId());
			if ((receivedQuantity + 1) > coupon.getLimited()) {
				return null;
			}
		}
		// 领取优惠券
		CouponReceive couponReceive = new CouponReceive();
		couponReceive.setCoupon(coupon);
		couponReceive.setCouponNo(coupon.getCouponNo());
		couponReceive.setCouponType(coupon.getCouponType());
		couponReceive.setParValue(parValue);
		couponReceive.setUsed(BigDecimal.ZERO);
		couponReceive.setSurplus(parValue);
		couponReceive.setMember(member);
		couponReceive.setMemberPhone(member.getPhone());
		couponReceive.setCouponGrantRecord(couponGrantRecord);
		couponReceive.setReceiveMode(ActivityEnum.RECEIVE_MODE_GIFT.getCode());
		couponReceive.setReceiveTime(new Date());
		couponReceive.setState(ActivityEnum.COUPON_USE_STATE_NO_USED.getCode());
		// 有效类型 时间段
		if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(coupon.getValidType())) {
			couponReceive.setStartTime(new Date());
			couponReceive.setEndTime(coupon.getEndTime());
			// 有效类型 固定天数
		} else if (ActivityEnum.VALID_TYPE_FIXED_DAY.getCode().equals(coupon.getValidType())) {
			Date startDate = new Date();
			couponReceive.setStartTime(startDate);
			couponReceive.setEndTime(DateUtils.addDays(startDate, coupon.getFixedDay()));
		}
		// 保存数据
		CouponReceive dbCouponReceive = couponReceiveDao.save(couponReceive);
		// 减去 优惠券领取数量
		// coupon.setReceiveQuantity(coupon.getReceiveQuantity() + 1);
		couponService.updateReceiveQuantity(coupon.getId(), 1);
		return dbCouponReceive;
	}

	@Transactional
	public CouponReceive addByGrantVoucherForStep(Coupon coupon, Member member, CouponGrantRecord couponGrantRecord, BigDecimal parValue, boolean firstGrant) {
		if (coupon != null && member != null && couponGrantRecord != null) {
			// 有效类型-时间段，检查是否失效
			if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(coupon.getValidType())) {
				if (coupon.getEndTime().before(new Date())) {
					return null;
				}
			}
			if (firstGrant) {
				// 检查优惠券领取数量
				if ((coupon.getReceiveQuantity() + 1) > coupon.getQuantity()) {
					return null;
				}
				// 检查该会员领取该优惠券的数量 为空的话是不限制领取
				if (coupon.getLimited() > 0) {
					// 统计会员已经领取的数量
					int receivedQuantity = couponReceiveDao.countByCoupon_idAndMember_id(coupon.getId(), member.getId());
					if ((receivedQuantity + 1) > coupon.getLimited()) {
						return null;
					}
				}
			}
			// 领取优惠券
			CouponReceive couponReceive = new CouponReceive();
			couponReceive.setCoupon(coupon);
			couponReceive.setCouponNo(coupon.getCouponNo());
			couponReceive.setCouponType(coupon.getCouponType());
			couponReceive.setParValue(parValue);
			couponReceive.setUsed(BigDecimal.ZERO);
			couponReceive.setSurplus(parValue);
			couponReceive.setMember(member);
			couponReceive.setMemberPhone(member.getPhone());
			couponReceive.setCouponGrantRecord(couponGrantRecord);
			couponReceive.setReceiveMode(ActivityEnum.RECEIVE_MODE_GIFT.getCode());
			couponReceive.setReceiveTime(new Date());
			couponReceive.setState(ActivityEnum.COUPON_USE_STATE_NO_USED.getCode());
			// 有效类型 时间段
			if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(coupon.getValidType())) {
				couponReceive.setStartTime(new Date());
				couponReceive.setEndTime(coupon.getEndTime());
				// 有效类型 固定天数
			} else if (ActivityEnum.VALID_TYPE_FIXED_DAY.getCode().equals(coupon.getValidType())) {
				Date startDate = new Date();
				couponReceive.setStartTime(startDate);
				couponReceive.setEndTime(DateUtils.addDays(startDate, coupon.getFixedDay()));
			}
			// 保存数据
			CouponReceive dbCouponReceive = couponReceiveDao.save(couponReceive);
			if (firstGrant) {
				// 减去 优惠券领取数量
				// coupon.setReceiveQuantity(coupon.getReceiveQuantity() + 1);
				couponService.updateReceiveQuantity(coupon.getId(), 1);
			}
			return dbCouponReceive;
		}
		return null;
	}

	/**
	 * 核验 会员等级是否符合
	 * 
	 * @param memberLevel
	 * @param memberLevels
	 * @return
	 */
	private boolean checkMemberLevel(MemberLevel memberLevel, Collection<MemberLevel> memberLevels) {
		if (memberLevel == null || CollectionUtils.isEmpty(memberLevels)) {
			return false;
		}
		for (MemberLevel tmpLevel : memberLevels) {
			if (tmpLevel != null && tmpLevel.getId() == memberLevel.getId()) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CouponReceiveListVo> getCouponTypeAvailable(int memberId, int couponType) {
		return couponReceiveConvert
				.toListVos(couponReceiveDao.findBycouponTypeAndMemberId(couponType, memberId, new Date(), ActivityEnum.COUPON_USE_STATE_NO_USED.getCode(), Deleted.DEL_FALSE));
	}

	/**
	 * 该优惠券所有情况
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int countByCouponId(int couponId) {
		return couponReceiveDao.countByCouponId(couponId);
	}

	@Override
	public void autoCancelCouponByTask() {
		List<CouponReceive> couponReceives = couponReceiveDao.findByStateAndEndTimeLessThanAndDeleted(ActivityEnum.COUPON_USE_STATE_NO_USED.getCode(), new Date(),
				Deleted.DEL_FALSE);
		if (CollectionUtils.isEmpty(couponReceives)) {
			LOG.info("没有需要执行失效的优惠券");
			return;
		}
		LOG.info("需要执行失效的优惠券有{}个", couponReceives.size());
		couponReceives.forEach(tmp -> {
			if (tmp.getEndTime().before(new Date())) {
				tmp.setState(ActivityEnum.COUPON_USE_STATE_INVALID.getCode());
			}
		});
	}

}
