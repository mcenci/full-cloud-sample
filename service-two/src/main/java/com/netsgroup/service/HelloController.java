package com.netsgroup.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @Value("${spring.application.name}")
  private String origin;

  @Autowired
  ServiceTemplate serviceTemplate;

  @GetMapping(path = "/items" , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public List<Item> items() {
    List<Item> list = new ArrayList<>();
    list.add(new Item("mauro", 36L, origin));
    list.add(new Item("fre" , 35L, origin));
    list.addAll(serviceTemplate.items());
    return list;
  }

  @GetMapping(path = "/items/{name}" , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Item item(@PathVariable(name = "name") String name) {
    return new Item(name, new Random().nextLong() , origin);
  }
}
