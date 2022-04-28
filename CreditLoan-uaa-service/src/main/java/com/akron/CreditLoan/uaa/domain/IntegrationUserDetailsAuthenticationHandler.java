package com.akron.CreditLoan.uaa.domain;

import com.akron.CreditLoan.api.account.model.AccountDTO;
import com.akron.CreditLoan.api.account.model.AccountLoginDTO;
import com.akron.CreditLoan.common.domain.RestResponse;
import com.akron.CreditLoan.common.util.StringUtil;
import com.akron.CreditLoan.uaa.agent.AccountApiAgent;
import com.akron.CreditLoan.uaa.common.utils.ApplicationContextHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class IntegrationUserDetailsAuthenticationHandler {

	/**
	 * 认证处理
	 * @param domain 用户域 ，如b端用户、c端用户等
	 * @param authenticationType  认证类型，如密码认证，短信认证等
	 * @param token
	 * @return
	 */
	public UnifiedUserDetails authentication(String domain, String authenticationType,
			UsernamePasswordAuthenticationToken token) {

		//1.从客户端取数据
		String username=token.getName();
		if(StringUtil.isBlank(username)){
			throw  new BadCredentialsException("账户为空");
		}
		if(token.getCredentials()==null){
			throw  new BadCredentialsException("密码为空");
		}
		String presentedPassword=token.getCredentials().toString();

		//2.远程调用统一账户服务，进行账户密码校验
		AccountLoginDTO accountLoginDTO=new AccountLoginDTO();
		accountLoginDTO.setDomain(domain);
		accountLoginDTO.setUsername(username);
		accountLoginDTO.setMobile(username);
		accountLoginDTO.setPassword(presentedPassword);
		AccountApiAgent accountApiAgent=(AccountApiAgent)ApplicationContextHelper.getBean(AccountApiAgent.class);
		RestResponse<AccountDTO> restResponse=accountApiAgent.login(accountLoginDTO);

		//3.异常处理
		if(restResponse.getCode()!=0){
			throw new BadCredentialsException("登录失败");
		}

		//4.登录成功，把用户数据封装到UnifiedUserDetails对象中
		UnifiedUserDetails unifiedUserDetails=new UnifiedUserDetails(restResponse.getResult().getUsername(),presentedPassword,AuthorityUtils.createAuthorityList());
		unifiedUserDetails.setMobile(restResponse.getResult().getMobile());
		return unifiedUserDetails;
		
	}

}
