package com.bootcamp.microservicemeetup.service;

import com.bootcamp.microservicemeetup.exception.BusinessException;
import com.bootcamp.microservicemeetup.model.entity.User;
import com.bootcamp.microservicemeetup.repository.UserRepository;
import com.bootcamp.microservicemeetup.service.impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {

    UserService userService;

    @MockBean
    UserRepository repository;

    @BeforeEach
    public void setUp() {
        this.userService = new UserServiceImpl(repository);
    }

    @Test
    @DisplayName("Should save an user")
    public void saveUser() {

        User user = createValidUser();

        Mockito.when(repository.existsByUserAttribute(Mockito.anyString())).thenReturn(false);
        Mockito.when(repository.save(user)).thenReturn(createValidUser());

        User savedUser = userService.saveUser(user);

        assertThat(savedUser.getIdUser()).isEqualTo(101);
        assertThat(savedUser.getNameUser()).isEqualTo("Éslin Makicine");
        assertThat(savedUser.getDateRegistryUser()).isEqualTo("01/04/2022");
        assertThat(savedUser.getUserAttribute()).isEqualTo("001");
    }

    @Test
    @DisplayName("Should throw business error when thy " +
            "to save a new user with a user duplicated")
    public void shouldNotSAveAsUserDuplicated() {

        User user = createValidUser();
        Mockito.when(repository.existsByUserAttribute(Mockito.any())).thenReturn(true);

        Throwable exception = Assertions.catchThrowable( () -> userService.saveUser(user));
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage("User already created");

        Mockito.verify(repository, Mockito.never()).save(user);
    }

    @Test
    @DisplayName("Should get an User by Id")
    public void getByUserIdTest() {

        Integer idUser = 11;
        User user = createValidUser();
        user.setIdUser(idUser);
        Mockito.when(repository.findById(idUser)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findUserById(idUser);

        assertThat(foundUser.isPresent()).isTrue();
        assertThat(foundUser.get().getIdUser()).isEqualTo(idUser);
        assertThat(foundUser.get().getNameUser()).isEqualTo(user.getNameUser());
        assertThat(foundUser.get().getDateRegistryUser()).isEqualTo(user.getDateRegistryUser());
        assertThat(foundUser.get().getUserAttribute()).isEqualTo(user.getUserAttribute());
    }

    @Test
    @DisplayName("Should return empty when get an user by id when doesn't exists")
    public void userNotFoundByIdTest() {

        Integer id = 11;
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<User> user  = userService.findUserById(id);

        assertThat(user.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Should delete an user")
    public void deleteUserTest() {

        User user = User.builder().idUser(11).build();

        assertDoesNotThrow(() -> userService.deleteUser(user));

        Mockito.verify(repository, Mockito.times(1)).delete(user);
    }

    @Test
    @DisplayName("Should update an user")
    public void updateUser() {

        Integer idUser = 11;
        User updatingUser = User.builder().idUser(11).build();

        User updatedUser = createValidUser();
        updatedUser.setIdUser(idUser);

        Mockito.when(repository.save(updatingUser)).thenReturn(updatedUser);
        User user = userService.updateUser(updatingUser);

        assertThat(user.getIdUser()).isEqualTo(updatedUser.getIdUser());
        assertThat(user.getNameUser()).isEqualTo(updatedUser.getNameUser());
        assertThat(user.getDateRegistryUser()).isEqualTo(updatedUser.getDateRegistryUser());
        assertThat(user.getUserAttribute()).isEqualTo(updatedUser.getUserAttribute());

    }

    @Test
    @DisplayName("Should filter users must by properties")
    public void findUserTest() {

        User user = createValidUser();
        PageRequest pageRequest = PageRequest.of(0,10);

        List<User> listUsers = Arrays.asList(user);
        Page<User> page = new PageImpl<User>(Arrays.asList(user),
                PageRequest.of(0,10), 1);

        Mockito.when(repository.findAll(Mockito.any(Example.class), Mockito.any(PageRequest.class)))
                .thenReturn(page);

        Page<User> result = userService.findAllUsers(user, pageRequest);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).isEqualTo(listUsers);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);
    }

    @Test
    @DisplayName("Should get an User model by user attribute")
    public void getUserByUserAttribute() {

        String userAttribute = "1234";

        Mockito.when(repository.findByUserAttribute(userAttribute))
                .thenReturn(Optional.of(User.builder().idUser(11).userAttribute(userAttribute).build()));

        Optional<User> user  = userService.getUserByUserAttribute(userAttribute);

        assertThat(user.isPresent()).isTrue();
        assertThat(user.get().getIdUser()).isEqualTo(11);
        assertThat(user.get().getUserAttribute()).isEqualTo(userAttribute);

        Mockito.verify(repository, Mockito.times(1)).findByUserAttribute(userAttribute);

    }


    private User createValidUser() {
        return User.builder()
                .idUser(101)
                .nameUser("Éslin Makicine")
                .dateRegistryUser("01/04/2022")
                .userAttribute("001")
                .build();
    }


}
