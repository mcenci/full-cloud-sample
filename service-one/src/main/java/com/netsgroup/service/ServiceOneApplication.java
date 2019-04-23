package com.netsgroup.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import com.netsgroup.feign.NgsFilterConfiguration;

@SpringBootApplication
@EnableDiscoveryClient
@Import(value = {
    NgsFilterConfiguration.class
})
public class ServiceOneApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServiceOneApplication.class, args);
  }



}

