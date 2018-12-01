package com.galvanize.badgearamaregistration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.badgearamaregistration.entity.ExtendedPersonFrontEnd;
import com.galvanize.badgearamaregistration.entity.Person;
import com.galvanize.badgearamaregistration.service.VisitorService;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import org.mockito.Mockito;


@RunWith(SpringRunner.class)
@WebMvcTest(value = VisitorController.class, secure = false)
public class VisitorControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(VisitorControllerTest.class);
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitorService service;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Test
    public void register() throws Exception {
        //Person person = Person.builder().firstName("Heena").lastName("Patel").company("cognizant").build();
        ExtendedPersonFrontEnd person = ExtendedPersonFrontEnd.builder().firstName("Heena")
                .lastName("Patel")
                .company("Cognizant")
                .phoneNumber("2014545555").build();

       // Person savedPerson = Person.builder().phoneNumber(2034545555L).firstName("Heena").lastName("Patel").company("cognizant").build();

        when(service.register(person)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/visitor/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsBytes(person))
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andReturn();
        verify(service, times(1)).register(person);


    }


}
