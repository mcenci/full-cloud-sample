package com.netsgroup.webapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.debug(true);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
      http
        .httpBasic().disable()
        .logout()
          .invalidateHttpSession(true)
          .deleteCookies("JSESSIONID");
//      http
//      .headers()
//          .frameOptions().sameOrigin()
//          
//          .and()
//          
//          .anonymous().disable()
//          .authorizeRequests()
//          .antMatchers("/welcome").authenticated()
//      .and()
//          .httpBasic()
//          .authenticationEntryPoint(oauth2AuthenticationEntryPoint())    
//      .and()
//          .csrf()
//              .disable()
//          .cors()
//      ;

  }

  private LoginUrlAuthenticationEntryPoint oauth2AuthenticationEntryPoint() {
      return new LoginUrlAuthenticationEntryPoint("/login");
  }
}
