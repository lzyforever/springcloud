package com.jack.mongodb.repository;

import com.jack.entity.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户Repository
 */
@Repository
public interface PersonRepository extends CrudRepository<Person, String> {
}
