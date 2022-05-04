package com.bootcamp.microservicemeetup.service;

import com.bootcamp.microservicemeetup.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    User saveUser(User any);

    Optional<User> findUserById(Integer id);

    void deleteUser(User user);

    User updateUser(User user);

    Page<User> findAllUsers(User filter, Pageable pageRequest);

    Optional<User> getUserByUserAttribute(String userAttribute);
}
