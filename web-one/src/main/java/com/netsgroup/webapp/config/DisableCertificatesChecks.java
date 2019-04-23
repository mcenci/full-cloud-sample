package com.netsgroup.webapp.config;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;

public class DisableCertificatesChecks {

	protected final static Logger logger = LoggerFactory.getLogger(DisableCertificatesChecks.class);

	public static void disableAuthorizationCodeCertificateChecks(OAuth2RestTemplate oauthTemplate) throws Exception {

		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

		try {
			SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
					.loadTrustMaterial(null, acceptingTrustStrategy)
					.build();

			@SuppressWarnings("deprecation")
			SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext ,  SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			CloseableHttpClient httpClient = HttpClients.custom()
					.setSSLSocketFactory(csf)
					.setSSLHostnameVerifier(new NoopHostnameVerifier())
					.build();

			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

			requestFactory.setHttpClient(httpClient);
			//	        restTemplate.setRequestFactory(requestFactory);

			//This is for OAuth protected resources
			oauthTemplate.setRequestFactory(requestFactory);

			//AuthorizationCodeAccessTokenProvider creates it's own RestTemplate for token operations
			AuthorizationCodeAccessTokenProvider provider = new AuthorizationCodeAccessTokenProvider();
			provider.setRequestFactory(requestFactory);
			oauthTemplate.setAccessTokenProvider(provider);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			logger.error(e.getMessage());
		}		
	}
	
	public static void disableClientCredentialsCertificateChecks(OAuth2RestTemplate oauthTemplate) throws Exception {

		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

		try {
			SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
					.loadTrustMaterial(null, acceptingTrustStrategy)
					.build();

			@SuppressWarnings("deprecation")
			SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext ,  SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			CloseableHttpClient httpClient = HttpClients.custom()
					.setSSLSocketFactory(csf)
					.setSSLHostnameVerifier(new NoopHostnameVerifier())
					.build();

			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

			requestFactory.setHttpClient(httpClient);
			//	        restTemplate.setRequestFactory(requestFactory);

			//This is for OAuth protected resources
			oauthTemplate.setRequestFactory(requestFactory);

			//AuthorizationCodeAccessTokenProvider creates it's own RestTemplate for token operations
			ClientCredentialsAccessTokenProvider provider = new ClientCredentialsAccessTokenProvider();
			provider.setRequestFactory(requestFactory);
			oauthTemplate.setAccessTokenProvider(provider);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			e.printStackTrace();
		}		
	}
	
	static class SSLContextRequestFactory extends SimpleClientHttpRequestFactory {

		private final SSLContext sslContext;

		public SSLContextRequestFactory(SSLContext sslContext) {
			this.sslContext = sslContext;
		}

		@Override
		protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
			if (connection instanceof HttpsURLConnection) {
				((HttpsURLConnection) connection).setSSLSocketFactory(sslContext.getSocketFactory());
			}
			super.prepareConnection(connection, httpMethod);
		}
	}

	static class Dumb509TrustManager implements X509TrustManager {
		@Override
		public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
				throws CertificateException {

		}

		@Override
		public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
				throws CertificateException {

		}

		@Override
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}
}
