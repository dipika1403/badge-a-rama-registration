package com.galvanize.badgearamaregistration.controller;

import com.galvanize.badgearamaregistration.entity.ExtendedPersonFrontEnd;
import com.galvanize.badgearamaregistration.entity.Person;
import com.galvanize.badgearamaregistration.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visitor")
@ControllerAdvice
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @PostMapping("/register")
    public Boolean register(@RequestBody ExtendedPersonFrontEnd person) {
        return registrationService.register(person);
    }

    @GetMapping("/lookup/{phoneNumber}")
    public ExtendedPersonFrontEnd getPersonByPhone(@PathVariable String phoneNumber){
        return registrationService.getPersonByPhone(phoneNumber);}


        // Host Checkin Visitor - change status to "IN" by Host through MQ in visitor table


    // Visitor check out - change status to "Out" by MQ in Visitor table

    @PutMapping("/checkout")
    public String setVisitorCheckOut(@RequestBody ExtendedPersonFrontEnd extendedPersonFrontEnd){

        return registrationService.setVisitorCheckOut(extendedPersonFrontEnd);
    }

    @PutMapping("/checkin")
    public String setVisitorCheckIn(@RequestBody ExtendedPersonFrontEnd extendedPersonFrontEnd){

        return registrationService.setVisitorCheckIn(extendedPersonFrontEnd);
    }
}
