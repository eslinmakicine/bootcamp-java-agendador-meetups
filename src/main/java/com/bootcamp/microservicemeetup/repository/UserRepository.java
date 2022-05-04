package com.bootcamp.microservicemeetup.repository;
import com.bootcamp.microservicemeetup.controller.dto.*;
import com.bootcamp.microservicemeetup.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUserAttribute(String userAttribute);

    Optional<User> findByUserAttribute(String userAttribute);
}
