package com.jack.cloud;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
	
	@GetMapping("/person")
	public Person getPerson(HttpServletRequest request) {
		Person person = new Person();
		person.setId(1);
		person.setName("jack");
		person.setMessage(request.getRequestURL().toString());
		return person;
	}
}
