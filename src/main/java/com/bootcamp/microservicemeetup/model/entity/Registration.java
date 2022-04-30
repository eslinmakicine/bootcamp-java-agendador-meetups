package com.bootcamp.microservicemeetup.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

//as anotações abaixo servem para não ser necessário criar get, set, construtores com a ajuda de lombok...
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
@ToString(exclude = "meetup")
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
    private String registration;

/*
    @OneToMany(mappedBy = "registration")
    private List<Meetup> meetups;
*/
    @JsonIgnore
    @JoinColumn(name = "id")
    @ManyToOne
    private Meetup meetup;
}
