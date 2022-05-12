package com.bootcamp.microservicemeetup.repository;

import com.bootcamp.microservicemeetup.model.entity.Meetup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetupRepository extends JpaRepository<Meetup, Integer> {

}
