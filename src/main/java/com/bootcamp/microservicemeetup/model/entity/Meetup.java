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

/*
    @JoinColumn(name = "id_registration") //apelido para sabermos que é de registration, nao é obrigatorio o nome
    @ManyToOne          //estamps introduzindo a tabela id_registration no meetup pois sao dados em comum
                        // mtos meetups para 1 registro - 1 registro só pode ir em um meetup
    private Registration registration;

    @OneToMany(mappedBy = "registration")
    private List<Registration> registrations;
*/

    @Column
    private String meetupDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Registration> registration;

}
