package com.yi.webserver.web.coupon;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.domain.entity.Coupon;
import com.yi.core.activity.domain.vo.CouponListVo;
import com.yi.core.activity.service.ICouponReceiveService;
import com.yi.core.activity.service.ICouponService;
import com.yi.core.member.service.IMemberService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.json.Result;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 优惠券
 */
@RestController
@RequestMapping(value = "/coupon")
public class CouponController {

	private final Logger LOG = LoggerFactory.getLogger(CouponController.class);

	@Resource
	private ICouponService couponService;

	@Resource
	private ICouponReceiveService couponReceiveService;

	@Resource
	private IMemberService memberService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public RestResult queryCoupon(@RequestBody Pagination<Coupon> query) {
		try {
			Page<CouponListVo> page = couponService.queryListVoForApp(query);
			return new RestResult(Result.SUCCESS, "查询成功", page);
		} catch (Exception e) {
			LOG.error("queryCoupon error{}", e.getMessage(), e);
			return new RestResult(Result.FAILURE, e.getMessage(), null);
		}
	}

	/**
	 * 领取优惠券
	 **/
	@RequestMapping(value = "receiveCoupon", method = RequestMethod.GET)
	public RestResult receiveCoupon(@RequestParam("couponId") Integer couponId, @RequestParam("memberId") Integer memberId) {
		try {
			return RestUtils.successWhenNotNull(couponReceiveService.receiveCoupon(couponId, memberId));
		} catch (BusinessException ex) {
			LOG.error("receive Coupon failure：couponId={}, memberId={}", couponId, memberId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取优惠券详情
	 * 
	 * @param couponId
	 * @return
	 */
	@RequestMapping(value = "getCouponDetail", method = RequestMethod.GET)
	public RestResult getAddressDetail(@RequestParam("couponId") int couponId) {
		try {
			return RestUtils.success(couponService.getCouponDetail(couponId));
		} catch (Exception e) {
			LOG.error("getCouponDetail error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

}
