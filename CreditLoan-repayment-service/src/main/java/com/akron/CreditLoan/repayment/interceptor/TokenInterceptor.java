package com.akron.CreditLoan.repayment.interceptor;

import com.akron.CreditLoan.common.util.EncryptUtil;
import com.akron.CreditLoan.repayment.model.LoginUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <P>
 * Token 拦截处理
 * </p>
 */
public class TokenInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
		String jsonToken = httpServletRequest.getParameter("jsonToken");
		if (StringUtils.isNotBlank(jsonToken)) {
			LoginUser loginUser = JSON
					.parseObject(EncryptUtil.decodeUTF8StringBase64(jsonToken), new TypeReference<LoginUser>() {
					});

			httpServletRequest.setAttribute("jsonToken", loginUser);
		}

		return true;
	}

}
