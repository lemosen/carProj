package com.yi.admin.web.auth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yi.admin.web.auth.jwt.AdminToken;
import com.yi.admin.web.auth.jwt.JwtAdminToken;
import com.yi.admin.web.auth.service.ILoginService;
import com.yi.core.auth.AuthEnum;
import com.yi.core.auth.domain.entity.User;
import com.yi.core.auth.domain.vo.UserVo;
import com.yi.core.auth.model.RestLoginResult;
import com.yi.core.auth.model.SessionData;
import com.yi.core.auth.service.IUserService;
import com.yihz.common.json.Result;

@Component
public class LoginService implements ILoginService {

	private final Logger LOG = LoggerFactory.getLogger(LoginService.class);

	@Autowired
	private IUserService userService;

	@Override
	public SessionData getSessionDataByToken(AdminToken token) {
		final User dbUser = userService.getUserById(token.getId());
		if (dbUser == null) {
			return null;
		}
		SessionData sessionData = new SessionData();
		sessionData.setId(dbUser.getId());
		sessionData.setUserName(dbUser.getUsername());
		sessionData.setAvatar(dbUser.getAvatar());
		return sessionData;
	}

	/**
	 * 通过TOKEN登录
	 */
	@Override
	public RestLoginResult loginByToken(String token) {
		AdminToken tk = JwtAdminToken.getToken(token);
		if (tk == null) {
			return new RestLoginResult(Result.FAILURE, "无法识别的JWT令牌内容");
		}
		final User dbUser = userService.getUserById(tk.getId());
		if (dbUser == null) {
			return new RestLoginResult(Result.FAILURE, "员工资料已经不存在,无法登陆");
		}

		// 封装sessionData
		SessionData sessionData = new SessionData();
		sessionData.setId(dbUser.getId());
		sessionData.setUserName(dbUser.getUsername());
		sessionData.setAvatar(dbUser.getAvatar());
		// 封装返回数据
		RestLoginResult restLoginResult = new RestLoginResult(Result.SUCCESS, "登录成功", dbUser);
		restLoginResult.setLoginUser(dbUser);
		restLoginResult.setSessionData(sessionData);
		return restLoginResult;
	}

	/**
	 * 登录
	 */
	@Override
	public RestLoginResult login(String username, String password) {
		UserVo dbUser = userService.login(username, password);
		if (dbUser == null) {
			LOG.error("用户名或密码错误，username={}", username);
			return new RestLoginResult(Result.FAILURE, "用户名或密码错误");
		}
		if (AuthEnum.STATE_DISABLE.getCode().equals(dbUser.getState())) {
			LOG.error("用户已被禁用，username=", username);
			return new RestLoginResult(Result.FAILURE, "您已被禁用，请联系管理员处理");
		}
		// 封装sessionData数据
		SessionData sessionData = new SessionData();
		sessionData.setId(dbUser.getId());
		sessionData.setUserName(dbUser.getUsername());
		sessionData.setAvatar(dbUser.getAvatar());
		// 封装返回数据
		RestLoginResult restLoginResult = new RestLoginResult(Result.SUCCESS, "登录成功", dbUser);
		restLoginResult.setLoginUser(dbUser);
		restLoginResult.setSessionData(sessionData);
		return restLoginResult;
	}

}
