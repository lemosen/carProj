/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.vdurmont.emoji.EmojiParser;
import com.yi.core.activity.dao.CouponDao;
import com.yi.core.activity.dao.CouponReceiveDao;
import com.yi.core.basic.BasicEnum;
import com.yi.core.basic.dao.CommunityDao;
import com.yi.core.basic.domain.entity.Community;
import com.yi.core.basic.service.IIntegralRecordService;
import com.yi.core.basic.service.IIntegralTaskService;
import com.yi.core.commodity.CommodityEnum;
import com.yi.core.common.Deleted;
import com.yi.core.member.MemberEnum;
import com.yi.core.member.dao.AccountRecordDao;
import com.yi.core.member.dao.MemberDao;
import com.yi.core.member.domain.bo.MemberBo;
import com.yi.core.member.domain.entity.Account;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.domain.entity.MemberCommission;
import com.yi.core.member.domain.entity.MemberLevel;
import com.yi.core.member.domain.entity.Member_;
import com.yi.core.member.domain.simple.MemberSimple;
import com.yi.core.member.domain.vo.MemberLevelListVo;
import com.yi.core.member.domain.vo.MemberListVo;
import com.yi.core.member.domain.vo.MemberVo;
import com.yi.core.member.service.IAccountRecordService;
import com.yi.core.member.service.IAccountService;
import com.yi.core.member.service.IDistributionLevelService;
import com.yi.core.member.service.IMemberCommissionService;
import com.yi.core.member.service.IMemberLevelService;
import com.yi.core.member.service.IMemberService;
import com.yi.core.order.OrderEnum;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.domain.entity.SaleOrderItem;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 会员
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class MemberServiceImpl implements IMemberService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(MemberServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private MemberDao memberDao;

	@Resource
	private CommunityDao communityDao;

	@Resource
	private CouponReceiveDao couponReceiveDao;

	@Resource
	private CouponDao couponDao;

	@Resource
	private AccountRecordDao accountRecordDao;

	@Resource
	private IIntegralTaskService integralTaskService;

	@Resource
	private IIntegralRecordService integralRecordService;

	@Resource
	private IMemberLevelService memberLevelService;

	@Resource
	private IAccountService accountService;

	@Resource
	private IAccountRecordService accountRecordService;

	@Resource
	private IDistributionLevelService distributionLevelService;

	@Resource
	private IMemberCommissionService memberCommissionService;

	private EntityListVoBoSimpleConvert<Member, MemberBo, MemberVo, MemberSimple, MemberListVo> memberConvert;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Member getMemberById(Integer memberId) {
		if (memberDao.existsById(memberId)) {
			return memberDao.getOne(memberId);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MemberVo getMemberVoById(Integer memberId) {
		Member dbMember = this.getMemberById(memberId);
		if (dbMember != null) {
			MemberVo memberVo = memberConvert.toVo(dbMember);
			if (memberVo.getParent() != null) {
				memberVo.setParentName(memberVo.getParent().getUsername());
			} else {
				memberVo.setParentName(null);
			}
			return memberVo;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MemberVo getMemberVoByIdForApp(Integer memberId) {
		Member dbMember = this.getMemberById(memberId);
		if (dbMember != null && Deleted.DEL_FALSE.equals(dbMember.getDeleted())) {
			MemberVo memberVo = memberConvert.toVo(dbMember);
			if (memberVo.getParent() != null) {
				memberVo.setParentName(memberVo.getParent().getUsername());
			} else {
				memberVo.setParentName(null);
			}
			return memberVo;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MemberListVo getMemberListVoById(Integer memberId) {
		return memberConvert.toListVo(this.getMemberById(memberId));
	}

	/**
	 * 注册会员
	 */
	@Override
	public MemberVo addMember(Member member) {
		if (member == null) {
			throw new BusinessException("提交数据不能为空");
		}
		List<Member> all = memberDao.findByUsername(member.getUsername());
		if (all.size() > 0) {
			throw new BusinessException("该账户已被注册");
		}
		return memberConvert.toVo(memberDao.save(member));
	}

	@Override
	public MemberListVo addMember(MemberBo memberBo) {
		return memberConvert.toListVo(memberDao.save(memberConvert.toEntity(memberBo)));
	}

	@Override
	public MemberVo updateMember(Member member) {

		Member dbMember = memberDao.getOne(member.getId());
		AttributeReplication.copying(member, dbMember, Member_.memberLevel, Member_.community, Member_.vip, Member_.province, Member_.city, Member_.district, Member_.address);
		return memberConvert.toVo(dbMember);
	}

	@Override
	public MemberListVo updateMember(MemberBo memberBo) {
		Member dbMember = memberDao.getOne(memberBo.getId());
		AttributeReplication.copying(memberBo, dbMember, Member_.username, Member_.password, Member_.nickname, Member_.memberType, Member_.vip, Member_.province, Member_.city,
				Member_.district, Member_.address, Member_.birthday);
		return memberConvert.toListVo(dbMember);
	}

	@Override
	public void removeMemberById(int memberId) {
		Member dbMember = memberDao.getOne(memberId);
		if (dbMember != null) {
			dbMember.setDeleted(Deleted.DEL_TRUE);
			dbMember.setDelTime(new Date());
		}
	}

	@Override
	public Page<Member> query(Pagination<Member> query) {
		query.setEntityClazz(Member.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Member_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(Member_.createTime)));
			/* 后期发放优惠券会员筛选，不要删掉 */
			/*
			 * Object couponId=query.getParams().get("couponId");
			 * System.out.println("++"+couponId==null);
			 * System.out.println("=="+couponId==""); if(couponId!=null) {
			 *//*
				 * List<CouponReceive> couponReceives = couponReceiveDao.findByCouponId((int)
				 * couponId);
				 *//*
					 * List<CouponReceive>
					 * couponReceives=couponReceiveDao.findAll().stream().filter(e->
					 * e.getCoupon().getId()=(int)couponId ); }
					 */
		}));
		Page<Member> page = memberDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<MemberListVo> queryListVo(Pagination<Member> query) {
		Page<Member> pages = this.query(query);
		List<MemberListVo> vos = new ArrayList<>();
		for (Member member : pages.getContent()) {
			if (member != null) {
				MemberListVo memberListVo = memberConvert.toListVo(member);
				memberListVo.setPromotionNum(Optional.ofNullable(member.getChildren()).map(e -> e.size()).orElse(0));
				vos.add(memberListVo);
			}
		}
		return new PageImpl<MemberListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 查询我的团队
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<MemberListVo> queryMyTeam(Pagination<Member> query, Integer teamType) {
		query.setEntityClazz(Member.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, predicates, orders) -> {
			predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Member_.deleted), Deleted.DEL_FALSE)));
			// orders.add(criteriaBuilder.desc(root.get(Member_.createTime)));
			Object memberId = query.getParams().get("member.id");
			if (memberId != null) {
				// 查询一级团队
				if (MemberEnum.DISTRIBUTION_LEVEL_FIRST.getCode().equals(teamType)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Member_.parent).get(Member_.id), memberId)));
					// 查询二级团队
				} else if (MemberEnum.DISTRIBUTION_LEVEL_SECOND.getCode().equals(teamType)) {
					Member dbMember = this.getMemberById((Integer) memberId);
					if (dbMember != null) {
						Path<Integer> path = root.get(Member_.parent).get(Member_.id);
						CriteriaBuilder.In<Integer> in = criteriaBuilder.in(path);
						if (CollectionUtils.isNotEmpty(dbMember.getChildren())) {
							dbMember.getChildren().forEach(e -> {
								in.value(e.getId());
							});
						} else {
							in.value(0);
						}
						predicates.add(criteriaBuilder.and(in));
					}
					// 不查询数据
				} else {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Member_.id), null)));
				}
			} else {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Member_.id), null)));
			}
		}));
		Page<Member> pages = memberDao.findAll(query, query.getPageRequest());
		List<MemberListVo> vos = new ArrayList<>();
		for (Member member : pages.getContent()) {
			if (member != null) {
				MemberListVo memberListVo = memberConvert.toListVo(member);
				memberListVo.setPromotionNum(Optional.ofNullable(member.getChildren()).map(e -> e.size()).orElse(0));
				vos.add(memberListVo);
			}
		}
		return new PageImpl<MemberListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	protected void initConvert() {
		this.memberConvert = new EntityListVoBoSimpleConvert<Member, MemberBo, MemberVo, MemberSimple, MemberListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Member, MemberVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Member, MemberVo>(beanConvertManager) {
					@Override
					protected void postConvert(MemberVo memberVo, Member member) {

					}
				};
			}

			@Override
			protected BeanConvert<Member, MemberListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Member, MemberListVo>(beanConvertManager) {
					@Override
					protected void postConvert(MemberListVo memberListVo, Member member) {

					}
				};
			}

			@Override
			protected BeanConvert<Member, MemberBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Member, MemberBo>(beanConvertManager) {
					@Override
					protected void postConvert(MemberBo memberBo, Member member) {

					}
				};
			}

			@Override
			protected BeanConvert<MemberBo, Member> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MemberBo, Member>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Member, MemberSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Member, MemberSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<MemberSimple, Member> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MemberSimple, Member>(beanConvertManager) {
					@Override
					public Member convert(MemberSimple MemberSimple) throws BeansException {
						return memberDao.getOne(MemberSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}

	@Override
	public MemberVo login(MemberBo memberBo) {
		if (StringUtils.isAnyBlank(memberBo.getPhone(), memberBo.getPassword())) {
			throw new BusinessException("手机号或密码不能为空");
		}
		Member dbMember = memberDao.findByPasswordAndPhoneAndDeleted(memberBo.getPassword(), memberBo.getPhone(), Deleted.DEL_FALSE);
		if (dbMember == null) {
			throw new BusinessException("手机号或密码错误");
		}
		if (MemberEnum.STATE_DISABLE.getCode().equals(dbMember.getState())) {
			throw new BusinessException("您已被禁用，请联系客服处理");
		}
		return memberConvert.toVo(dbMember);
	}

	/**
	 * 通过手机号和验证码登录
	 */
	@Override
	public MemberVo loginBySms(MemberBo memberBo) {
		Member dbMember = memberDao.findByPhoneAndDeleted(memberBo.getPhone(), Deleted.DEL_FALSE);
		if (dbMember == null) {
			memberBo.setPassword("123456");
			// 如果未注册 自动注册
			return this.registerByWeChat(memberBo);
		}
		if (MemberEnum.STATE_DISABLE.getCode().equals(dbMember.getState())) {
			throw new BusinessException("您已被禁用，请联系客服处理");
		}
		return memberConvert.toVo(dbMember);
	}

	@Override
	public MemberVo changePhone(MemberBo memberBo) {
		Member member = memberDao.getOne(memberBo.getId());
		member.setPhone(memberBo.getPhone());
		member.setUsername(memberBo.getPhone());
		return memberConvert.toVo(member);
	}

	@Override
	public MemberVo changeMember(MemberBo memberBo) {
		Member dbMember = memberDao.getOne(memberBo.getId());
		AttributeReplication.copying(memberConvert.toEntity(memberBo), dbMember, Member_.avater, Member_.nickname, Member_.province, Member_.city, Member_.district,
				Member_.address, Member_.birthday, Member_.sex);
		return memberConvert.toVo(memberDao.save(dbMember));
	}

	@Override
	public MemberVo changePwd(MemberBo memberBo) {
		Member member = memberDao.getOne(memberBo.getId());
		if (MemberEnum.STATE_DISABLE.getCode().equals(member.getState())) {
			throw new BusinessException("您已被禁用，请联系客服处理");
		}
		member.setPassword(memberBo.getPassword());
		return memberConvert.toVo(member);

	}

	@Override
	public MemberVo forgetPassword(MemberBo memberBo) {
		Member member = memberDao.findByPhoneAndDeleted(memberBo.getPhone(), Deleted.DEL_FALSE);
		if (member == null) {
			throw new BusinessException("该账号还未注册，请注册");
		}
		if (MemberEnum.STATE_DISABLE.getCode().equals(member.getState())) {
			throw new BusinessException("您已被禁用，请联系客服处理");
		}
		member.setPassword(memberBo.getPassword());
		return memberConvert.toVo(member);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getMyTeamNum(int memberId) {
		int myTeamNum = 0;
		List<Member> myTeam = memberDao.findByParent_IdAndDeleted(memberId, Deleted.DEL_FALSE);
		if (CollectionUtils.isNotEmpty(myTeam)) {
			myTeamNum += myTeam.size();
			for (Member tmp : myTeam) {
				if (tmp != null && CollectionUtils.isNotEmpty(tmp.getChildren())) {
					myTeamNum += tmp.getChildren().size();
				}
			}
		}
		return myTeamNum;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MemberVo getBalance(int memberId) {
		return memberConvert.toVo(memberDao.getOne(memberId));
	}

	@Override
	public MemberVo updateState(int memberId) {
		Member member = memberDao.getOne(memberId);
		Integer state = member.getState();
		if (MemberEnum.STATE_DISABLE.getCode().equals(state)) {
			member.setState(MemberEnum.STATE_ENABLE.getCode());
		} else if (MemberEnum.STATE_ENABLE.getCode().equals(state)) {
			member.setState(MemberEnum.STATE_DISABLE.getCode());
		}
		return memberConvert.toVo(member);
	}

	/**
	 * 修改支付密码
	 *
	 * @param memberBo
	 * @return
	 */
	@Override
	public MemberVo modifyPayPassword(MemberBo memberBo) {
		Member member = memberDao.getOne(memberBo.getId());
		if (member.getPayPassword() != null) {
			if (!member.getPayPassword().equalsIgnoreCase(memberBo.getPayPassword())) {
				throw new BusinessException("密码错误，请重新输入");
			}
			if (member.getPayPassword().equalsIgnoreCase(memberBo.getNewPayPassword())) {
				throw new BusinessException("新密码不能和旧密码一样");
			}
		}

		member.setPayPassword(memberBo.getNewPayPassword());
		return memberConvert.toVo(member);
	}

	@Override
	public MemberVo updataVipNo(int memberId) {
		Member member = memberDao.getOne(memberId);
		member.setVip(MemberEnum.VIP_NO.getCode());
		return memberConvert.toVo(member);
	}

	@Override
	public MemberVo updataVipYes(int memberId) {
		Member member = memberDao.getOne(memberId);
		member.setVip(MemberEnum.VIP_YES.getCode());
		return memberConvert.toVo(member);
	}

	@Override
	public MemberListVo updataCommunity(int communityId, int memberId) {
		Community community = communityDao.getOne(communityId);
		Member member = memberDao.getOne(memberId);
		member.setCommunity(community);
		member.setAddress(community.getAddress());
		return memberConvert.toListVo(member);
	}

	/**
	 * 修改手机号
	 *
	 * @param memberBo
	 * @return
	 */
	@Override
	public MemberVo updataPhone(MemberBo memberBo) {
		Member member = memberDao.getOne(memberBo.getId());
		member.setPhone(memberBo.getPhone());
		return memberConvert.toVo(member);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getMemberNum() {
		return memberDao.countByDeleted(Deleted.DEL_FALSE);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Object[]> getDailyAddNumByDate(Date startDate, Date endDate) {
		return memberDao.findDailyAddNumByDate(Deleted.DEL_FALSE, startDate, endDate);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Member checkMemberByWeChat(String unionId) {
		if (StringUtils.isAnyBlank(unionId)) {
			return null;
		}
		return memberDao.findByUnionIdAndDeleted(unionId, Deleted.DEL_FALSE);
	}

	/**
	 * 服务号授权登录
	 */
	@Override
	public Member checkMemberByWeChatForSp(String unionId, String openId, String avater, String nickname) {
		if (StringUtils.isAnyBlank(unionId)) {
			return null;
		}
		Member dbMember = memberDao.findByUnionIdAndDeleted(unionId, Deleted.DEL_FALSE);
		if (dbMember != null) {
			if (StringUtils.isNotBlank(avater) && StringUtils.isBlank(dbMember.getAvater())) {
				dbMember.setAvater(avater);
			}
			if (StringUtils.isNotBlank(nickname) && StringUtils.isBlank(dbMember.getNickname())) {
				dbMember.setNickname(nickname);
			}
			if (StringUtils.isNotBlank(openId) && StringUtils.isBlank(dbMember.getOpenId())) {
				dbMember.setOpenId(openId);
			}
		}
		return dbMember;
	}

	/**
	 * 小程序授权登录
	 */
	@Override
	public Object checkMemberByWeChatForMp(JSONObject userInfo, Integer parentId) {
		if (userInfo == null || StringUtils.isAnyBlank(userInfo.getString("unionId"), userInfo.getString("openId"))) {
			return null;
		}
		Member dbMember = memberDao.findByUnionIdAndDeleted(userInfo.getString("unionId"), Deleted.DEL_FALSE);
		if (dbMember != null) {
			if (StringUtils.isNotBlank(userInfo.getString("avatarUrl")) && StringUtils.isBlank(dbMember.getAvater())) {
				dbMember.setAvater(userInfo.getString("avatarUrl"));
			}
			if (StringUtils.isNotBlank(userInfo.getString("nickName")) && StringUtils.isBlank(dbMember.getNickname())) {
				dbMember.setNickname(EmojiParser.removeAllEmojis(userInfo.getString("nickName")));
			}
			if (StringUtils.isNotBlank(userInfo.getString("openId")) && StringUtils.isBlank(dbMember.getMpOpenId())) {
				dbMember.setMpOpenId(userInfo.getString("openId"));
			}
		} else {
			// 如果没有注册 先自动保存用户数据
			Member parent = this.getMemberById(parentId);
			Member member = new Member();
			member.setPassword("123456");
			member.setMpOpenId(userInfo.getString("openId"));
			member.setUnionId(userInfo.getString("unionId"));
			if (parent != null) {
				member.setParent(parent);
			}
			member.setNickname(EmojiParser.removeAllEmojis(userInfo.getString("nickName")));
			member.setAvater(userInfo.getString("avatarUrl"));
			member.setMemberType(MemberEnum.MEMBER_TYPE_ORDINARY.getCode());
			member.setMemberLevel(memberLevelService.getDefaultMemberLevel());
			// 保存会员
			dbMember = memberDao.save(member);
			// 保存会员账户
			Account account = new Account();
			account.setMember(dbMember);
			account.setBalance(BigDecimal.ZERO);
			account.setConsumeAmount(BigDecimal.ZERO);
			account.setFreezeAmount(BigDecimal.ZERO);
			Account dbAccount = accountService.addAccount(account);
			// 修改会员账户
			member.setAccount(dbAccount);
			// 计算邀请人积分
			integralRecordService.addIntegralRecordByTaskType(parent, BasicEnum.TASK_TYPE_INVITE);
		}
		userInfo.put("memberId", dbMember.getId());
		userInfo.put("isLogin", Boolean.TRUE);
		userInfo.put("phone", dbMember.getPhone());
		return userInfo;
	}

	// /**
	// * 小程序授权登录
	// */
	// @Override
	// public Member checkMemberByWeChatForMp(String unionId, String openId, String
	// avater, String nickname) {
	// if (StringUtils.isAnyBlank(unionId)) {
	// return null;
	// }
	// Member dbMember = memberDao.findByUnionIdAndDeleted(unionId,
	// Deleted.DEL_FALSE);
	// if (dbMember != null) {
	// if (StringUtils.isNotBlank(avater) &&
	// StringUtils.isBlank(dbMember.getAvater())) {
	// dbMember.setAvater(avater);
	// }
	// if (StringUtils.isNotBlank(nickname) &&
	// StringUtils.isBlank(dbMember.getNickname())) {
	// dbMember.setNickname(nickname);
	// }
	// if (StringUtils.isNotBlank(openId) &&
	// StringUtils.isBlank(dbMember.getMpOpenId())) {
	// dbMember.setMpOpenId(openId);
	// }
	// } else {
	// // 如果没有注册 先自动保存用户数据
	//
	// }
	// return dbMember;
	// }

	/**
	 * APP授权登录
	 */
	@Override
	public Member checkMemberByWeChatForApp(String unionId, String openId, String avater, String nickname) {
		if (StringUtils.isAnyBlank(unionId)) {
			return null;
		}
		Member dbMember = memberDao.findByUnionIdAndDeleted(unionId, Deleted.DEL_FALSE);
		if (dbMember != null) {
			if (StringUtils.isNotBlank(avater) && StringUtils.isBlank(dbMember.getAvater())) {
				dbMember.setAvater(avater);
			}
			if (StringUtils.isNotBlank(nickname) && StringUtils.isBlank(dbMember.getNickname())) {
				dbMember.setNickname(nickname);
			}
			if (StringUtils.isNotBlank(openId) && StringUtils.isBlank(dbMember.getAppOpenId())) {
				dbMember.setAppOpenId(openId);
			}
		}
		return dbMember;
	}

	@Override
	public MemberVo register(MemberBo memberBo) {
		int checkPhone = memberDao.countByPhoneAndDeleted(memberBo.getPhone(), Deleted.DEL_FALSE);
		if (checkPhone > 0) {
			throw new BusinessException("该账户已被注册");
		}
		Member parent = this.getMemberById(memberBo.getParentId());
		Member member = new Member();
		member.setPhone(memberBo.getPhone());
		if (parent != null) {
			member.setParent(parent);
		}
		member.setUsername(memberBo.getPhone());
		member.setPassword(memberBo.getPassword());
		if (StringUtils.isNotBlank(memberBo.getNickname())) {
			member.setNickname(EmojiParser.removeAllEmojis(memberBo.getNickname()));
		} else {
			member.setNickname(memberBo.getPhone().substring(8) + "会员");
		}
		member.setMemberType(MemberEnum.MEMBER_TYPE_ORDINARY.getCode());
		member.setMemberLevel(memberLevelService.getDefaultMemberLevel());
		// 保存会员
		Member dbMember = memberDao.save(member);
		// 保存会员资金账户
		Account account = new Account();
		account.setMember(dbMember);
		account.setBalance(BigDecimal.ZERO);
		account.setConsumeAmount(BigDecimal.ZERO);
		account.setFreezeAmount(BigDecimal.ZERO);
		Account dbAccount = accountService.addAccount(account);
		// 修改 会员账户
		dbMember.setAccount(dbAccount);
		// 邀请送积分
		integralRecordService.addIntegralRecordByTaskType(memberBo.getParentId(), BasicEnum.TASK_TYPE_INVITE);
		return memberConvert.toVo(dbMember);
	}

	/**
	 * 微信授权登录的注册
	 */
	@Override
	public MemberVo registerByWeChat(MemberBo memberBo) {
		if (memberBo == null || StringUtils.isAnyBlank(memberBo.getUnionId())) {
			LOG.error("registerByWeChat,提交数据为空");
			throw new BusinessException("提交数据不能为空");
		}
		// open_id为空
		if (StringUtils.isAllBlank(memberBo.getOpenId(), memberBo.getMpOpenId(), memberBo.getAppOpenId())) {
			LOG.error("registerByWeChat,提交数据为空");
			throw new BusinessException("请选择微信授权");
		}
		// 微信服务号授权
		if (StringUtils.isNotBlank(memberBo.getOpenId())) {
			// 查看当前微信账户是否存在
			Member checkMemberOpenId = memberDao.findByUnionIdAndOpenIdAndDeleted(memberBo.getUnionId(), memberBo.getOpenId(), Deleted.DEL_FALSE);
			if (checkMemberOpenId != null) {
				LOG.error("registerByWeChat，微信open_id={}已被注册", memberBo.getOpenId());
				throw new BusinessException("该微信已注册");
			}
			// 微信小程序授权
		} else if (StringUtils.isNotBlank(memberBo.getMpOpenId())) {
			// 查看当前微信账户是否存在
			Member checkMemberMpOpenId = memberDao.findByUnionIdAndMpOpenIdAndDeleted(memberBo.getUnionId(), memberBo.getMpOpenId(), Deleted.DEL_FALSE);
			if (checkMemberMpOpenId != null) {
				LOG.error("registerByWeChat，微信open_id={}已被注册", memberBo.getOpenId());
				throw new BusinessException("该微信已注册");
			}
			// 微信APP授权
		} else if (StringUtils.isNotBlank(memberBo.getAppOpenId())) {
			// 查看当前微信账户是否存在
			Member checkMemberAppOpenId = memberDao.findByUnionIdAndAppOpenIdAndDeleted(memberBo.getUnionId(), memberBo.getAppOpenId(), Deleted.DEL_FALSE);
			if (checkMemberAppOpenId != null) {
				LOG.error("registerByWeChat，微信app_open_id={}已被注册", memberBo.getAppOpenId());
				throw new BusinessException("该微信已注册");
			}
		}
		// 查询当前数据 是否存在相同的手机号
		Member checkPhone = memberDao.findByPhoneAndDeleted(memberBo.getPhone(), Deleted.DEL_FALSE);
		// 绑定微信到当前账号
		if (checkPhone != null) {
			// 禁用账户返回异常
			if (MemberEnum.STATE_DISABLE.getCode().equals(checkPhone.getState())) {
				LOG.error("registerByWeChat，账号{}已被禁用", memberBo.getPhone());
				throw new BusinessException("该账号已禁用，请联系客服处理");
			}
			if (StringUtils.isNotBlank(memberBo.getOpenId()) && StringUtils.isBlank(checkPhone.getOpenId())) {
				checkPhone.setOpenId(memberBo.getOpenId());
			}
			if (StringUtils.isNotBlank(memberBo.getMpOpenId()) && StringUtils.isBlank(checkPhone.getMpOpenId())) {
				checkPhone.setMpOpenId(memberBo.getMpOpenId());
			}
			if (StringUtils.isNotBlank(memberBo.getAppOpenId()) && StringUtils.isBlank(checkPhone.getAppOpenId())) {
				checkPhone.setAppOpenId(memberBo.getAppOpenId());
			}
			if (StringUtils.isNotBlank(memberBo.getAvater()) && StringUtils.isBlank(checkPhone.getAvater())) {
				checkPhone.setAvater(memberBo.getAvater());
			}
			if (StringUtils.isNotBlank(memberBo.getNickname()) && StringUtils.isBlank(checkPhone.getNickname())) {
				checkPhone.setNickname(EmojiParser.removeAllEmojis(memberBo.getNickname()));
			}
			if (StringUtils.isNotBlank(memberBo.getUnionId()) && StringUtils.isBlank(checkPhone.getUnionId())) {
				checkPhone.setUnionId(memberBo.getUnionId());
			}

			// // 禁用的账户 改为启用
			// if (MemberEnum.STATE_DISABLE.getCode().equals(checkMemberPhone.getState())) {
			// checkMemberPhone.setState(MemberEnum.STATE_DISABLE.getCode());
			// }
		} else {
			// 查询父类
			Member parent = this.getMemberById(memberBo.getParentId());
			// 根据当前数据注册会员
			Member member = new Member();
			member.setPhone(memberBo.getPhone());
			member.setUsername(memberBo.getPhone());
			member.setPassword(memberBo.getPassword());
			member.setOpenId(memberBo.getOpenId());
			member.setMpOpenId(memberBo.getMpOpenId());
			member.setAppOpenId(memberBo.getAppOpenId());
			member.setUnionId(memberBo.getUnionId());
			if (parent != null) {
				member.setParent(parent);
			}
			if (StringUtils.isNotBlank(memberBo.getNickname())) {
				member.setNickname(EmojiParser.removeAllEmojis(memberBo.getNickname()));
			} else {
				member.setNickname(memberBo.getPhone().substring(8) + "会员");
			}
			member.setAvater(memberBo.getAvater());
			member.setMemberType(MemberEnum.MEMBER_TYPE_ORDINARY.getCode());
			member.setMemberLevel(memberLevelService.getDefaultMemberLevel());
			// 保存会员
			checkPhone = memberDao.save(member);
			// 保存会员账户
			Account account = new Account();
			account.setMember(checkPhone);
			account.setBalance(BigDecimal.ZERO);
			account.setConsumeAmount(BigDecimal.ZERO);
			account.setFreezeAmount(BigDecimal.ZERO);
			Account dbAccount = accountService.addAccount(account);
			// 更新 会员账户
			checkPhone.setAccount(dbAccount);
			// 计算邀请人积分
			integralRecordService.addIntegralRecordByTaskType(parent, BasicEnum.TASK_TYPE_INVITE);
		}
		return memberConvert.toVo(checkPhone);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Member getMemberByWeChatForSp(String unionId, String openId) {
		if (StringUtils.isAnyBlank(unionId, openId)) {
			LOG.error("参数（unionId={}，openId={}）为空", unionId, openId);
			return null;
		}
		return memberDao.findByUnionIdAndOpenIdAndStateAndDeleted(unionId, openId, MemberEnum.STATE_ENABLE.getCode(), Deleted.DEL_FALSE);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Member getMemberByWeChatForMp(String unionId, String mpOpenId) {
		if (StringUtils.isAnyBlank(unionId, mpOpenId)) {
			LOG.error("参数（unionId={}，mpOpenId={}）为空", unionId, mpOpenId);
			return null;
		}
		return memberDao.findByUnionIdAndMpOpenIdAndStateAndDeleted(unionId, mpOpenId, MemberEnum.STATE_ENABLE.getCode(), Deleted.DEL_FALSE);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Member getMemberByWeChatForApp(String unionId, String appOpenId) {
		if (StringUtils.isAnyBlank(unionId, appOpenId)) {
			LOG.error("参数（unionId={}，appOpenId={}）为空", unionId, appOpenId);
			return null;
		}
		return memberDao.findByUnionIdAndAppOpenIdAndStateAndDeleted(unionId, appOpenId, MemberEnum.STATE_ENABLE.getCode(), Deleted.DEL_FALSE);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int checkByPhone(String phone) {
		if (StringUtils.isBlank(phone)) {
			return 0;
		}
		return memberDao.countByPhoneAndDeleted(phone, Deleted.DEL_FALSE);
	}

	/**
	 * TODO 待完善
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MemberVo getMemberLevelInfo(Integer memberId) {
		if (memberId == null) {
			LOG.error("getMemberLevelInfo，请求参数（memberId）为空");
			throw new BusinessException("请求参数不能为空");
		}
		Member dbMember = memberDao.getOne(memberId);
		if (dbMember == null) {
			LOG.error("getMemberLevelInfo，根据memberId={}获取数据为空", memberId);
			throw new BusinessException("系统异常，请稍后重试");
		}
		// 查询 会员等级数据
		List<MemberLevelListVo> memberLevels = memberLevelService.queryAll();
		if (CollectionUtils.isNotEmpty(memberLevels)) {

		}

		return null;
	}

	/**
	 * 服务号 绑定微信
	 */
	@Override
	public MemberVo bindWeChatForSp(String memberId, String unionId, String openId) {
		if (StringUtils.isAnyBlank(memberId, unionId, openId)) {
			LOG.error("bindWeChat，绑定微信参数为空memberId={}，unionId={}，openId={}", memberId, unionId, openId);
			throw new BusinessException("参数不能为空");
		}
		Member dbMember = memberDao.getOne(Integer.valueOf(memberId));
		if (dbMember == null) {
			LOG.error("bindWeChatForSp，查询数据为空，memberId={}", memberId);
			throw new BusinessException("该账号还未注册，请您注册");
		}
		if (StringUtils.isBlank(dbMember.getUnionId())) {
			dbMember.setUnionId(unionId);
		}
		dbMember.setOpenId(openId);
		return memberConvert.toVo(dbMember);
	}

	/**
	 * 微信小程序 绑定手机号
	 */
	@Override
	public MemberListVo bindPhoneForMp(Integer memberId, String phone) {
		if (memberId != null && memberId.intValue() > 0 && StringUtils.isAnyBlank(phone)) {
			LOG.error("参数为空memberId={}，phone={}", memberId, phone);
			throw new BusinessException("提交数据不能为空");
		}
		Member dbMember = memberDao.getOne(memberId);
		if (dbMember == null) {
			LOG.error("查询数据为空，memberId={}", memberId);
			throw new BusinessException("该账号还未注册，请您注册");
		}
		// 绑定手机号
		dbMember.setUsername(phone);
		dbMember.setPhone(phone);
		return memberConvert.toListVo(dbMember);
	}

	/**
	 * APP 绑定微信
	 */
	@Override
	public MemberVo bindWeChatForApp(String memberId, String unionId, String appOpenId) {
		if (StringUtils.isAnyBlank(memberId, unionId, appOpenId)) {
			LOG.error("bindWeChat，绑定微信参数为空memberId={}，unionId={}，appOpenId={}", memberId, unionId, appOpenId);
			throw new BusinessException("参数不能为空");
		}
		Member dbMember = memberDao.getOne(Integer.valueOf(memberId));
		if (dbMember == null) {
			LOG.error("bindWeChatForApp，查询数据为空，memberId={}", memberId);
			throw new BusinessException("该账号还未注册，请您注册");
		}
		if (StringUtils.isBlank(dbMember.getUnionId())) {
			dbMember.setUnionId(unionId);
		}
		dbMember.setAppOpenId(appOpenId);
		return memberConvert.toVo(dbMember);
	}

	/**
	 * 计算会员佣金 并记录</br>
	 * 1.当前会员上级不为空 计算一级佣金 </br>
	 * 2.当前会员上级的上级不为空 计算二级佣金
	 *
	 * @param saleOrder
	 * @param member
	 */
	@Override
	public void calculateCommissionForDistribution(SaleOrder saleOrder, Member member) {
		if (saleOrder != null && member != null) {
			// 计算佣金金额=支付金额-运费
			// BigDecimal commissionAmount =
			// saleOrder.getPayAmount().subtract(saleOrder.getFreight());
			BigDecimal commissionAmount = this.calculateEffectiveCommissionAmount(saleOrder);
			// 有效金额大于0 计算佣金
			if (commissionAmount.compareTo(BigDecimal.ZERO) > 0) {
				if (member.getParent() != null) {
					Member parent = member.getParent();
					// 非送礼订单 计算佣金
					if (!OrderEnum.ORDER_TYPE_GIFT.getCode().equals(saleOrder.getOrderType())) {
						// 查询一级佣金比例
						BigDecimal firstCommissionRate = distributionLevelService.getFirstLevelCommissionRate();
						BigDecimal firstCommission = commissionAmount.multiply(firstCommissionRate).divide(BigDecimal.valueOf(100));
						// 修改账户一级佣金并记录
						accountService.updateMemberCommission(saleOrder, parent.getId(), firstCommission, MemberEnum.TRADE_TYPE_COMMISSION, member);
						// 保存佣金到会员佣金表
						MemberCommission memberCommission1 = new MemberCommission();
						memberCommission1.setMember(parent);
						memberCommission1.setSaleOrder(saleOrder);
						memberCommission1.setSubordinate(member);
						memberCommission1.setCommissionGrade(MemberEnum.COMMISSION_GRADE_FIRST.getCode());
						memberCommission1.setSettlementState(MemberEnum.SETTLEMENT_STATE_UNSETTLED.getCode());
						memberCommission1.setCommissionAmount(firstCommission);
						memberCommission1.setRemark("一级佣金");
						memberCommissionService.addMemberCommission(memberCommission1);
						// 计算二级佣金
						if (parent.getParent() != null) {
							// 查询二级佣金比例
							BigDecimal secondCommissionRate = distributionLevelService.getSecondLevelCommissionRate();
							BigDecimal secondCommission = commissionAmount.multiply(secondCommissionRate).divide(BigDecimal.valueOf(100));
							// 修改账户二级佣金并记录
							accountService.updateMemberCommission(saleOrder, parent.getParent().getId(), secondCommission, MemberEnum.TRADE_TYPE_COMMISSION, member);
							// 保存佣金到会员佣金表
							MemberCommission memberCommission2 = new MemberCommission();
							memberCommission2.setMember(parent.getParent());
							memberCommission2.setSaleOrder(saleOrder);
							memberCommission2.setSubordinate(member);
							memberCommission2.setCommissionGrade(MemberEnum.COMMISSION_GRADE_SECOND.getCode());
							memberCommission2.setSettlementState(MemberEnum.SETTLEMENT_STATE_UNSETTLED.getCode());
							memberCommission2.setCommissionAmount(secondCommission);
							memberCommission2.setRemark("二级佣金");
							memberCommissionService.addMemberCommission(memberCommission2);
						}
					}
				}
				// 会员小区存在且小区管理员存在
				if (member.getCommunity() != null && member.getCommunity().getMember() != null) {
					// 小区佣金比例
					BigDecimal communityCommissionRate = Optional.ofNullable(member.getCommunity().getCommissionRate()).orElse(BigDecimal.ZERO);
					BigDecimal communityCommission = commissionAmount.multiply(communityCommissionRate).divide(BigDecimal.valueOf(100));
					// 修改账户佣金并记录
					accountService.updateMemberCommission(saleOrder, member.getCommunity().getMember().getId(), communityCommission, MemberEnum.TRADE_TYPE_COMMUNITY_COMMISSION,
							member);
					// 保存佣金到会员佣金表
					MemberCommission memberCommission = new MemberCommission();
					memberCommission.setMember(member.getCommunity().getMember());
					memberCommission.setSaleOrder(saleOrder);
					memberCommission.setSubordinate(member);
					memberCommission.setCommissionGrade(MemberEnum.COMMISSION_GRADE_FIRST.getCode());
					memberCommission.setSettlementState(MemberEnum.SETTLEMENT_STATE_UNSETTLED.getCode());
					memberCommission.setCommissionAmount(communityCommission);
					memberCommission.setRemark("小区管理员佣金");
					memberCommissionService.addMemberCommission(memberCommission);
				}
			}
		}
	}

	// /**
	// * 计算会员佣金 并记录</br>
	// * 1.当前会员上级不为空 计算一级佣金 </br>
	// * 2.当前会员上级的上级不为空 计算二级佣金
	// *
	// * @param saleOrder
	// * @param member
	// */
	// @Override
	// public void calculateCommissionForCommodity(SaleOrder saleOrder, Member
	// member) {
	// if (saleOrder != null &&
	// CollectionUtils.isNotEmpty(saleOrder.getSaleOrderItems()) && member != null)
	// {
	// // 每个订单项 应该减去的优惠金额=优惠券金额-
	// BigDecimal couponAmount =
	// saleOrder.getCouponAmount().add(saleOrder.getVoucherAmount()).divide(BigDecimal.valueOf(saleOrder.getSaleOrderItems().size()));
	//
	// // 计算佣金
	// BigDecimal commission = saleOrder.getSaleOrderItems().stream().map(e ->
	// e.getSubtotal().multiply(e.getCommodity().getCommissionRate().divide(BigDecimal.valueOf(100))))
	// .reduce(BigDecimal.ZERO, BigDecimal::add);
	//
	// BigDecimal commissionAmount =
	// this.calculateEffectiveCommissionAmount(saleOrder);
	// // 有效金额大于0 计算佣金
	// if (commissionAmount.compareTo(BigDecimal.ZERO) > 0) {
	// if (member.getParent() != null) {
	// Member parent = member.getParent();
	// // 非送礼订单 计算佣金
	// if (!OrderEnum.ORDER_TYPE_GIFT.getCode().equals(saleOrder.getOrderType())) {
	// // 查询一级佣金比例
	// BigDecimal firstCommissionRate =
	// distributionLevelService.getFirstLevelCommissionRate();
	// BigDecimal firstCommission =
	// commissionAmount.multiply(firstCommissionRate).divide(BigDecimal.valueOf(100));
	// // 修改账户一级佣金并记录
	// accountService.updateMemberCommission(saleOrder, parent.getId(),
	// firstCommission, MemberEnum.TRADE_TYPE_COMMISSION, member);
	// // 保存佣金到会员佣金表
	// MemberCommission memberCommission1 = new MemberCommission();
	// memberCommission1.setMember(parent);
	// memberCommission1.setSaleOrder(saleOrder);
	// memberCommission1.setSubordinate(member);
	// memberCommission1.setCommissionGrade(MemberEnum.COMMISSION_GRADE_FIRST.getCode());
	// memberCommission1.setSettlementState(MemberEnum.SETTLEMENT_STATE_UNSETTLED.getCode());
	// memberCommission1.setCommissionAmount(firstCommission);
	// memberCommission1.setRemark("一级佣金");
	// memberCommissionService.addMemberCommission(memberCommission1);
	// // 计算二级佣金
	// if (parent.getParent() != null) {
	// // 查询二级佣金比例
	// BigDecimal secondCommissionRate =
	// distributionLevelService.getSecondLevelCommissionRate();
	// BigDecimal secondCommission =
	// commissionAmount.multiply(secondCommissionRate).divide(BigDecimal.valueOf(100));
	// // 修改账户二级佣金并记录
	// accountService.updateMemberCommission(saleOrder, parent.getParent().getId(),
	// secondCommission, MemberEnum.TRADE_TYPE_COMMISSION, member);
	// // 保存佣金到会员佣金表
	// MemberCommission memberCommission2 = new MemberCommission();
	// memberCommission2.setMember(parent.getParent());
	// memberCommission2.setSaleOrder(saleOrder);
	// memberCommission2.setSubordinate(member);
	// memberCommission2.setCommissionGrade(MemberEnum.COMMISSION_GRADE_SECOND.getCode());
	// memberCommission2.setSettlementState(MemberEnum.SETTLEMENT_STATE_UNSETTLED.getCode());
	// memberCommission2.setCommissionAmount(secondCommission);
	// memberCommission2.setRemark("二级佣金");
	// memberCommissionService.addMemberCommission(memberCommission2);
	// }
	// }
	// }
	// // 会员小区存在且小区管理员存在
	// if (member.getCommunity() != null && member.getCommunity().getMember() !=
	// null) {
	// // 小区佣金比例
	// BigDecimal communityCommissionRate =
	// Optional.ofNullable(member.getCommunity().getCommissionRate()).orElse(BigDecimal.ZERO);
	// BigDecimal communityCommission =
	// commissionAmount.multiply(communityCommissionRate).divide(BigDecimal.valueOf(100));
	// // 修改账户佣金并记录
	// accountService.updateMemberCommission(saleOrder,
	// member.getCommunity().getMember().getId(), communityCommission,
	// MemberEnum.TRADE_TYPE_COMMUNITY_COMMISSION,
	// member);
	// // 保存佣金到会员佣金表
	// MemberCommission memberCommission = new MemberCommission();
	// memberCommission.setMember(member.getCommunity().getMember());
	// memberCommission.setSaleOrder(saleOrder);
	// memberCommission.setSubordinate(member);
	// memberCommission.setCommissionGrade(MemberEnum.COMMISSION_GRADE_FIRST.getCode());
	// memberCommission.setSettlementState(MemberEnum.SETTLEMENT_STATE_UNSETTLED.getCode());
	// memberCommission.setCommissionAmount(communityCommission);
	// memberCommission.setRemark("小区管理员佣金");
	// memberCommissionService.addMemberCommission(memberCommission);
	// }
	// }
	// }
	// }

	/**
	 * 计算有效的计算佣金的订单金额
	 *
	 * @param saleOrder
	 */
	public BigDecimal calculateEffectiveCommissionAmount(SaleOrder saleOrder) {
		if (saleOrder == null) {
			return BigDecimal.ZERO;
		}
		BigDecimal effectiveAmount = BigDecimal.ZERO;
		// 非分销商品不计算佣金
		if (CollectionUtils.isNotEmpty(saleOrder.getSaleOrderItems())) {
			for (SaleOrderItem tmpItem : saleOrder.getSaleOrderItems()) {
				if (tmpItem != null && tmpItem.getCommodity() != null && CommodityEnum.DISTRIBUTION_YES.getCode().equals(tmpItem.getCommodity().getDistribution())) {
					effectiveAmount = effectiveAmount.add(tmpItem.getSubtotal());
				}
			}
		}
		// 有效金额-减去优惠金额
		effectiveAmount = effectiveAmount.subtract(saleOrder.getCouponAmount()).subtract(saleOrder.getVoucherAmount());
		return effectiveAmount;
	}

	/**
	 * 计算会员积分 并记录
	 */
	@Override
	public void calculateOrderIntegral(SaleOrder saleOrder, Member member) {
		if (saleOrder != null && member != null) {
			BigDecimal orderIntegral = saleOrder.getSaleOrderItems().stream().map(e -> e.getSubtotal().multiply(e.getCommodity().getIntegralRate().divide(BigDecimal.valueOf(100))))
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			LOG.error("----" + saleOrder.getOrderAmount() + "-----------" + saleOrder.getPayAmount());
			// 订单优惠金额
			BigDecimal discount = saleOrder.getOrderAmount().subtract(saleOrder.getPayAmount());
			LOG.error("订单优惠金额{}", discount);
			Account dbAccount = integralRecordService.addIntegralRecordByOrder(member, orderIntegral.subtract(discount), BasicEnum.TASK_TYPE_ORDER);
			if (dbAccount != null) {
				if (dbAccount.getIntegral() > 0) {
					// 计算会员等级
					MemberLevel dbMemberLevel = memberLevelService.calculateLevelByIntegral(dbAccount.getIntegral());
					// 等级不为空且不是会员当前拥有的等级 升级会员
					if (dbMemberLevel != null && dbMemberLevel.getId() != member.getMemberLevel().getId()) {
						member.setMemberLevel(dbMemberLevel);
					}
				}
			}
		}
	}

	/**
	 * 根据时间 统计今日新增会员数
	 *
	 * @param date
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getMemberNumByDate(Date date) {
		if (date == null) {
			return 0;
		}
		return memberDao.countByDeletedAndCreateTime(Deleted.DEL_FALSE, date);
	}

}
