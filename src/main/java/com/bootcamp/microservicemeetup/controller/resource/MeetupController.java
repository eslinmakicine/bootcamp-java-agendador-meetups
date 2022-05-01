package com.bootcamp.microservicemeetup.controller.resource;

import com.bootcamp.microservicemeetup.controller.dto.MeetupDTO;
import com.bootcamp.microservicemeetup.model.entity.Meetup;
import com.bootcamp.microservicemeetup.service.MeetupService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/meetups")
@RequiredArgsConstructor
public class MeetupController {

    private final MeetupService meetupService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MeetupDTO create(@RequestBody MeetupDTO meetupDTO) {

        Meetup entity = Meetup.builder()
                .event(meetupDTO.getEvent())
                .meetupDate(meetupDTO.getMeetupDate())
                .build();
        entity = meetupService.save(entity);
        return modelMapper.map(entity, MeetupDTO.class);
    }

    @GetMapping
    public Page<MeetupDTO> find(Meetup dto, Pageable pageRequest) {
        Page<Meetup> result = meetupService.find(dto, pageRequest);
        List<MeetupDTO> meetups = result
                .getContent()
                .stream()
                .map(entity -> {
                    MeetupDTO meetupDTO = modelMapper.map(entity, MeetupDTO.class);
                    return meetupDTO;
                }).collect(Collectors.toList());
        return new PageImpl<MeetupDTO>(meetups, pageRequest, result.getTotalElements());
    }
}
