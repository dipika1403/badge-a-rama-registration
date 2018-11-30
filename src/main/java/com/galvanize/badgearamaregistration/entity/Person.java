package com.galvanize.badgearamaregistration.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@Entity
@NoArgsConstructor
public class Person {

    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long phoneNumber;
    private String firstName;
    private String lastName;
    private String company;

    @Builder
    public Person(Long phoneNumber, String firstName, String lastName, String company) {
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
    }
}
