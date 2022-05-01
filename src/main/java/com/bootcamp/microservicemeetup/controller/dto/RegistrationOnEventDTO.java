package com.bootcamp.microservicemeetup.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationOnEventDTO {

    private Integer id;

    private String registrationAttribute;

    private Integer eventAttribute;

    private RegistrationDTO registration;

    private MeetupDTO meetup;

}
