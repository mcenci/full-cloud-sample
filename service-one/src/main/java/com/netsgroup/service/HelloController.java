package com.netsgroup.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
public class HelloController {
	
	@Value("${spring.application.name}")
	private String origin;
	
	@GetMapping(path = "/items" , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Item> items(WebRequest request) {
		List<Item> list = new ArrayList<>();
		list.add(new Item("michele", 35L , origin));
		list.add(new Item("fede" , 34L , origin));
		
		Iterator<String> headerNames = request.getHeaderNames();
		while (headerNames.hasNext()) {
          String hn = (String) headerNames.next();
          System.out.println(hn + " - " + request.getHeader(hn));
          
        }
		return list;
	}
	
	@GetMapping(path = "/items/{name}" , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Item item(@PathVariable(name = "name") String name) {
		return new Item(name, new Random().nextLong(), origin);
	}
}
