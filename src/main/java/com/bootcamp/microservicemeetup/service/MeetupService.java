package com.bootcamp.microservicemeetup.service;

import com.bootcamp.microservicemeetup.controller.dto.MeetupFilterDTO;
import com.bootcamp.microservicemeetup.model.entity.Meetup;
import com.bootcamp.microservicemeetup.model.entity.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MeetupService {

    Meetup save(Meetup meetup); //em registration é (Meetup any)

    Optional<Meetup> getById(Integer id);
    //em registration tem delete
    Meetup update(Meetup meetup);

    Page<Meetup> find(MeetupFilterDTO filterDTO, Pageable pageable); //em registration nao usa DTO

    List<Meetup> findAll(); //em registration nao usa DTO

}
