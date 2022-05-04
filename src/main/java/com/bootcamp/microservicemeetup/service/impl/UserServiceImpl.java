package com.bootcamp.microservicemeetup.service.impl;

import com.bootcamp.microservicemeetup.exception.BusinessException;
import com.bootcamp.microservicemeetup.model.entity.User;
import com.bootcamp.microservicemeetup.repository.UserRepository;
import com.bootcamp.microservicemeetup.service.UserService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    public User saveUser(User user) {
        if (repository.existsByUserAttribute(user.getUserAttribute())) {
            throw new BusinessException("User already created");
        }
        return repository.save(user);
    }

    @Override
    public Optional<User> findUserById(Integer id) {
        return this.repository.findById(id);
    }

    // inserir mais uma validacao no delete();
    @Override
    public void deleteUser(User user) {
        if (user == null || user.getIdUser() == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }
        this.repository.delete(user);
    }

    // inserir mais uma validacao no save();
    @Override
    public User updateUser(User user) {
        if (user == null || user.getIdUser() == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }
        return this.repository.save(user);
    }

    @Override
    public Page<User> findAllUsers(User filter, Pageable pageRequest) {
        Example<User> example = Example.of(filter,
                ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return repository.findAll(example, pageRequest);
    }

    @Override
    public Optional<User> getUserByUserAttribute(String userAttribute) {
        return repository.findByUserAttribute(userAttribute);
   }
}
