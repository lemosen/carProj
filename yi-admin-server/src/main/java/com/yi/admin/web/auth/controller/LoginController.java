package com.yi.admin.web.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.admin.web.auth.jwt.JwtAdminToken;
import com.yi.admin.web.auth.service.ILoginService;
import com.yi.core.auth.model.RestLoginResult;
import com.yi.core.auth.model.SessionData;
import com.yi.core.auth.session.ThreadLocalSession;
import com.yihz.common.annotation.session.LoginRequired;
import com.yihz.common.json.RestResult;
import com.yihz.common.json.Result;
import com.yihz.common.utils.web.RestUtils;

/**
 * @author aidy
 * @version 1.0
 * @since 1.0 本controller 类里面的接口均无需登录
 **/
@RestController
@RequestMapping(value = "/auth")
@LoginRequired(required = false)
public class LoginController {

	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

	@Value("${yi.session.data.key:YI_SESSION_DATA}")
	private String sessionDataKey;

	@Autowired
	private ILoginService loginService;

	/**
	 * 登录
	 **/
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public RestLoginResult login(@RequestParam(required = false) String username,
			@RequestParam(required = false) String password, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isAnyBlank(username, password)) {
			return new RestLoginResult(Result.FAILURE, "请输入用户名或密码");
		}
		RestLoginResult restLoginResult = loginService.login(username, password);
		if (restLoginResult.getResult() == Result.SUCCESS) {
			ThreadLocalSession.setSessionData(restLoginResult.getSessionData());
			restLoginResult.setToken(generateJwtToken(restLoginResult.getSessionData()));
			response.setHeader(JwtAdminToken.TOKEN_HEADER, restLoginResult.getToken());

			LOG.info("用户登录成功, sessionData={}", restLoginResult.getSessionData());
		}
		return restLoginResult;
	}

	/**
	 * 员工重新登录
	 **/
	@RequestMapping(value = "loginByToken", method = RequestMethod.POST)
	public RestLoginResult loginByToken(@RequestParam(name = "token", required = false) String token,
			HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isBlank(token)) {
			return new RestLoginResult(Result.FAILURE, "参数（token）不能为空");
		}
		RestLoginResult restLoginResult = loginService.loginByToken(token);
		if (restLoginResult.getResult() == Result.SUCCESS) {
			ThreadLocalSession.setSessionData(restLoginResult.getSessionData());
			restLoginResult.setToken(generateJwtToken(restLoginResult.getSessionData()));
			response.setHeader(JwtAdminToken.TOKEN_HEADER, restLoginResult.getToken());
			LOG.info("用户重新登录成功, sessionData={}", restLoginResult.getSessionData());
		}
		return restLoginResult;
	}

	/**
	 * 登出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "logout", method = RequestMethod.POST)
	public RestResult logout(HttpServletRequest request, HttpServletResponse response) {
		SessionData sessionData = ThreadLocalSession.getSessionData();
		if (sessionData != null) {
			// 采用了 JWT 令牌, 没有必要了
			ThreadLocalSession.setSessionData(null);
			LOG.info("用户注销成功, sessionData={}", sessionData);
		}
		HttpSession httpSession = request.getSession(false);
		if (httpSession != null) {
			httpSession.invalidate();
		}
		return RestUtils.success();
	}

	/**
	 * 根据登陆信息生成JWT令牌
	 *
	 * @param sessionData
	 * @return
	 */
	private String generateJwtToken(SessionData sessionData) {
		return JwtAdminToken.generateToken(sessionData.getId(), sessionData.getUserCode(), null, null);
	}

}