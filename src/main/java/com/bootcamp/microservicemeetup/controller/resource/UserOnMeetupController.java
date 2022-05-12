package com.bootcamp.microservicemeetup.controller.resource;

import com.bootcamp.microservicemeetup.controller.dto.*;
import com.bootcamp.microservicemeetup.controller.dto.UserOnMeetupDTO;
import com.bootcamp.microservicemeetup.model.entity.Meetup;
import com.bootcamp.microservicemeetup.model.entity.User;
import com.bootcamp.microservicemeetup.service.MeetupService;
import com.bootcamp.microservicemeetup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/userMeetup")
@RequiredArgsConstructor
public class UserOnMeetupController {

    private final MeetupService meetupService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseUserOnMeetupDTO create(@RequestBody UserOnMeetupDTO userOnMeetupDTO) {

        User user = userService
                .getUserByUserAttribute(userOnMeetupDTO.getUserAttribute())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must enter a valid user"));

        Meetup meetup = meetupService
                .findMeetupById(userOnMeetupDTO.getMeetupAttribute())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must enter a valid meetup"));
        user.setMeetup(meetup);
        meetup.getUser().add(user);
        meetupService.save(meetup);

        ResponseUserOnMeetupDTO entityDTO = ResponseUserOnMeetupDTO.builder()
                .userAttribute(userOnMeetupDTO.getUserAttribute())
                .meetupAttribute(userOnMeetupDTO.getMeetupAttribute())
                .build();

        return modelMapper.map(entityDTO, ResponseUserOnMeetupDTO.class);
    }

    @GetMapping
    public Page<ResponseMeetupDTO> find(Meetup dto, Pageable pageRequest) {
        Page<Meetup> result = meetupService.find(dto, pageRequest); //

        List<ResponseMeetupDTO> meetups = result
                .getContent()
                .stream()
                .map(entity -> {
                    List<User> users = entity.getUser();

                    List<UserDTO> userDTOS = users.stream()
                            .map(user -> modelMapper.map(user, UserDTO.class))
                            .collect(Collectors.toList());

                    ResponseMeetupDTO responseMeetupDTO = modelMapper.map(entity, ResponseMeetupDTO.class);
                    responseMeetupDTO.setRegistrations(userDTOS);

                    return responseMeetupDTO;
                }).collect(Collectors.toList());

        return new PageImpl<ResponseMeetupDTO>(meetups, pageRequest, result.getTotalElements());


    }

}
