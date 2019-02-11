package com.yi.webserver.web.common;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.common.SmsService;
import com.yi.core.common.SmsTemplateEnum;
import com.yi.core.member.service.IMemberService;
import com.yihz.common.json.RestResult;
import com.yihz.common.utils.RandomUtils;
import com.yihz.common.utils.web.RestUtils;

/**
 * 短信发送
 * 
 * @author xuyh
 *
 */
@RestController
@RequestMapping("sms")
public class SmsController {

	private final Logger LOG = LoggerFactory.getLogger(SmsController.class);

	@Resource
	private SmsService SmsService;

	@Resource
	private IMemberService memberService;

	/**
	 * 发送 注册验证码
	 * 
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "sendRegisterCode", method = RequestMethod.GET)
	public RestResult sendRegisterCode(@RequestParam("phone") String phone) {
		try {
			if (StringUtils.isBlank(phone)) {
				return RestUtils.error("手机号不能为空");
			}
			// 校验 手机号是否注册
			int count = memberService.checkByPhone(phone);
			if (count > 0) {
				LOG.error("短信发送失败，手机号{}，已经注册", phone);
				RestUtils.error("您已注册，请您登录");
			}
			Map<String, String> params = new HashMap<>();
			String code = RandomUtils.randomString(4, RandomUtils.RANDRULE.RAND_NUMBER);
			params.put("code", code);
			SmsService.sendSms(SmsTemplateEnum.USER_REGISTER.getCode(), phone, params);
			return RestUtils.success(code);
		} catch (Exception e) {
			LOG.error("短信发送失败", e);
			return RestUtils.error(e.getMessage() + "register error");
		}
	}

	/**
	 * 发送修改密码验证码
	 * 
	 * @param phone
	 *            change_password
	 * @return
	 */
	@RequestMapping(value = "sendChangePwdCode", method = RequestMethod.GET)
	public RestResult sendChangePwdCode(@RequestParam("phone") String phone) {
		try {
			if (StringUtils.isBlank(phone)) {
				return RestUtils.error("手机号不能为空");
			}
			Map<String, String> params = new HashMap<>();
			String code = RandomUtils.randomString(4, RandomUtils.RANDRULE.RAND_NUMBER);
			params.put("code", code);
			SmsService.sendSms(SmsTemplateEnum.CHANGE_PASSWORD.getCode(), phone, params);
			return RestUtils.success(code);
		} catch (Exception e) {
			LOG.error("短信发送失败", e);
			return RestUtils.error(e.getMessage() + "register error");
		}
	}

	/**
	 * 发送登录验证码
	 * 
	 * @param phone
	 *            change_password
	 * @return
	 */
	@RequestMapping(value = "sendLoginCode", method = RequestMethod.GET)
	public RestResult sendLoginCode(@RequestParam("phone") String phone) {
		try {
			if (StringUtils.isBlank(phone)) {
				return RestUtils.error("手机号不能为空");
			}
			Map<String, String> params = new HashMap<>();
			String code = RandomUtils.randomString(4, RandomUtils.RANDRULE.RAND_NUMBER);
			params.put("code", code);
			SmsService.sendSms(SmsTemplateEnum.LOGIN_CONFIRM.getCode(), phone, params);
			return RestUtils.success(code);
		} catch (Exception e) {
			LOG.error("短信发送失败", e);
			return RestUtils.error(e.getMessage() + "register error");
		}
	}

}
