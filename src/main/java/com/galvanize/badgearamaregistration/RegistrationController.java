package com.galvanize.badgearamaregistration;

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
    public Person getPersobByPhone(@PathVariable String phoneNumber){
        return registrationService.getPersonByPhone(phoneNumber);}

}
