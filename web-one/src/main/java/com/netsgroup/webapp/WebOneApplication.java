package com.netsgroup.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import com.netsgroup.feign.CustomFeignClientConfiguration;
import com.netsgroup.feign.NgsFilterConfiguration;
import com.netsgroup.feign.http.RestTemplateConfiguration;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Import(value = {
    RestTemplateConfiguration.class,
    NgsFilterConfiguration.class,
    CustomFeignClientConfiguration.class
})
public class WebOneApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebOneApplication.class, args);
	}

}

