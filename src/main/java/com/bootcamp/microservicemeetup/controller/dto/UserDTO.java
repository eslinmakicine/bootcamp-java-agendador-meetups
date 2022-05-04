package com.bootcamp.microservicemeetup.controller.dto;

import com.bootcamp.microservicemeetup.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Integer idUser;

    @NotEmpty
    private String nameUser;

    @NotEmpty
    private String dateRegistryUser;

    @NotEmpty
    private String userAttribute;


}
