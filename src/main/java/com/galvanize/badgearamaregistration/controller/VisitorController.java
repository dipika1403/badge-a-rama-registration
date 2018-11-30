package com.galvanize.badgearamaregistration.controller;

import com.galvanize.badgearamaregistration.entity.ExtendedPersonFrontEnd;
import com.galvanize.badgearamaregistration.service.VisitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visitor")
@ControllerAdvice
public class VisitorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VisitorController.class);
    @Autowired
    VisitorService visitorService;

    @PostMapping("/register")
    public Boolean register(@RequestBody ExtendedPersonFrontEnd person) {
        return visitorService.register(person);
    }

    @GetMapping("/lookup/{phoneNumber}")
    public ExtendedPersonFrontEnd getPersonByPhone(@PathVariable String phoneNumber){
        return visitorService.getPersonByPhone(phoneNumber);
    }
        // Host Checkin Visitor - change status to "IN" by Host through MQ in visitor table
    // Visitor check out - change status to "Out" by MQ in Visitor table

    @PutMapping("/checkout")
    public String setVisitorCheckOut(@RequestBody ExtendedPersonFrontEnd extendedPersonFrontEnd){
        return visitorService.setVisitorCheckOut(extendedPersonFrontEnd);
    }



}
