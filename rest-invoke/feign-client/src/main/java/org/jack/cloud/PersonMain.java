package org.jack.cloud;

import org.jack.cloud.PersonClient.Person;

import feign.Feign;
import feign.gson.GsonDecoder;

/**
 * Person服务的运行主类
 *
 */
public class PersonMain {

	public static void main(String[] args) {
		PersonClient personService = Feign.builder()
				.decoder(new GsonDecoder())
				.target(PersonClient.class, "http://localhost:8080/");
		Person person = personService.findById(2);
		System.out.println(person.id);
		System.out.println(person.name);
		System.out.println(person.age);
		System.out.println(person.message);
	}
}
