/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.domain.bo.CouponReceiveBo;
import com.yi.core.activity.domain.entity.CouponReceive;
import com.yi.core.activity.domain.vo.CouponReceiveListVo;
import com.yi.core.activity.domain.vo.CouponReceiveVo;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yihz.common.persistence.Pagination;

/**
 * 优惠券领取
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface ICouponReceiveService {

	/**
	 * 分页查询: CouponReceive
	 * 
	 * @param query
	 * @return
	 */
	Page<CouponReceive> query(Pagination<CouponReceive> query);

	/**
	 * 分页查询: CouponReceiveListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<CouponReceiveListVo> queryListVo(Pagination<CouponReceive> query);

	/**
	 * 分页查询: CouponReceiveListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<CouponReceiveListVo> queryListVoForApp(Pagination<CouponReceive> query);

	/**
	 * 根据Entity创建CouponReceive
	 * 
	 * @param couponReceive
	 * @return
	 */
	CouponReceive addCouponReceive(CouponReceive couponReceive);

	/**
	 * 根据BO创建CouponReceive
	 * 
	 * @param couponReceiveBo
	 * @return
	 */
	CouponReceiveListVo addCouponReceive(CouponReceiveBo couponReceiveBo);

	/**
	 * 根据Entity更新CouponReceive
	 * 
	 * @param couponReceive
	 * @return
	 */
	CouponReceive updateCouponReceive(CouponReceive couponReceive);

	/**
	 * 根据BO更新CouponReceive
	 *
	 * @param couponReceiveBo
	 * @return
	 */
	CouponReceiveListVo updateCouponReceive(CouponReceiveBo couponReceiveBo);

	/**
	 * 删除CouponReceive
	 * 
	 * @param couponReceiveId
	 */
	void removeCouponReceiveById(int couponReceiveId);

	/**
	 * 根据ID得到CouponReceive
	 * 
	 * @param couponReceiveId
	 * @return
	 */
	CouponReceive getById(int couponReceiveId);

	/**
	 * 根据ID得到CouponReceiveVo
	 * 
	 * @param couponReceiveId
	 * @return
	 */
	CouponReceiveVo getVoById(int couponReceiveId);

	/**
	 * 根据ID得到CouponReceiveListVo
	 * 
	 * @param couponReceiveId
	 * @return
	 */
	CouponReceiveListVo getListVoById(int couponReceiveId);

	/**
	 * 领取优惠券
	 * 
	 * @param couponId
	 * @param memberId
	 * @return
	 */
	CouponReceiveListVo receiveCoupon(Integer couponId, Integer member);

	/**
	 * 获取会员的优惠券
	 * 
	 * @param memberId
	 * @return
	 */
	List<CouponReceive> getMemberCoupons(Integer memberId);

	/**
	 * 获取会员的代金券
	 * 
	 * @param memberId
	 * @return
	 */
	List<CouponReceive> getMemberVouchers(Integer memberId);

	/**
	 * 使用优惠券
	 * 
	 * @param saleOrder
	 */
	void useCouponsByOrder(List<SaleOrder> saleOrders);

	/**
	 * 分步发放代金券
	 * 
	 * @param member
	 * @param saleOrder
	 * @param grantStep
	 */
	void grantVoucherByStep(Member member, SaleOrder saleOrder, ActivityEnum grantStep);

	/**
	 * 手工发送优惠券
	 * 
	 * @param couponReceiveBo
	 */
	void grantCoupon(CouponReceiveBo couponReceiveBo);

	/**
	 * 退款时 收回已经发出的代金券
	 * 
	 * @param member
	 * @param saleOrder
	 */
	void returnVoucherByRefund(Member member, SaleOrder saleOrder);

	/**
	 * 
	 * @param memberId
	 * @param couponType
	 * @return
	 */
	List<CouponReceiveListVo> getCouponTypeAvailable(int memberId, int couponType);

	/**
	 * 该优惠券领取数量
	 */
	int countByCouponId(int couponId);

	/**
	 * 自动作废优惠券
	 */
	void autoCancelCouponByTask();

}
