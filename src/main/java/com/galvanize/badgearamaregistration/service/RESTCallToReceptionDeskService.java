package com.galvanize.badgearamaregistration.service;

import com.galvanize.badgearamaregistration.entity.ExtendedPerson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class RESTCallToReceptionDeskService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RESTCallToReceptionDeskService.class);

    public ExtendedPerson getVisitorDetails(){
    RestTemplate restTemplate = new RestTemplate();
    ExtendedPerson extendedPerson = restTemplate.getForObject("http://localhost:8182/reception/random", ExtendedPerson.class);

    LOGGER.info(" ExtendedPerson :: "+extendedPerson.toString());
    return extendedPerson;
}
}
