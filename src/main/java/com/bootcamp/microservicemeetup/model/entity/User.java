package com.bootcamp.microservicemeetup.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

//as anotações abaixo servem para não ser necessário criar get, set, construtores com a ajuda de lombok...
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
@ToString(exclude = "meetup")
public class User {

    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

    @Column(name = "name_user")
    private String nameUser;

    @Column(name = "date_registry_user")
    private String dateRegistryUser;

    @Column
    private String userAttribute;

    @JsonIgnore
    @JoinColumn(name = "id_meetup")
    @ManyToOne
    private Meetup meetup;
}
