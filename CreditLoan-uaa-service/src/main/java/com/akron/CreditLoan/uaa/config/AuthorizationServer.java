package com.akron.CreditLoan.uaa.config;


import com.akron.CreditLoan.uaa.domain.CustomJdbcClientDetailsService;
import com.akron.CreditLoan.uaa.service.OauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends
		AuthorizationServerConfigurerAdapter {

	@Autowired
	private TokenStore tokenStore;

	@Autowired//生成令牌
	private JwtAccessTokenConverter accessTokenConverter;
	

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	private AuthorizationCodeServices authorizationCodeServices;


	@Autowired
	private OauthService oauthService;

	@Autowired
	private AuthenticationManager authenticationManager;


	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

    @Bean//设置数据源，从数据库uaa拿到authorizationCode
    public ClientDetailsService clientDetailsService(DataSource dataSource) {
		ClientDetailsService clientDetailsService = new CustomJdbcClientDetailsService(dataSource);
		((CustomJdbcClientDetailsService) clientDetailsService).setPasswordEncoder(passwordEncoder());
        return clientDetailsService;
    }
    
    @Bean//令牌服务
   	public AuthorizationServerTokenServices tokenService() {
       	DefaultTokenServices service=new DefaultTokenServices();
       	service.setClientDetailsService(clientDetailsService);//客户端信息服务
       	service.setSupportRefreshToken(true);//是否刷新令牌
   		service.setTokenStore(tokenStore);//令牌存储策略
		//令牌增强，采用JWT
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter));
   		service.setTokenEnhancer(tokenEnhancerChain);

   		service.setAccessTokenValiditySeconds(7200); // 令牌默认有效期2小时
   		service.setRefreshTokenValiditySeconds(259200); // 刷新令牌默认有效期3天
   		return service;
    }
    
    
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        return new JdbcAuthorizationCodeServices(dataSource);
    }
    
    @Override//1.客户端详情配置，谁来申请令牌
	public void configure(ClientDetailsServiceConfigurer clients)
			throws Exception {
		 clients.withClientDetails(clientDetailsService);
	}
  
    
    @Bean
    public OAuth2RequestFactory oAuth2RequestFactory() {
        return new DefaultOAuth2RequestFactory(clientDetailsService);
    }
    
    
    
    @Bean
    public UserApprovalHandler userApprovalHandler() {
        OauthUserApprovalHandler userApprovalHandler = new OauthUserApprovalHandler();
        userApprovalHandler.setOauthService(oauthService);
        userApprovalHandler.setTokenStore(tokenStore);
        userApprovalHandler.setClientDetailsService(this.clientDetailsService);
        userApprovalHandler.setRequestFactory(oAuth2RequestFactory());
        return userApprovalHandler;
    }

	@Override//2、令牌访问端点和令牌管理服务,url
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.authenticationManager(authenticationManager)//密码模式需要
				//.userDetailsService(userDetailsService)// 若无，refresh_token会有UserDetailsService	 is required错误
				.authorizationCodeServices(authorizationCodeServices)//授权码模式需要
				.userApprovalHandler(userApprovalHandler())
				.tokenServices(tokenService())//令牌管理服务
			    .pathMapping("/oauth/confirm_access", "/confirm_access")
				.pathMapping("/oauth/error", "/oauth_error")
				.allowedTokenEndpointRequestMethods(HttpMethod.POST)
				.exceptionTranslator(new RestOAuth2WebResponseExceptionTranslator());
	}
	
	@Bean
    public TokenEnhancer tokenEnhancer(){
        return (accessToken, authentication) -> {
			DefaultOAuth2AccessToken token= (DefaultOAuth2AccessToken) accessToken;
			Map<String, Object> additionalInformation = new LinkedHashMap<>();
			additionalInformation.put("code",0);
			additionalInformation.put("msg","success");
			token.setAdditionalInformation(additionalInformation);
			return accessToken;
		};
    }

	@Override//3、令牌访问端点的安全策略
	public void configure(AuthorizationServerSecurityConfigurer security)
			throws Exception {
		security
		.tokenKeyAccess("permitAll()")
		.checkTokenAccess("permitAll()")
		.allowFormAuthenticationForClients()//允许表单认证
		;
	}

	
}
