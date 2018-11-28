package com.galvanize.badgearamaregistration;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
    public Person findByPhoneNumber(Long phoneNumber);
}
