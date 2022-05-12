package com.bootcamp.microservicemeetup.controller;

import com.bootcamp.microservicemeetup.controller.resource.UserController;
import com.bootcamp.microservicemeetup.exception.BusinessException;
import com.bootcamp.microservicemeetup.controller.dto.UserDTO;
import com.bootcamp.microservicemeetup.model.entity.User;
import com.bootcamp.microservicemeetup.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;


import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = {UserController.class})
@AutoConfigureMockMvc
public class UserControllerTest {

    static String USER_API = "/api/user";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    @DisplayName("Should create a user with success")
    public void createUserTest() throws Exception {

        UserDTO userDTOBuilder = createNewUser();
        User savedUser = User.builder().idUser(101)
                .nameUser("Ana Neri").dateRegistryUser("10/10/2021").userAttribute("001").build();

        BDDMockito.given(userService.saveUser(any(User.class))).willReturn(savedUser);


        String json  = new ObjectMapper().writeValueAsString(userDTOBuilder);


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders //simula reques
                .post(USER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("idUser").value(101))
                .andExpect(jsonPath("nameUser").value(userDTOBuilder.getNameUser()))
                .andExpect(jsonPath("dateRegistryUser").value(userDTOBuilder.getDateRegistryUser()))
                .andExpect(jsonPath("userAttribute").value(userDTOBuilder.getUserAttribute()));
    }

    @Test
    @DisplayName("Should throw an exception when not have date enough for the test.")
    public void createInvalidUserTest() throws Exception {

        String json  = new ObjectMapper().writeValueAsString(new UserDTO());

        MockHttpServletRequestBuilder request  = MockMvcRequestBuilders
                .post(USER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should throw an exception when try to create a new user with an userAttribute already created.")
    public void createUserWithDuplicatedUserAttribute() throws Exception {

        UserDTO dto = createNewUser();
        String json  = new ObjectMapper().writeValueAsString(dto);

        BDDMockito.given(userService.saveUser(any(User.class)))
                .willThrow(new BusinessException("User already created"));

        MockHttpServletRequestBuilder request  = MockMvcRequestBuilders
                .post(USER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0]").value("User already created"));
    }

    @Test
    @DisplayName("Should get user informations")
    public void getUserTest() throws Exception {

        Integer idUser = 11;

        User user = User.builder()
                .idUser(idUser)
                .nameUser(createNewUser().getNameUser())
                .dateRegistryUser(createNewUser().getDateRegistryUser())
                .userAttribute(createNewUser().getUserAttribute()).build();

        BDDMockito.given(userService.findUserById(idUser)).willReturn(Optional.of(user));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(USER_API.concat("/" + idUser))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("idUser").value(idUser))
                .andExpect(jsonPath("nameUser").value(createNewUser().getNameUser()))
                .andExpect(jsonPath("dateRegistryUser").value(createNewUser().getDateRegistryUser()))
                .andExpect(jsonPath("userAttribute").value(createNewUser().getUserAttribute()));

    }

    @Test
    @DisplayName("Should return NOT FOUND when the user doesn't exists")
    public void userNotFoundTest() throws Exception {

        BDDMockito.given(userService.findUserById(anyInt())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(USER_API.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("Should delete the user")
    public void deleteUser() throws Exception {

        BDDMockito.given(userService
                .findUserById(anyInt()))
                .willReturn(Optional.of(User.builder().idUser(11).build()));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(USER_API.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return resource not found when no user is found to delete")
    public void deleteNonExistentUserTest() throws Exception {

        BDDMockito.given(userService
                .findUserById(anyInt())).willReturn(Optional.empty());


        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(USER_API.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should update when user info")
    public void updateUserTest() throws Exception {

        Integer idUser = 11;
        String json = new ObjectMapper().writeValueAsString(createNewUser()); //ele precisa receber um json

        User updatingUser = //cria registro que será atualizado
                User.builder()
                .idUser(idUser)
                .nameUser("Julie Neri")
                .dateRegistryUser("10/10/2021")
                .userAttribute("323")
                .build();

        BDDMockito.given(userService.findUserById(anyInt()))
                .willReturn(Optional.of(updatingUser));

        User updatedUser =
                User.builder()
                        .idUser(idUser)
                        .nameUser("Ana Neri")
                        .dateRegistryUser("10/10/2021")
                        .userAttribute("323")
                        .build();

        BDDMockito.given(userService
                .updateUser(updatingUser))
                .willReturn(updatedUser);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(USER_API.concat("/" + 1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("idUser").value(idUser))
                .andExpect(jsonPath("nameUser").value(createNewUser().getNameUser()))
                .andExpect(jsonPath("dateRegistryUser").value(createNewUser().getDateRegistryUser()))
                .andExpect(jsonPath("userAttribute").value("323"));

    }

    @Test
    @DisplayName("Should return 404 when try to update an user no existent")
    public void updateNonExistentUserTest() throws Exception {

        String json = new ObjectMapper().writeValueAsString(createNewUser());
        BDDMockito.given(userService.findUserById(anyInt()))
                .willReturn(Optional.empty());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(USER_API.concat("/" + 1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should filter user")
    public void findUserTest() throws Exception {

        Integer idUser = 11;

        User user = User.builder()
                .idUser(idUser)
                .nameUser(createNewUser().getNameUser())
                .dateRegistryUser(createNewUser().getDateRegistryUser())
                .userAttribute(createNewUser().getUserAttribute()).build();

        BDDMockito.given(userService.findAllUsers(Mockito.any(User.class), Mockito.any(Pageable.class)) )
                .willReturn(new PageImpl<User>(Arrays.asList(user), PageRequest.of(0,100), 1));


        String queryString = String.format("?nameUser=%s&dateRegistryUser=%s&page=0&size=100",
                user.getUserAttribute(), user.getDateRegistryUser());


        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(USER_API.concat(queryString))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("content", hasSize(1))) //vai validar somente a parte de paginação
                .andExpect(jsonPath("totalElements"). value(1))
                .andExpect(jsonPath("pageable.pageSize"). value(100))
                .andExpect(jsonPath("pageable.pageNumber"). value(0));

    }


    private UserDTO createNewUser() {
        return  UserDTO.builder().idUser(101).nameUser("Ana Neri").dateRegistryUser("10/10/2021").userAttribute("001").build();
    }
}