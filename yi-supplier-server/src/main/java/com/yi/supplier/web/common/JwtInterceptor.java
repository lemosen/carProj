package com.yi.supplier.web.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.yi.supplier.web.auth.jwt.JwtSupplierToken;

/**
 * JWT 拦截器
 *
 * @author xuyh
 *
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

	private static final Logger LOG = LoggerFactory.getLogger(JwtInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 不拦截预检请求
		if (RequestMethod.OPTIONS.name().equals(request.getMethod())) {
			return true;
		}
		// 从请求头或参数中获取token
		String token = getTokenFromRequest(request);
		if (StringUtils.isBlank(token)) {
			response.setStatus(HttpStatus.SC_FORBIDDEN);
			return false;
		}
		try {
			boolean flag = JwtSupplierToken.verifyToken(token);
			if (!flag) {
				response.setStatus(HttpStatus.SC_FORBIDDEN);
				return false;
			}
			// token = JwtWebToken.updateToken(token);
			response.setHeader("Access-Control-Expose-Headers", JwtSupplierToken.TOKEN_HEADER);
			response.setHeader(JwtSupplierToken.TOKEN_HEADER, token);
			return true;
		} catch (Exception ex) {
			LOG.error("=====>JWT令牌处理失败", ex);
			response.setStatus(HttpStatus.SC_FORBIDDEN);
			return false;
		}
	}

	/**
	 * 从请求信息中获取token值
	 *
	 * @param request
	 *            request
	 * @return token值
	 */
	private String getTokenFromRequest(HttpServletRequest request) {
		// 默认从header里获取token值
		String token = request.getHeader(JwtSupplierToken.TOKEN_HEADER);
		if (StringUtils.isBlank(token)) {
			// 从请求信息中获取token值
			token = request.getParameter(JwtSupplierToken.TOKEN_HEADER);
		}
		return token;
	}

}
