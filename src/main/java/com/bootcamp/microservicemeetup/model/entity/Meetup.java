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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String event;

    @JoinColumn(name = "id_registration") //apelido para sabermos que é de registration, nao é obrigatorio o nome
    @ManyToOne          //estamps introduzindo a tabela id_registration no meetup pois sao dados em comum
                        // mtos meetups para 1 registro - 1 registro só pode ir em um meetup
    private Registration registration;

    @Column
    private String meetupDate;

    @Column
    private Boolean registered;

    @OneToMany(mappedBy = "registration")
    private List<RegistrationOnEvent> registrationsEvents;
}
