package com.bootcamp.microservicemeetup.repository;

import com.bootcamp.microservicemeetup.model.entity.Meetup;
import com.bootcamp.microservicemeetup.model.entity.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MeetupRepository extends JpaRepository<Meetup, Integer> {
   // SELECT u FROM User u WHERE u.status = :status and u.name = :name
    @Query( value = " select l from Meetup l where l.event = :event ")
    Page<Meetup> findByRegistrationOnMeetup(
  //          @Param("registration") String registration,
            @Param("event") String event,
            Pageable pageable
    );

}
