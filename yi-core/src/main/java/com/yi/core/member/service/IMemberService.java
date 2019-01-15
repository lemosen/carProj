/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONObject;
import com.yi.core.member.domain.bo.MemberBo;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.domain.vo.MemberListVo;
import com.yi.core.member.domain.vo.MemberVo;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yihz.common.persistence.Pagination;

/**
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IMemberService {

    /**
     * 根据ID得到Member
     *
     * @param memberId
     * @return
     */
    Member getMemberById(Integer memberId);

    /**
     * 根据ID得到MemberVo
     *
     * @param memberId
     * @return
     */
    MemberVo getMemberVoById(Integer memberId);

    /**
     * 根据ID得到MemberVo
     *
     * @param memberId
     * @return
     */
    MemberVo getMemberVoByIdForApp(Integer memberId);

    /**
     * 根据ID得到MemberListVo
     *
     * @param memberId
     * @return
     */
    MemberListVo getMemberListVoById(Integer memberId);

    /**
     * 根据Entity创建Member
     *
     * @param member
     * @return
     */
    MemberVo addMember(Member member);

    /**
     * 根据BO创建Member
     *
     * @param memberBo
     * @return
     */
    MemberListVo addMember(MemberBo memberBo);

    /**
     * 根据Entity更新Member
     *
     * @param member
     * @return
     */
    MemberVo updateMember(Member member);

    /**
     * 根据BO更新Member
     *
     * @param memberBo
     * @return
     */
    MemberListVo updateMember(MemberBo memberBo);

    /**
     * 删除Member
     *
     * @param memberId
     */
    void removeMemberById(int memberId);

    /**
     * 分页查询: Member
     *
     * @param query
     * @return
     */
    Page<Member> query(Pagination<Member> query);

    /**
     * 分页查询: MemberListVo
     *
     * @param query
     * @return
     */
    Page<MemberListVo> queryListVo(Pagination<Member> query);

    /**
     * 分页查询: MemberListVo
     *
     * @param query
     * @param teamType
     * @return
     */
    Page<MemberListVo> queryMyTeam(Pagination<Member> query, Integer teamType);

    /**
     * 通过账号密码登录
     *
     * @param memberBo
     * @return
     */
    MemberVo login(MemberBo memberBo);

    /**
     * 通过验证码登录
     *
     * @param memberBo
     * @return
     */
    MemberVo loginBySms(MemberBo memberBo);

    /**
     * 注册
     *
     * @param memberBo
     * @return
     */
    MemberVo register(MemberBo memberBo);

    /**
     * 微信授权登录的注册
     *
     * @param memberBo
     * @return
     */
    MemberVo registerByWeChat(MemberBo memberBo);

    /**
     * 修改手机号
     *
     * @param memberBo
     * @return
     */
    MemberVo changePhone(MemberBo memberBo);

    MemberVo changeMember(MemberBo memberBo);

    /**
     * 修改密码
     *
     * @param memberBo
     * @return
     */
    MemberVo changePwd(MemberBo memberBo);

    /**
     * 忘记密码
     *
     * @param memberBo
     * @return
     */
    MemberVo forgetPassword(MemberBo memberBo);

    /**
     * 获取邀请列表
     *
     * @param memberId
     * @return
     */
    int getMyTeamNum(int memberId);

    MemberVo getBalance(int memberId);

    MemberVo updateState(int memberId);

    /**
     * 修改支付密码
     *
     * @param memberBo
     * @return
     */
    MemberVo modifyPayPassword(MemberBo memberBo);

    MemberVo updataVipNo(int memberId);

    MemberVo updataVipYes(int memberId);

    /**
     * 修改小区
     */
    MemberListVo updataCommunity(int communityId, int memberId);

    /**
     * 修改手机号
     *
     * @param memberBo
     * @return
     */
    MemberVo updataPhone(MemberBo memberBo);

    /**
     * 获取会员数量
     *
     * @return
     */
    int getMemberNum();

    /**
     * 统计每天新增数量
     *
     * @param statrtDate
     * @param endDate
     * @return
     */
    List<Object[]> getDailyAddNumByDate(Date startDate, Date endDate);

    /**
     * 微信授权登录 检查数据库是否存在
     *
     * @param unionId
     * @param appOpenid
     * @return
     */
    Member checkMemberByWeChat(String unionId);

    /**
     * 微信服务号授权登录 检查数据库是否存在 并同步 头像和昵称
     *
     * @param unionId
     * @param avater
     * @param nickname
     * @return
     */
    Member checkMemberByWeChatForSp(String unionId, String openId, String avater, String nickname);

    /**
     * 微信小程序授权登录 检查数据库是否存在 并同步头像和昵称 或注册
     *
     * @param unionId
     * @param avater
     * @param nickname
     * @return
     */
    // Member checkMemberByWeChatForMp(String unionId, String openId, String avater,
    // String nickname);

    /**
     * 微信小程序授权登录 检查数据库是否存在 并同步头像和昵称 或注册
     *
     * @param userInfo
     * @param parentId
     * @return
     */
    Object checkMemberByWeChatForMp(JSONObject userInfo, Integer parentId);

    /**
     * 微信APP授权登录 检查数据库是否存在 并同步 头像和昵称
     *
     * @param unionId
     * @param avater
     * @param nickname
     * @return
     */
    Member checkMemberByWeChatForApp(String unionId, String openId, String avater, String nickname);

    /**
     * 根据微信unionId和open_id获取会员数据
     *
     * @param unionId
     * @param openId
     * @return
     */
    Member getMemberByWeChatForSp(String unionId, String openId);

    /**
     * 根据微信unionId和mpOpenId获取会员数据
     *
     * @param unionId
     * @param mpOpenId
     * @return
     */
    Member getMemberByWeChatForMp(String unionId, String mpOpenId);

    /**
     * 根据微信unionId和appOpenId获取会员数据
     *
     * @param unionId
     * @param appOpenId
     * @return
     */
    Member getMemberByWeChatForApp(String unionId, String appOpenId);

    /**
     * 服务号 绑定微信
     *
     * @param memberId
     * @param unionId
     * @param openId
     * @return
     */
    MemberVo bindWeChatForSp(String memberId, String unionId, String openId);

    /**
     * 微信小程序 绑定手机
     *
     * @param memberId
     * @param phone
     * @return
     */
    MemberListVo bindPhoneForMp(Integer memberId, String phone);

    /**
     * app 绑定微信
     *
     * @param memberId
     * @param unionId
     * @param appOpenId
     * @return
     */
    MemberVo bindWeChatForApp(String memberId, String unionId, String appOpenId);

    /**
     * 根据手机号 校验是否注册
     *
     * @param phone
     * @return
     */
    int checkByPhone(String phone);

    /**
     * 获取会员等级 信息
     *
     * @param memberId
     * @return
     */
    MemberVo getMemberLevelInfo(Integer memberId);

    /**
     * 计算会员佣金 并记录</br>
     * 1.当前会员上级不为空 计算一级佣金 </br>
     * 2.当前会员上级的上级不为空 计算二级佣金
     *
     * @param saleOrder
     * @param member
     */
    void calculateCommissionForDistribution(SaleOrder saleOrder, Member member);

//	/**
//	 * 计算会员佣金 并记录</br>
//	 * 1.当前会员上级不为空 计算一级佣金 </br>
//	 * 2.当前会员上级的上级不为空 计算二级佣金
//	 * 
//	 * @param saleOrder
//	 * @param member
//	 */
//	void calculateCommissionForCommodity(SaleOrder saleOrder, Member member);

    /**
     * 计算会员积分 并记录
     *
     * @param saleOrder
     * @param member
     */
    void calculateOrderIntegral(SaleOrder saleOrder, Member member);

    /**
     * 根据时间 统计今日新增会员数
     *
     * @param date
     * @return
     */
    int getMemberNumByDate(Date date);

    /**
     * 修改赠送红包余额
     *
     * @param memberId
     * @param balance
     */
    void updateBalance(int memberId, BigDecimal balance);
}
