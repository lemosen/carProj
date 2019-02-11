package com.yi.supplier.web.auth.service.impl;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yi.core.auth.model.RestLoginResult;
import com.yi.core.auth.model.SessionData;
import com.yi.core.supplier.SupplierEnum;
import com.yi.core.supplier.domain.entity.Supplier;
import com.yi.core.supplier.domain.vo.SupplierVo;
import com.yi.core.supplier.service.ISupplierService;
import com.yi.supplier.web.auth.jwt.SupplierToken;
import com.yi.supplier.web.auth.jwt.JwtSupplierToken;
import com.yi.supplier.web.auth.service.ILoginService;
import com.yihz.common.json.Result;

@Component
public class LoginService implements ILoginService {

	private final Logger LOG = LoggerFactory.getLogger(LoginService.class);

	/**
	 * 缓存登录供应商
	 */
	private ConcurrentHashMap<Integer, SupplierVo> CACHE_MAP = new ConcurrentHashMap<Integer, SupplierVo>(10);

	@Autowired
	private ISupplierService supplierService;

	/**
	 * 
	 */
	@Override
	public SessionData getSessionDataByToken(SupplierToken token) {
		final Supplier dbSupplier = supplierService.getSupplierById(token.getId());
		if (dbSupplier == null) {
			return null;
		}
		SessionData sessionData = new SessionData();
		sessionData.setId(dbSupplier.getId());
		sessionData.setUserName(dbSupplier.getPhone());
		return sessionData;
	}

	/**
	 * 通过TOKEN登录
	 */
	@Override
	public RestLoginResult loginByToken(String token) {
		SupplierToken tk = JwtSupplierToken.getToken(token);
		if (tk == null) {
			return new RestLoginResult(Result.FAILURE, "无法识别的JWT令牌内容");
		}
		final Supplier dbSupplier = supplierService.getSupplierById(tk.getId());
		if (dbSupplier == null) {
			return new RestLoginResult(Result.FAILURE, "供应商资料已经不存在,无法登陆");
		}
		// 封装sessionData数据
		SessionData sessionData = new SessionData();
		sessionData.setId(dbSupplier.getId());
		sessionData.setUserName(dbSupplier.getPhone());
		// 封装返回数据
		RestLoginResult restLoginResult = new RestLoginResult(Result.SUCCESS, "登录成功", dbSupplier);
		restLoginResult.setLoginUser(dbSupplier);
		restLoginResult.setSessionData(sessionData);
		return restLoginResult;
	}

	/**
	 * 供应商登录
	 */
	@Override
	public RestLoginResult login(String username, String password) {
		SupplierVo dbSupplier = supplierService.login(username, password);
		if (dbSupplier == null) {
			LOG.error("用户名或密码错误，username={}", username);
			return new RestLoginResult(Result.FAILURE, "用户名或密码错误");
		}
		if (SupplierEnum.STATE_DISABLE.getCode().equals(dbSupplier.getState())) {
			LOG.error("用户已被禁用，username=", username);
			return new RestLoginResult(Result.FAILURE, "您已被禁用，请联系管理员处理");
		}
		// 封装sessionData数据
		SessionData sessionData = new SessionData();
		sessionData.setId(dbSupplier.getId());
		sessionData.setUserName(dbSupplier.getPhone());
		// 封装返回数据
		RestLoginResult restLoginResult = new RestLoginResult(Result.SUCCESS, "登录成功", dbSupplier);
		restLoginResult.setLoginUser(dbSupplier);
		restLoginResult.setSessionData(sessionData);
		//
		CACHE_MAP.put(dbSupplier.getId(), dbSupplier);
		return restLoginResult;
	}

	/**
	 * 获取当前登录的 供应商
	 */
	@Override
	public SupplierVo getLoginSupplier(SupplierToken token) {
		if (CACHE_MAP.containsKey(token.getId())) {
			return CACHE_MAP.get(token.getId());
		}
		return supplierService.getSupplierVoById(token.getId());
	}

}
