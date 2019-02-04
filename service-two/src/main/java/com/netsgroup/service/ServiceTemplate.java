package com.netsgroup.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "web-service-1")
public interface ServiceTemplate {

	@GetMapping(path = "/items" , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	List<Item> items(); 
	
	@GetMapping(path = "/items/{name}" , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	Item item(@PathVariable(name = "name") String name);
}
