package com.bootcamp.microservicemeetup.model.entity;

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
    private Integer idMeetup;

    @Column
    private String nameMeetup;

    @Column
    private String dateMeetup;

    @Column
    private Boolean registered;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> user;

}
