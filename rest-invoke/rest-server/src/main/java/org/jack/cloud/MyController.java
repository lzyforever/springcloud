package org.jack.cloud;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

	/**
	 * 查询方法，参数为Person的id
	 */
	@RequestMapping(value = "/person/{personId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Person findPerson(@PathVariable("personId") Integer personId,
			HttpServletRequest request) {
		Person p = new Person();
		p.setId(personId);
		p.setName("Jack Luo");
		p.setAge(30);
		p.setMessage(request.getRequestURL().toString());
		return p;
	}

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello() {
		return "Hello World";
	}

	/**
	 * 参数为JSON
	 */
	@RequestMapping(value = "/person/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String createPerson(@RequestBody Person person) {
		System.out.println(person.getName() + "-" + person.getAge());
		return "Success, Person Id: " + person.getId();
	}

	/**
	 * 参数与返回值均为XML
	 */
	@RequestMapping(value = "/person/createXML", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_XML_VALUE, 
			produces = MediaType.APPLICATION_XML_VALUE)
	public String createXMLPerson(@RequestBody Person person) {
		System.out.println(person.getName() + "-" + person.getId());
		return "<result><message>success</message></result>";
	}
}
