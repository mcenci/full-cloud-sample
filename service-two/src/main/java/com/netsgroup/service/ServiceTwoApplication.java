package com.netsgroup.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import com.netsgroup.feign.http.RestTemplateConfiguration;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Import(value = {
    RestTemplateConfiguration.class
})
public class ServiceTwoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceTwoApplication.class, args);
	}

}

