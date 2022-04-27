package com.bootcamp.microservicemeetup.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class RegistrationOnEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String dateRegistry;

    @JoinColumn(name = "id_registration")
    @ManyToOne
    private Registration registration;

    @JoinColumn(name = "id_meetup")
    @ManyToOne
    private Meetup meetup;

    @Column
    private Boolean registeredEvent;

}
