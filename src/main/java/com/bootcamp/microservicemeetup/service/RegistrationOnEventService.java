package com.bootcamp.microservicemeetup.service;

        import com.bootcamp.microservicemeetup.controller.dto.MeetupFilterDTO;
        import com.bootcamp.microservicemeetup.model.entity.Meetup;
        import com.bootcamp.microservicemeetup.model.entity.Registration;
        import com.bootcamp.microservicemeetup.model.entity.RegistrationOnEvent;
        import org.springframework.data.domain.Page;
        import org.springframework.data.domain.Pageable;

        import java.util.List;
        import java.util.Optional;

public interface RegistrationOnEventService {

    RegistrationOnEvent save(RegistrationOnEvent registrationOnEvent);
/*
    Optional<RegistrationOnEvent> getById(Integer id);

    RegistrationOnEvent update(RegistrationOnEvent registrationOnEvent);
*/
    Page<RegistrationOnEvent> find(MeetupFilterDTO filterDTO, Pageable pageable);

    Page<RegistrationOnEvent> findByEventId(Integer eventAttribute, Pageable pageable);

    List<RegistrationOnEvent> findAll();

}
