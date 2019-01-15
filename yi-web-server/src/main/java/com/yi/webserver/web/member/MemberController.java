package com.yi.webserver.web.member;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.activity.domain.entity.CouponReceive;
import com.yi.core.activity.service.ICouponReceiveService;
import com.yi.core.basic.domain.entity.IntegralRecord;
import com.yi.core.basic.domain.vo.IntegralRecordListVo;
import com.yi.core.basic.service.ICommunityService;
import com.yi.core.basic.service.IIntegralRecordService;
import com.yi.core.basic.service.IIntegralTaskService;
import com.yi.core.common.SmsService;
import com.yi.core.member.MemberEnum;
import com.yi.core.member.domain.bo.MemberBo;
import com.yi.core.member.domain.bo.ShippingAddressBo;
import com.yi.core.member.domain.entity.AccountRecord;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.domain.entity.MemberCommission;
import com.yi.core.member.domain.vo.AccountRecordListVo;
import com.yi.core.member.domain.vo.MemberCommissionListVo;
import com.yi.core.member.domain.vo.MemberListVo;
import com.yi.core.member.domain.vo.MemberVo;
import com.yi.core.member.service.IAccountRecordService;
import com.yi.core.member.service.IAccountService;
import com.yi.core.member.service.IMemberCommissionService;
import com.yi.core.member.service.IMemberService;
import com.yi.core.member.service.IShippingAddressService;
import com.yi.core.member.service.ISignTimeService;
import com.yi.core.payment.weChat.WeChatAuthService;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 会员
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    private final Logger LOG = LoggerFactory.getLogger(MemberController.class);

    @Resource
    private IMemberService memberService;

    @Resource
    private IShippingAddressService shippingAddressService;

    @Resource
    private ICouponReceiveService couponReceiveService;

    @Resource
    private IAccountRecordService accountRecordService;

    @Resource
    private ICommunityService communityService;

    @Resource
    private ISignTimeService signTimeService;

    @Resource
    private IIntegralRecordService integralRecordService;

    @Resource
    private SmsService smsService;

    @Resource
    private IAccountService accountService;

    @Resource
    private IIntegralTaskService integralTaskService;

    @Resource
    private WeChatAuthService weChatAuthService;

    @Resource
    private IMemberCommissionService memberCommissionService;

    /**
     * 通过验证码登录
     *
     * @param memberBo
     * @return
     */
    @RequestMapping(value = "loginBySms", method = RequestMethod.POST)
    public RestResult loginBySms(@RequestBody MemberBo memberBo) {
        try {
            if (StringUtils.isAnyBlank(memberBo.getPhone(), memberBo.getSmsCode())) {
                return RestUtils.error("手机号或验证码不能为空");
            }
            boolean flag = smsService.checkCode(memberBo.getPhone(), memberBo.getSmsCode());
            if (!flag) {
                return RestUtils.error("验证码不正确");
            }
            MemberVo memberVo = memberService.loginBySms(memberBo);
            return RestUtils.successWhenNotNull(memberVo);
        } catch (Exception e) {
            LOG.error("login error", e.getMessage(), e);
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * 通过账号密码登陆
     *
     * @param memberBo
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public RestResult login(@RequestBody MemberBo memberBo) {
        try {
            MemberVo memberVo = memberService.login(memberBo);
            return RestUtils.successWhenNotNull(memberVo);
        } catch (Exception e) {
            LOG.error("login error" + e.getMessage());
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * 服务号 微信授权 自动登录
     *
     * @param openId
     * @return
     */
    @RequestMapping(value = "getMemberByWeChatForSp", method = RequestMethod.GET)
    public RestResult getMemberByWeChatForSp(@RequestParam("unionId") String unionId, @RequestParam("openId") String openId) {
        try {
            return RestUtils.successWhenNotNull(memberService.getMemberByWeChatForSp(unionId, openId));
        } catch (Exception e) {
            LOG.error("getMemberByWeChatForSp error：{}", e.getMessage(), e);
            return RestUtils.error("授权失败，请稍后重试");
        }
    }

    /**
     * 小程序 微信授权 自动登录
     *
     * @param openId
     * @return
     */
    @RequestMapping(value = "getMemberByWeChatForMp", method = RequestMethod.GET)
    public RestResult getMemberByWeChatForMp(@RequestParam("unionId") String unionId, @RequestParam("openId") String openId) {
        try {
            return RestUtils.successWhenNotNull(memberService.getMemberByWeChatForMp(unionId, openId));
        } catch (Exception e) {
            LOG.error("getMemberByWeChatForMp error：{}", e.getMessage(), e);
            return RestUtils.error("授权失败，请稍后重试");
        }
    }

    /**
     * app 微信授权 自动登录
     *
     * @param unionId
     * @param unionId
     * @return
     */
    @RequestMapping(value = "getMemberByWeChatForApp", method = RequestMethod.GET)
    public RestResult getMemberByWeChatForApp(@RequestParam("unionId") String unionId, @RequestParam("appOpenId") String appOpenId) {
        try {
            return RestUtils.successWhenNotNull(memberService.getMemberByWeChatForApp(unionId, appOpenId));
        } catch (Exception e) {
            LOG.error("getMemberByWeChatForApp error：{}", e.getMessage(), e);
            return RestUtils.error("授权失败，请稍后重试");
        }
    }

    /**
     * 服务号 绑定微信
     *
     * @param memberId
     * @param unionId
     * @param openId
     * @return
     */
    @RequestMapping(value = "bindWeChatForSp", method = RequestMethod.GET)
    public RestResult bindWeChatForSp(@RequestParam("memberId") String memberId, @RequestParam("unionId") String unionId, @RequestParam("openId") String openId) {
        try {
            return RestUtils.successWhenNotNull(memberService.bindWeChatForSp(memberId, unionId, openId));
        } catch (Exception e) {
            LOG.error("bindWeChatForSp error{}", e.getMessage(), e);
            return RestUtils.error("绑定失败，请稍后重试");
        }
    }

    /**
     * 微信小程序 绑定手机
     *
     * @param memberId
     * @param encryptedData
     * @param iv
     * @return
     */
    @RequestMapping(value = "bindPhoneForMp", method = RequestMethod.GET)
    public RestResult bindPhoneForMp(@RequestParam("memberId") Integer memberId, @RequestParam("code") String code, @RequestParam("encryptedData") String encryptedData,
                                     @RequestParam("iv") String iv) {
        try {
            String phone = weChatAuthService.getUserInfoByBindPhoneForMp(code, encryptedData, iv);
            MemberListVo dbMemberListVo = memberService.bindPhoneForMp(memberId, phone);
            return RestUtils.successWhenNotNull(dbMemberListVo);
        } catch (Exception e) {
            LOG.error("bindPhoneForMp error:{}", e.getMessage(), e);
            return RestUtils.error("绑定失败，请稍后重试");
        }
    }

    /**
     * APP 绑定微信
     *
     * @param unionId
     * @param memberId
     * @param openId
     * @return
     */
    @RequestMapping(value = "bindWeChatForApp", method = RequestMethod.GET)
    public RestResult bindWeChatForApp(@RequestParam("memberId") String memberId, @RequestParam("unionId") String unionId, @RequestParam("openId") String openId) {
        try {
            return RestUtils.successWhenNotNull(memberService.bindWeChatForApp(memberId, unionId, openId));
        } catch (Exception e) {
            LOG.error("bindWeChatForApp error:{}", e.getMessage(), e);
            return RestUtils.error("绑定失败，请稍后重试");
        }
    }

    @RequestMapping(value = "testUpdateMemberLevel", method = RequestMethod.GET)
    public RestResult testUpdateMemberLevel(@RequestParam("memberId") String memberId) {
        return RestUtils.success(memberService.updateMemberLevel(Integer.parseInt(memberId)));
    }

    /**
     * 注册
     *
     * @param memberBo
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public RestResult register(@RequestBody MemberBo memberBo) {
        if (StringUtils.isBlank(memberBo.getPhone())) {
            return RestUtils.error("手机号不能为空");
        }
        if (StringUtils.isBlank(memberBo.getPassword())) {
            return RestUtils.error("密码不能为空");
        }
        if (StringUtils.isBlank(memberBo.getSmsCode())) {
            return RestUtils.error("验证码不能为空");
        }
        boolean flag = smsService.checkCode(memberBo.getPhone(), memberBo.getSmsCode());
        if (!flag) {
            return RestUtils.error("验证码不正确");
        }
        try {
            MemberVo memberVo = null;
            // 微信授权登录的注册
            if (StringUtils.isNotBlank(memberBo.getUnionId())) {
                memberVo = memberService.registerByWeChat(memberBo);
            } else {
                // 普通注册
                memberVo = memberService.register(memberBo);
            }
            memberService.sendRedPackets(memberVo.getId(), memberBo.getParent().getId());
            return RestUtils.success(memberVo);
        } catch (Exception e) {
            LOG.error("register error" + e.getMessage());
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * 根据ID获取用户信息 包括支付密码
     *
     * @param memberId
     * @return
     */
    @RequestMapping(value = "getMember", method = RequestMethod.GET)
    public RestResult getMember(@RequestParam("memberId") Integer memberId) {
        try {
            return RestUtils.successWhenNotNull(memberService.getMemberVoByIdForApp(memberId));
        } catch (Exception e) {
            LOG.error("getMember error :{}", e.getMessage(), e);
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * 修改用户信息
     *
     * @param memberBo
     * @return
     */
    @RequestMapping(value = "changeMember", method = RequestMethod.POST)
    public RestResult changeMember(@RequestBody MemberBo memberBo) {
        if (memberBo.getId() < 1) {
            return RestUtils.error("提交参数不能为空");
        }
        try {
            return RestUtils.success(memberService.changeMember(memberBo));
        } catch (Exception e) {
            LOG.error("changeMember error :" + e.getMessage());
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * 忘记密码
     *
     * @param
     * @return
     */
    @RequestMapping(value = "forgetPassword", method = RequestMethod.POST)
    public RestResult forgetPassword(@RequestBody MemberBo memberBo) {
        if (StringUtils.isBlank(memberBo.getPhone())) {
            return RestUtils.error("手机号不能为空");
        }
        if (StringUtils.isBlank(memberBo.getSmsCode())) {
            return RestUtils.error("验证码不能为空");
        }
        boolean checkCode = smsService.checkCode(memberBo.getPhone(), memberBo.getSmsCode());
        if (!checkCode) {
            return RestUtils.error("验证码不正确");
        }
        try {
            return RestUtils.success(memberService.forgetPassword(memberBo));
        } catch (Exception e) {
            LOG.error("forgetPassword error :" + e.getMessage());
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * 修改密码
     *
     * @param
     * @return
     */
    @RequestMapping(value = "changePwd", method = RequestMethod.POST)
    public RestResult changePwd(@RequestBody MemberBo memberBo) {
        try {
            return RestUtils.success(memberService.changePwd(memberBo));
        } catch (Exception e) {
            LOG.error("changePwd error :" + e.getMessage());
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * 修改支付密码
     */
    @RequestMapping(value = "modifyPayPassword", method = RequestMethod.POST)
    public RestResult modifyPayPassword(@RequestBody MemberBo memberBo) {
        try {
            return RestUtils.success(memberService.modifyPayPassword(memberBo));
        } catch (Exception e) {
            LOG.error("changePwd error :" + e.getMessage());
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * 获取收货地址
     *
     * @param memberId
     * @return
     */
    @RequestMapping(value = "getAddress", method = RequestMethod.GET)
    public RestResult getAddress(@RequestParam("memberId") int memberId) {
        try {
            return RestUtils.success(shippingAddressService.getShippingAddressListVoByMemberId(memberId));
        } catch (Exception e) {
            LOG.error("getAddress error :" + e.getMessage());
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * 添加收货地址
     *
     * @param shippingAddressBo
     * @return
     */
    @RequestMapping(value = "addAddress", method = RequestMethod.POST)
    public RestResult addAddress(@RequestBody ShippingAddressBo shippingAddressBo) {
        try {
            return RestUtils.success(shippingAddressService.addShippingAddress(shippingAddressBo));
        } catch (Exception e) {
            LOG.error("addAddress error :" + e.getMessage());
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * 编辑收货地址
     *
     * @param shippingAddressBo
     * @return
     */
    @RequestMapping(value = "changeAddress", method = RequestMethod.POST)
    public RestResult changeAddress(@RequestBody ShippingAddressBo shippingAddressBo) {
        try {
            return RestUtils.success(shippingAddressService.updateShippingAddress(shippingAddressBo));
        } catch (Exception e) {
            LOG.error("changeAddress error :" + e.getMessage());
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * 删除收货地址
     *
     * @param addressId
     * @return
     */
    @RequestMapping(value = "removeAddress", method = RequestMethod.GET)
    public RestResult removeAddress(@RequestParam("addressId") int addressId) {
        try {
            shippingAddressService.removeShippingAddressById(addressId);
            return RestUtils.success();
        } catch (Exception e) {
            LOG.error("removeAddress error :" + e.getMessage());
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * 获取地址信息
     *
     * @param addressId
     * @return
     */
    @RequestMapping(value = "getAddressDetail", method = RequestMethod.GET)
    public RestResult getAddressDetail(@RequestParam("addressId") int addressId) {
        try {
            return RestUtils.success(shippingAddressService.getShippingAddressDetail(addressId));
        } catch (Exception e) {
            LOG.error("getAddressDetail error :" + e.getMessage());
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * 设置收货地址为默认地址
     *
     * @param memberId
     * @param addressId
     * @return
     */
    @RequestMapping(value = "setDefaultAddress", method = RequestMethod.GET)
    public RestResult setDefaultAddress(@RequestParam("memberId") int memberId, @RequestParam("addressId") int addressId) {
        try {
            return RestUtils.success(shippingAddressService.setDefaultAddress(memberId, addressId));
        } catch (Exception e) {
            LOG.error("setDefaultAddress error :" + e.getMessage());
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * 获取我的优惠券
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "getCoupon", method = RequestMethod.POST)
    public RestResult getCoupon(@RequestBody Pagination<CouponReceive> query) {
        try {
            return RestUtils.success(couponReceiveService.queryListVoForApp(query));
        } catch (Exception e) {
            LOG.error("getCoupon error :{}", e.getMessage(), e);
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * @param memberId
     * @return
     */
    @RequestMapping(value = "memberSign", method = RequestMethod.GET)
    public RestResult memberSign(@RequestParam("memberId") int memberId) {
        try {
            return RestUtils.success(signTimeService.getSignInfo(memberId));
        } catch (Exception e) {
            LOG.error("getRewards error :{}", e.getMessage(), e);
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * 点击签到
     *
     * @param memberId
     * @return
     */
    @RequestMapping(value = "checkIn", method = RequestMethod.GET)
    public RestResult checkIn(@RequestParam("memberId") int memberId) {
        try {
            return RestUtils.success(signTimeService.clickSign(memberId));
        } catch (Exception e) {
            LOG.error("clickCheck error :{}", e.getMessage(), e);
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * 获取我的团队人数
     *
     * @param memberId
     * @return
     */
    @RequestMapping(value = "getMyTeamNum", method = RequestMethod.GET)
    public RestResult getMyTeamNum(@RequestParam("memberId") int memberId) {
        try {
            return RestUtils.success(memberService.getMyTeamNum(memberId));
        } catch (Exception e) {
            LOG.error("getMyTeamNum error :{}", e.getMessage(), e);
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * 获取会员余额
     *
     * @param memberId
     * @return
     */
    @RequestMapping(value = "getBalance", method = RequestMethod.GET)
    public RestResult getBalance(@RequestParam("memberId") int memberId) {
        try {
            return RestUtils.success(memberService.getBalance(memberId));
        } catch (Exception e) {
            LOG.error("getBalance error :{}", e.getMessage(), e);
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * 账户记录
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "getAccountRecords", method = RequestMethod.POST)
    public Page<AccountRecordListVo> getAccountRecords(@RequestBody Pagination<AccountRecord> query) {
        return accountRecordService.queryListVoForApp(query);
    }

    /**
     * 根据城市查询小区
     *
     * @param city
     * @return
     */
    @RequestMapping(value = "getCommunityByCity", method = RequestMethod.GET)
    public RestResult getCommunityByCity(@RequestParam("city") String city) {
        try {
            return RestUtils.success(communityService.getCommunityByCity(city));
        } catch (Exception ex) {
            LOG.error("getAddressByCity failure : city={}", city, ex);
            return RestUtils.error(ex.getMessage());
        }
    }

    /**
     * 修改用户小区
     **/
    @RequestMapping(value = "updateCommunity", method = RequestMethod.GET)
    public RestResult updateCommunity(@RequestParam("communityId") int communityId, @RequestParam("memberId") int memberId) {
        try {
            return RestUtils.success(memberService.updataCommunity(communityId, memberId));
        } catch (Exception ex) {
            LOG.error("update Community failure : memberId={}", memberId, ex);
            return RestUtils.error(ex.getMessage());
        }
    }

    /**
     * 修改手机号
     */
    @RequestMapping(value = "updatePhone", method = RequestMethod.POST)
    public RestResult updatePhone(@RequestBody MemberBo memberBo) {
        if (StringUtils.isBlank(memberBo.getPhone())) {
            return RestUtils.error("手机号不能为空");
        }
        if (StringUtils.isBlank(memberBo.getSmsCode())) {
            return RestUtils.error("验证码不能为空");
        }
        boolean flag = smsService.checkCode(memberBo.getPhone(), memberBo.getSmsCode());
        if (!flag) {
            return RestUtils.error("验证码不正确");
        }
        try {
            return RestUtils.success(memberService.forgetPassword(memberBo));
        } catch (Exception e) {
            LOG.error("forgetPassword error :", e.getMessage(), e);
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * 获取会员等级信息
     *
     * @param memberId
     * @return
     */
    @RequestMapping(value = "getMemberLevelInfo", method = RequestMethod.GET)
    public RestResult getMemberLevelInfo(@RequestParam("memberId") Integer memberId) {
        if (memberId == null) {
            return RestUtils.error("请求参数不能为空");
        }
        try {
            return RestUtils.success(memberService.getMemberLevelInfo(memberId));
        } catch (Exception e) {
            LOG.error("forgetPassword error :{}", e.getMessage(), e);
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * 获取用户所属小区信息
     *
     * @param memberId
     * @return
     */
    @RequestMapping(value = "getCommunityByMemberId", method = RequestMethod.GET)
    public RestResult getCommunityByMemberId(@RequestParam("memberId") Integer memberId) {
        if (memberId == null) {
            return RestUtils.error("请求参数不能为空");
        }
        try {
            return RestUtils.success(communityService.getCommunityInfo(memberId));
        } catch (Exception e) {
            LOG.error("getCommunityByMemberId error :{}", e.getMessage(), e);
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * 查询会员账户信息
     *
     * @param memberId
     * @return
     */
    @RequestMapping(value = "getAccountByMemberId", method = RequestMethod.GET)
    public RestResult getAccountByMemberId(@RequestParam("memberId") Integer memberId) {
        try {
            return RestUtils.success(accountService.getAccountVoByMember(memberId));
        } catch (Exception e) {
            LOG.error("getAccountByMemberId error :{}", e.getMessage(), e);
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * 分页查询用户积分记录
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "queryIntegralRecords", method = RequestMethod.POST)
    public Page<IntegralRecordListVo> getIntegralRecord(@RequestBody Pagination<IntegralRecord> query) {
        Page<IntegralRecordListVo> pageInfo = new PageImpl<IntegralRecordListVo>(new ArrayList<>());
        // 没有查询条件 不查询数据
        if (CollectionUtils.isNotEmpty(query.getFilter().getFilters()) && "member.id".equals(query.getFilter().getFilters().get(0).getField())) {
            pageInfo = integralRecordService.queryListVo(query);
        }
        return pageInfo;
    }

    /**
     * 查看积分赠送规则
     *
     * @param taskType
     * @return
     */
    @RequestMapping(value = "getIntegralTask", method = RequestMethod.GET)
    public RestResult getIntegralRecord(@RequestParam("taskType") Integer taskType) {
        try {
            return RestUtils.success(integralTaskService.getIntegralTaskByType(taskType));
        } catch (Exception e) {
            LOG.error("get IntegralTask error :{}", e.getMessage(), e);
            return RestUtils.error(e.getMessage());
        }
    }

    /**
     * 分页查询 我的一级团队
     */
    @RequestMapping(value = "queryFirstLevelTeam", method = RequestMethod.POST)
    public Page<MemberListVo> queryFirstLevelTeam(@RequestBody Pagination<Member> query) {
        Page<MemberListVo> page = memberService.queryMyTeam(query, MemberEnum.DISTRIBUTION_LEVEL_FIRST.getCode());
        return page;
    }

    /**
     * 分页查询 我的二级团队
     */
    @RequestMapping(value = "querySecondLevelTeam", method = RequestMethod.POST)
    public Page<MemberListVo> querySecondLevelTeam(@RequestBody Pagination<Member> query) {
        Page<MemberListVo> page = memberService.queryMyTeam(query, MemberEnum.DISTRIBUTION_LEVEL_SECOND.getCode());
        return page;
    }

    /**
     * 查询我的分销佣金
     */
    @RequestMapping(value = "queryMyCommission", method = RequestMethod.POST)
    public Page<MemberCommissionListVo> queryMyCommission(@RequestBody Pagination<MemberCommission> query) {
        Page<MemberCommissionListVo> pages = memberCommissionService.queryListVoForApp(query);
        return pages;
    }

}
