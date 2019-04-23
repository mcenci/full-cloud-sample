package com.netsgroup.webapp.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import com.netsgroup.api.domain.UserObject;

public class CustomJwtAccesTokenConverter extends JwtAccessTokenConverter {

  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

    if(authentication.getPrincipal() instanceof CustomUser) {
      CustomUser user = (CustomUser) authentication.getPrincipal();
      UserObject uo = user.getUser();
      Map<String, Object> additionalInfo = new HashMap<>();
      additionalInfo.put("customer", uo.getCustid());
      additionalInfo.put("email", uo.getEmail());
      additionalInfo.put("user_id" ,uo.getUserId());
      ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
    }
    return super.enhance(accessToken, authentication);
  }

}
