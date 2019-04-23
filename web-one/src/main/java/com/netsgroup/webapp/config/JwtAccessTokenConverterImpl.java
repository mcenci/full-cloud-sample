package com.netsgroup.webapp.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

public class JwtAccessTokenConverterImpl extends DefaultAccessTokenConverter {

	final static String NBF = "nbf";
	
	final static String IAT = "iat";
	
	final static String CLAIM_NAME_AGE = "age";

    final static String CLAIM_NAME_LAST_ACCESS_TIME = "lat";
    
	@Override
	public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		Map<String, ?> result =  super.convertAccessToken(token, authentication);
		Map<String, Object> response = new HashMap<String, Object>();
		response.putAll(result);
		if (token.getExpiration() != null) {
			response.put(NBF, token.getExpiration().getTime());
			response.put(IAT, token.getExpiration().getTime());
			response.put(CLAIM_NAME_LAST_ACCESS_TIME, token.getExpiration().getTime());
			response.put(CLAIM_NAME_AGE, token.getExpiresIn());
		}
		return response;
	}

}
