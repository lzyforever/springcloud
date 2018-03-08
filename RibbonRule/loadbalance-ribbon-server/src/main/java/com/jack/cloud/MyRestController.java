package com.jack.cloud;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {
	
	@RequestMapping(value="/person/{id}", method= RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Person getPerson(HttpServletRequest request) {
		Person person = new Person();
		person.setId(1);
		person.setAge(99);
		person.setName("ะกร๗");
		person.setMessage(request.getRequestURL().toString());
		return person;
	}
}
