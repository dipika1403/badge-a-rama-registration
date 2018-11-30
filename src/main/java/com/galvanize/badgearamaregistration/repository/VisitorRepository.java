package com.galvanize.badgearamaregistration.repository;

import com.galvanize.badgearamaregistration.entity.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepository extends CrudRepository<Person, Long> {

}
