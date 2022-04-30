package com.bootcamp.microservicemeetup.controller.resource;

import com.bootcamp.microservicemeetup.controller.dto.MeetupDTO;
import com.bootcamp.microservicemeetup.controller.dto.MeetupFilterDTO;
import com.bootcamp.microservicemeetup.controller.dto.RegistrationDTO;
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
    private ResponseEntity<Void> create(@RequestBody RegistrationOnEventDTO registrationOnEventDTO) {

        Registration registration = registrationService
                .getRegistrationByRegistrationAttribute(registrationOnEventDTO.getRegistrationAttribute())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Meetup meetup = meetupService
                .getById(registrationOnEventDTO.getEventAttribute())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        registration.setMeetup(meetup);
        meetup.getRegistration().add(registration);
        meetupService.save(meetup);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public List<Meetup> find(MeetupFilterDTO dto, Pageable pageRequest) {
        List<Meetup> result = meetupService.findAll();

        return result;

    }


}
