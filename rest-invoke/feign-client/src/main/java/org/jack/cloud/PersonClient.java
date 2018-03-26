package org.jack.cloud;

import lombok.Data;
import feign.Param;
import feign.RequestLine;

/**
 * Person�ͻ��˷�����
 * 
 */
public interface PersonClient {

	@RequestLine("GET /person/{personId}")
	Person findById(@Param("personId") Integer personId);

	@Data
	class Person {
		Integer id;
		String name;
		Integer age;
		String message;
	}
}