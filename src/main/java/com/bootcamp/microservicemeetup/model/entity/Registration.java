package com.bootcamp.microservicemeetup.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

//as anotações abaixo servem para não ser necessário criar get, set, construtores com a ajuda de lombok...
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class Registration {

    @Id
    @Column(name = "registration_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "person_name")
    private String name;

    @Column(name = "date_of_registration")
    private String dateOfRegistration;

    @Column
    private String registration; //seria como se fosse o versionamento. Se houver alguma alteração nesse objeto, esse atributo irá mudar

    //um registro para mts meetups
    @OneToMany(mappedBy = "registration")
    private List<Meetup> meetups;

    @OneToMany(mappedBy = "registration")
    private List<RegistrationOnEvent> registrationsEvents;
}
