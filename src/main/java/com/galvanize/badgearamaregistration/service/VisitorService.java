package com.galvanize.badgearamaregistration.service;

import com.galvanize.badgearamaregistration.entity.ExtendedPerson;
import com.galvanize.badgearamaregistration.entity.ExtendedPersonFrontEnd;
import com.galvanize.badgearamaregistration.entity.Person;
import com.galvanize.badgearamaregistration.exception.GuestNotFoundException;
import com.galvanize.badgearamaregistration.exception.StatusUpdateFailureException;
import com.galvanize.badgearamaregistration.repository.VisitorRepository;
import com.galvanize.badgearamaregistration.utility.VisitStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VisitorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VisitorService.class);

    @Autowired
    VisitorRepository personRepository;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${amqp.exchange.name}")
    private String appExchangeName;

    @Value("${amqp.routing.key}")
    private String appRoutingKey;

    // New Visitor
    @Transactional
    public Boolean register(ExtendedPersonFrontEnd personFrontEnd) {
        ExtendedPerson extendedPerson = transformFrontEndPerson(personFrontEnd);
        personRepository.save(Person.builder()
                .phoneNumber(extendedPerson.getPhoneNumber())
                .firstName(extendedPerson.getFirstName())
                .lastName(extendedPerson.getLastName())
                .company(extendedPerson.getCompany())
                .build());

        sendMessage(appExchangeName, appRoutingKey, extendedPerson);
        return true;
    }

    public ExtendedPerson transformFrontEndPerson(ExtendedPersonFrontEnd personFE) {
        return ExtendedPerson.builder()
                .phoneNumber(Long.parseLong(personFE.getPhoneNumber().replaceAll("[^0-9]", "")))
                .firstName(personFE.getFirstName())
                .lastName(personFE.getLastName())
                .company(personFE.getCompany())
                .hostName(personFE.getHostName())
                .hostPhoneNumber(Long.parseLong(personFE.getHostPhone().replaceAll("[^0-9]", "")))
                .purposeOfVisit(personFE.getPurposeOfVisit())
                .reasonForDeletion(personFE.getReasonForDeletion())
                .badgeNumber(personFE.getBadgeNumber())
                .active(personFE.getActive())
                .status(personFE.getStatus())
                .build();
    }

    public ExtendedPersonFrontEnd transformPersonToFrontEndPerson(Person person) {
        return ExtendedPersonFrontEnd.builder()
                .phoneNumber(person.getPhoneNumber().toString())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .company(person.getCompany()).build();

    }


    // Returning Visitor : get visitor by phonenumber
    // ?? Not sure how to return - HTTPStatus --> 404, if not successful

    @Transactional
    public ExtendedPersonFrontEnd getPersonByPhone(String phoneNumber) {
        Long phone = Long.parseLong(phoneNumber.replaceAll("[^0-9]", ""));
        if (!personRepository.existsById(phone)) {
            throw new GuestNotFoundException(phone);
        }

        ExtendedPersonFrontEnd transformedPerson = transformPersonToFrontEndPerson(personRepository.findById(phone).get());
        return transformedPerson;
    }

    @Transactional
    public String setVisitorCheckOut(ExtendedPersonFrontEnd extendedPersonFrontEnd) {
        String response = UpdateExtendedPersonFrontEnd(extendedPersonFrontEnd , "OUT");
        return response;

    }




    public void sendMessage(String exchange, String routingKey, Object data) {
        LOGGER.info("Sending message to the queue using routingKey {}. Message= {}", routingKey, data);
        rabbitTemplate.convertAndSend(exchange, routingKey, data);
        LOGGER.info("The message has been sent to the queue.");
    }

    public String UpdateExtendedPersonFrontEnd(ExtendedPersonFrontEnd extendedPersonFrontEnd, String status) {
        String phoneNumber = extendedPersonFrontEnd.getPhoneNumber();
        Long phone = Long.parseLong(phoneNumber.replaceAll("[^0-9]", ""));
        if (!personRepository.existsById(phone)) {
            throw new GuestNotFoundException(phone);
        }
        if (status.equals("IN")) {
            extendedPersonFrontEnd.setStatus(VisitStatus.IN);
        }
        else if (status.equals("OUT")) {
            extendedPersonFrontEnd.setStatus(VisitStatus.OUT);
        }else if (status.equals("WAITING")) {
            extendedPersonFrontEnd.setStatus(VisitStatus.WAITING);
        }
        else {
            status = "UNVERIFIED";
        }

        ExtendedPerson extendedPerson = transformFrontEndPerson(extendedPersonFrontEnd);
        try {
            sendMessage(appExchangeName, appRoutingKey, extendedPerson);

            return String.format("Status changed to %s",status) ;
        }
        catch(StatusUpdateFailureException ex){
            return "";      // As per Ray's front end wants to consume.
        }
    }

}
