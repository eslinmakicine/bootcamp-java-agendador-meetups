package com.bootcamp.microservicemeetup.service.impl;

import com.bootcamp.microservicemeetup.service.RegistrationOnEventService;
import org.springframework.stereotype.Service;

@Service
public class RegistrationOnEventServiceImpl implements RegistrationOnEventService {
/*
    private RegistrationOnEventRepository repository;

    public RegistrationOnEventServiceImpl(RegistrationOnEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public RegistrationOnEvent save(RegistrationOnEvent registrationOnEvent) {
        return repository.save(registrationOnEvent);
    }

        @Override
        public Optional<RegistrationOnEvent> getById(Integer id) {
            return repository.findById(id);
        }

        @Override
        public RegistrationOnEvent update(RegistrationOnEvent registrationOnEvent) {
            return repository.save(registrationOnEvent);
        }

        @Override
        public Page<RegistrationOnEvent> find(MeetupFilterDTO filterDTO, Pageable pageable) {
           /* if (filterDTO.getRegistration() == null && filterDTO.getEvent() == null )  {
                return repository.findAll(pageable);
            }
            return repository.findAll(pageable);

            //return repository.findByRegistrationOnMeetup( filterDTO.getRegistration(), filterDTO.getEvent(), pageable );
        }

    public List<RegistrationOnEvent> findAll() {
        return repository.findAll();
    }
*/
}