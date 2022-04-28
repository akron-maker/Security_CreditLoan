package com.akron.CreditLoan.uaa.config;


import com.akron.CreditLoan.uaa.domain.ClientDefaultAccessTokenConverter;
import com.akron.CreditLoan.uaa.domain.UnifiedUserAuthenticationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class JWTConfig {


  private String SIGNING_KEY = "credit";

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY); //对称秘钥，资源服务器使用该秘钥来解密
        ClientDefaultAccessTokenConverter accessTokenConverter = new ClientDefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new UnifiedUserAuthenticationConverter());
        converter.setAccessTokenConverter(accessTokenConverter);
        return converter;
    }

}
