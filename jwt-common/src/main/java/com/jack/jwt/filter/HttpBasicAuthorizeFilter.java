package com.jack.jwt.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jack.jwt.common.ResponseCode;
import com.jack.jwt.common.ResponseData;
import com.jack.jwt.utils.JWTUtils;
import com.jack.jwt.utils.JsonUtils;

/**
 * API 调用权限控制
 * 如果没的网关，就直接在每个服务里面注册本Filter来做权限控制
 */
public class HttpBasicAuthorizeFilter implements Filter {

	JWTUtils jwtUtils = JWTUtils.getInstance();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("application/json; charset=utf-8");
		String auth = httpRequest.getHeader("Authorization");
		// 验证TOKEN
		if (!hasText(auth)) {
			PrintWriter print = httpResponse.getWriter();
			print.write(JsonUtils.toJson(ResponseData.fail("非法请求【缺少Authorization信息】", ResponseCode.NO_AUTH_CODE.getCode())));
			return;
		}

		JWTUtils.JWTResult jwt = jwtUtils.checkToken(auth);
		if (!jwt.isStatus()) {
			PrintWriter print = httpResponse.getWriter();
			print.write(JsonUtils.toJson(ResponseData.fail(jwt.getMsg(), jwt.getCode())));
			return;
		}

		chain.doFilter(httpRequest, response);
	}

	@Override
	public void destroy() {

	}

	/**
	 * org.springframework.util.StringUtils.hasText 方法，copy过来，减少依赖
	 */
	public static boolean hasText(String str) {
		return str != null && !str.isEmpty() && containsText(str);
	}

	private static boolean containsText(CharSequence str) {
		int strLen = str.length();
		for(int i = 0; i < strLen; ++i) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

}
