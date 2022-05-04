package com.bootcamp.microservicemeetup.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOnMeetupDTO {

    private Integer id;

    private String userAttribute;

    private Integer meetupAttribute;

    private UserDTO user;

    private MeetupDTO meetup;

}
