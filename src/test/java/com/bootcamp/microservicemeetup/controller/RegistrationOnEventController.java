package com.bootcamp.microservicemeetup.controller;

import com.bootcamp.microservicemeetup.controller.dto.MeetupDTO;
import com.bootcamp.microservicemeetup.model.entity.Meetup;
import com.bootcamp.microservicemeetup.model.entity.Registration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegistrationOnEventController {

//
//    @Test
//    @DisplayName("Should register on a meetup")
//    public void createMeetupTest() throws Exception {
//
//        // quando enviar uma requisicao pra esse registration precisa ser encontrado um valor que tem esse usuario
//        MeetupDTO dto = MeetupDTO.builder().registrationAttribute("123").event("Womakerscode Dados").build();
//        String json = new ObjectMapper().writeValueAsString(dto);
//
//        Registration registration = Registration.builder().id(11).registration("123").build(); //como so quer verificar se ele existe, nao precisa trazer tds as informações
//
//        BDDMockito.given(registrationService.getRegistrationByRegistrationAttribute("123")). //simula a request
//                willReturn(Optional.of(registration));
//
//        Meetup meetup = Meetup.builder().id(11).event("Womakerscode Dados").registration(registration).meetupDate("10/10/2021").build(); //objeto que vamos utilizar pra fazer a requisição
//
//        BDDMockito.given(meetupService.save(Mockito.any(Meetup.class))).willReturn(meetup);
//
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(MEETUP_API) //mocka a requisição para simular essa requisição
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json);
//
//        mockMvc.perform(request)
//                .andExpect(status().isCreated())
//                .andExpect(content().string("11"));
//
//    }

//    ////incluir teste para a classe RegistrationOnEvent
//    @Test
//    @DisplayName("Should return error when try to register an a meetup nonexistent")
//    public void invalidRegistrationCreateMeetupTest() throws Exception {
//
//        MeetupDTO dto = MeetupDTO.builder().registrationAttribute("123").event("Womakerscode Dados").build();
//        String json = new ObjectMapper().writeValueAsString(dto);
//
//        BDDMockito.given(registrationService.getRegistrationByRegistrationAttribute("123")).
//                willReturn(Optional.empty());
//
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(MEETUP_API)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json);
//
//        mockMvc.perform(request)
//                .andExpect(status().isBadRequest());
//
//    }


////incluir teste para a classe RegistrationOnEvent
//    @Test
//    @DisplayName("Should return error when try to register a registration already register on a meetup")
//    public void  meetupRegistrationErrorOnCreateMeetupTest() throws Exception {
//
//        MeetupDTO dto = MeetupDTO.builder().registrationAttribute("123").event("Womakerscode Dados").build();
//        String json = new ObjectMapper().writeValueAsString(dto);
//
//        Registration registration = Registration.builder().id(11).name("Ana Neri").registration("123").build();
//        BDDMockito.given(registrationService.getRegistrationByRegistrationAttribute("123"))
//                .willReturn(Optional.of(registration));
//
//        BDDMockito.given(meetupService.save(Mockito.any(Meetup.class))).willThrow(new BusinessException("Meetup already enrolled"));
//
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(MEETUP_API)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json);
//
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isBadRequest());
//    }


}
