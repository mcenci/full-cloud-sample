package com.netsgroup.webapp.config;

import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
@EnableOAuth2Client
public class OauthClientConfiguration {

  @Bean
  public OAuth2ProtectedResourceDetails portale() {
      AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
      details.setId("portale_new");
      details.setClientId("portale_new");
      details.setClientSecret("123456");
      details.setAccessTokenUri("https://cloud.netsgroup.com:8443/uaa/oauth/token");
      details.setUserAuthorizationUri("https://cloud.netsgroup.com:8443/uaa/oauth/authorize");
      details.setTokenName("oauth_token");
      details.setScope(Arrays.asList("/portale/dummy"));
//      details.setPreEstablishedRedirectUri("https://cloud.netsgroup.com:8443/waa");
      details.setUseCurrentUri(false);
      return details;
  }

  @Bean
  public OAuth2RestTemplate netsRestTemplate(OAuth2ClientContext oauth2Context) throws Exception {
      OAuth2RestTemplate template = new OAuth2RestTemplate(portale(), oauth2Context);
      DisableCertificatesChecks.disableAuthorizationCodeCertificateChecks(template);
      return template;
  }
  
  @Autowired
  private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

  @PostConstruct
  public void init() {
     requestMappingHandlerAdapter.setIgnoreDefaultModelOnRedirect(true);
  }
  
}
