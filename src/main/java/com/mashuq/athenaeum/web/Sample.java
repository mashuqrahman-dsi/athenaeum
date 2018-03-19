package com.mashuq.athenaeum.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Sample {

	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}

}