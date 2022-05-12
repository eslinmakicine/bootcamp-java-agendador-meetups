package com.bootcamp.microservicemeetup.service.impl;

import com.bootcamp.microservicemeetup.exception.BusinessException;
import com.bootcamp.microservicemeetup.model.entity.Meetup;
import com.bootcamp.microservicemeetup.repository.MeetupRepository;
import com.bootcamp.microservicemeetup.service.MeetupService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MeetupServiceImpl implements MeetupService {

    private MeetupRepository repository;

    public MeetupServiceImpl(MeetupRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meetup save(Meetup meetup) {
        return repository.save(meetup);
    }

    @Override
    public Meetup update(Meetup meetup) {
        return repository.save(meetup);
    }

    @Override
    public Page<Meetup> find(Meetup filter, Pageable pageable) {
        Example<Meetup> example = Example.of(filter,
                ExampleMatcher
                        .matching()
                        .withIgnoreCase()
                        .withIgnoreNullValues()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return repository.findAll(example, pageable);

    }

    @Override
    public void delete(Integer id) {
        this.repository.deleteById(id);
    }

    @Override
    public Optional<Meetup> findMeetupById(Integer id) {
        Optional<Meetup> result = repository.findById(id);
        if (!(result.isPresent())) {
            throw new BusinessException("You must inform a valid meetup id");
        }
        return result;
    }

}