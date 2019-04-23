package com.netsgroup.service;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

  //Determines the timeout in milliseconds until a connection is established.
  private static final int CONNECT_TIMEOUT = 30000;

  // The timeout when requesting a connection from the connection manager.
  private static final int REQUEST_TIMEOUT = 30000;

  // The timeout for waiting for data
  private static final int SOCKET_TIMEOUT = 60000;

  private static final Logger LOGGER = LoggerFactory.getLogger(ServiceOneApplication.class);

  @Autowired
  CloseableHttpClient httpClient;

  @Bean
  public RestTemplateBuilder restTemplate() {
    RestTemplateCustomizer rtc = new RestTemplateCustomizer() {

      @Override
      public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(clientHttpRequestFactory());

      }
    };
    RestTemplateBuilder rtb = new RestTemplateBuilder(rtc);
    rtb.build();
    //      RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
    return rtb;
  }

  @Bean
  public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
    clientHttpRequestFactory.setHttpClient(httpClient);
    return clientHttpRequestFactory;
  }

  @Bean
  public CloseableHttpClient httpClient() throws KeyManagementException, NoSuchAlgorithmException {

    RequestConfig requestConfig = RequestConfig.custom()
        .setConnectionRequestTimeout(REQUEST_TIMEOUT)
        .setConnectTimeout(CONNECT_TIMEOUT)
        .setSocketTimeout(SOCKET_TIMEOUT).build();

    SSLConnectionSocketFactory sslsf = null;
    try {
      // Create a trust manager that does not validate certificate chains
      TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
        public X509Certificate[] getAcceptedIssuers(){return null;}
        public void checkClientTrusted(X509Certificate[] certs, String authType){}
        public void checkServerTrusted(X509Certificate[] certs, String authType){}
      }};

      SSLContext sc = SSLContext.getInstance("TLS");
      sc.init(null, trustAllCerts, new SecureRandom());
      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
      sslsf = new SSLConnectionSocketFactory(sc , new HostnameVerifier() {

        @Override
        public boolean verify(String hostname, SSLSession session) {
          LOGGER.info("hostname is {}", hostname);
          return true;
        }

      });
    } catch (KeyManagementException | NoSuchAlgorithmException e) {
      LOGGER.error("Pooling Connection Manager Initialisation failure because of " + e.getMessage(), e);
    }
    return HttpClients.custom()
        .setDefaultRequestConfig(requestConfig)
        .setSSLSocketFactory(sslsf)
        .build();
  }
}
