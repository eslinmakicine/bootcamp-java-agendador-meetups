package com.bootcamp.microservicemeetup.repository;

import com.bootcamp.microservicemeetup.model.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Integer> {

    boolean existsByRegistration(String registration); //metodo pra validar se a versão ja foi criada, para garantir que nao tenha valores duplicados

    Optional<Registration> findByRegistration(String registrationAtrb);
}
