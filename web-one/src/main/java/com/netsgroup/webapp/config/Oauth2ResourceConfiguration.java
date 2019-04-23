package com.netsgroup.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class Oauth2ResourceConfiguration  extends ResourceServerConfigurerAdapter {

  @Override
  public void configure(final HttpSecurity http) throws Exception {
    http
      .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
      .and()
      .authorizeRequests().antMatchers("/welcome*", "/v2/**").access("#oauth2.hasScope('FDSC/menu')").anyRequest().permitAll();

  }

  // JWT token store

  @Override
  public void configure(final ResourceServerSecurityConfigurer config) {
    config.tokenServices(tokenServices());
    config.resourceId("FDSC");
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(accessTokenConverter());
  }

  @Bean
  public CustomJwtAccesTokenConverter accessTokenConverter() {
      CustomJwtAccesTokenConverter converter = new CustomJwtAccesTokenConverter();
      converter.setSigningKey("michele1234567890michele1234567890");
      converter.setAccessTokenConverter(jwtAccessTokenConverterImpl());
      return converter;
  }
  
  @Bean
  public AccessTokenConverter jwtAccessTokenConverterImpl() {
      return new JwtAccessTokenConverterImpl();
  }
//  
  @Bean
  @Primary
  public DefaultTokenServices tokenServices() {
    final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore());
    return defaultTokenServices;
  }
}