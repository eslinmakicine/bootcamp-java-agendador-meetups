package com.bootcamp.microservicemeetup.controller.dto;

import com.bootcamp.microservicemeetup.model.entity.User;
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

    private Integer idMeetup;

    private String nameMeetup;

    private String meetupDate;

    private List<UserDTO> registrations;


}