package com.bootcamp.microservicemeetup.controller.resource;

import com.bootcamp.microservicemeetup.controller.dto.*;
import com.bootcamp.microservicemeetup.controller.dto.RegistrationOnEventDTO;
import com.bootcamp.microservicemeetup.model.entity.Meetup;
import com.bootcamp.microservicemeetup.model.entity.Registration;
import com.bootcamp.microservicemeetup.model.entity.RegistrationOnEvent;
import com.bootcamp.microservicemeetup.service.MeetupService;
import com.bootcamp.microservicemeetup.service.RegistrationOnEventService;
import com.bootcamp.microservicemeetup.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/registrationEvent")
@RequiredArgsConstructor
public class RegistrationOnEventController {

    private final MeetupService meetupService;
    private final RegistrationService registrationService;
    private final RegistrationOnEventService registrationOnEventService;

    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseRegistrationOnEventDTO create(@RequestBody RegistrationOnEventDTO registrationOnEventDTO) {

        Registration registration = registrationService
                .getRegistrationByRegistrationAttribute(registrationOnEventDTO.getRegistrationAttribute())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must enter a valid registration"));

        Meetup meetup = meetupService
                .getById(registrationOnEventDTO.getEventAttribute())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must enter a valid event"));
        registration.setMeetup(meetup);
        meetup.getRegistration().add(registration);
        meetupService.save(meetup);

        ResponseRegistrationOnEventDTO entityDTO = ResponseRegistrationOnEventDTO.builder()
                .registrationAttribute(registrationOnEventDTO.getRegistrationAttribute())
                .eventAttribute(registrationOnEventDTO.getEventAttribute())
                .build();

        return modelMapper.map(entityDTO, ResponseRegistrationOnEventDTO.class);
    }

    @GetMapping
    public Page<ResponseMeetupDTO> find(Meetup dto, Pageable pageRequest) {
        Page<Meetup> result = meetupService.find(dto, pageRequest); //

        List<ResponseMeetupDTO> meetups = result
                .getContent()
                .stream()
                .map(entity -> {
                    List<Registration> registrations = entity.getRegistration();

                    List<RegistrationDTO> registrationDTOS = registrations.stream()
                            .map(registration -> modelMapper.map(registration, RegistrationDTO.class))
                            .collect(Collectors.toList());

                    ResponseMeetupDTO responseMeetupDTO = modelMapper.map(entity, ResponseMeetupDTO.class);
                    responseMeetupDTO.setRegistrations(registrationDTOS);

                    return responseMeetupDTO;
                }).collect(Collectors.toList());

        return new PageImpl<ResponseMeetupDTO>(meetups, pageRequest, result.getTotalElements());


    }

}
