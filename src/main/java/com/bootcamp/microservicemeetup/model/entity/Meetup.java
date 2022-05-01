package com.bootcamp.microservicemeetup.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Meetup {

    @Id
    @Column(name = "id_meetup")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String event;

    @Column
    private String meetupDate;

    @Column
    private Boolean registered;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Registration> registration;

}
