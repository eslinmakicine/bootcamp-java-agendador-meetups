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
import org.springframework.web.server.ResponseStatusException;
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
    public MeetupDTO createMeetup(@RequestBody MeetupDTO meetupDTO) {

        Meetup entity = Meetup.builder()
                .event(meetupDTO.getEvent())
                .meetupDate(meetupDTO.getMeetupDate())
                .build();
        entity = meetupService.save(entity);
        return modelMapper.map(entity, MeetupDTO.class);
    }

    @GetMapping
    public Page<MeetupDTO> findAllMeetups(Meetup dto, Pageable pageRequest) {
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

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public MeetupDTO findMeetupById(@PathVariable Integer id) {
        Meetup result = meetupService.findMeetupById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return modelMapper.map(result, MeetupDTO.class);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMeetupById(@PathVariable Integer id) {
        Meetup meetup = meetupService.findMeetupById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        meetupService.delete(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public MeetupDTO updateMeetupById(@PathVariable Integer id,
                                 @RequestBody  MeetupDTO meetupDTO) {

        return meetupService.findMeetupById(id).map(meetup -> {
            meetup.setEvent(meetupDTO.getEvent());
            meetup.setMeetupDate(meetupDTO.getMeetupDate());
            meetup = meetupService.update(meetup);
            return modelMapper.map(meetup, MeetupDTO.class);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

}
