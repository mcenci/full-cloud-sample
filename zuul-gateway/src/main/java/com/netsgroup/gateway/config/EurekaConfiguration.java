package com.netsgroup.gateway.config;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl.EurekaJerseyClientBuilder;

@ConditionalOnProperty(name = "server.ssl.enabled" , matchIfMissing = true)
@Configuration
public class EurekaConfiguration {
  
  @Value("${server.ssl.key-store}")
  private String keyStorePath;

  @Value("${server.ssl.key-store-password}")
  private String keyStorePassword;

  @Value("${server.ssl.trust-store}")
  private String trustStorePath;

  @Value("${server.ssl.trust-store-password}")
  private String trustStorePassword;
  
  @Bean
  public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws NoSuchAlgorithmException, KeyManagementException {
    DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
    System.setProperty("javax.net.ssl.keyStore", keyStorePath.split(":")[1]);
    System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
    System.setProperty("javax.net.ssl.trustStore", trustStorePath.split(":")[1]);
    System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
    
    // Create a trust manager that does not validate certificate chains
    TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
        public X509Certificate[] getAcceptedIssuers(){return null;}
        public void checkClientTrusted(X509Certificate[] certs, String authType){}
        public void checkServerTrusted(X509Certificate[] certs, String authType){}
    }};
    
    SSLContext sc = SSLContext.getInstance("TLS");
    sc.init(null, trustAllCerts, new SecureRandom());
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    EurekaJerseyClientBuilder builder = new EurekaJerseyClientBuilder();
    builder.withClientName("client");
    builder.withCustomSSL(sc);
    builder.withMaxTotalConnections(10);
    builder.withMaxConnectionsPerHost(10);
    
    args.setEurekaJerseyClient(builder.build());
    
    return args;
  }
}
