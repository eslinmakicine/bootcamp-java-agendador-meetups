package com.bootcamp.microservicemeetup.controller.dto;

import com.bootcamp.microservicemeetup.model.entity.Registration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMeetupDTO {

    private Integer id;

    private String event;

    private String meetupDate;

    private List<RegistrationDTO> registrations;


}