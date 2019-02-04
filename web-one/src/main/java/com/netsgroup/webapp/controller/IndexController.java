package com.netsgroup.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.netsgroup.webapp.client.ServiceOneTemplate;
import com.netsgroup.webapp.client.ServiceTwoTemplate;

@Controller
public class IndexController {

	protected static Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	ServiceOneTemplate serviceOneTemplate;
	
	@Autowired
	ServiceTwoTemplate serviceTwoTemplate;
	
	@GetMapping("/")
	public String index(Model model) {
		logger.info("retrieving information from webservice 2");
		model.addAttribute("s2Items", serviceTwoTemplate.items());
		logger.info("retrieving information from webservice 1");
		model.addAttribute("s1Items", serviceOneTemplate.items());
		return "index";
	}
}
