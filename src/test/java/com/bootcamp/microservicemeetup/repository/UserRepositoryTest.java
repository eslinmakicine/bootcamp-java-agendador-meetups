package com.bootcamp.microservicemeetup.repository;

import com.bootcamp.microservicemeetup.model.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class UserRepositoryTest {


    @Autowired
    TestEntityManager entityManager;

    @Autowired
    UserRepository repository;


    @Test
    @DisplayName("Should return true when exists an user already created.")
    public void returnTrueWhenUserExists() {

        String userAttribute = "123";

        User user = createNewUser(userAttribute);
        entityManager.persist(user);

        boolean exists = repository.existsByUserAttribute(userAttribute);

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should return false when doesn't exists an userAttribute with a user already created.")
    public void returnFalseWhenUserAttributeDoesntExists() {

        String userAttribute = "123";

        boolean exists = repository.existsByUserAttribute(userAttribute);

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should get an user by id")
    public void findByIdTest() {

        User user = createNewUser("323");
        entityManager.persist(user);

        Optional<User> foundUser = repository
                .findById(user.getIdUser());

        assertThat(foundUser.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should save an user")
    public void saveUserTest() {

        User user = createNewUser("323");

        User savedUser = repository.save(user);

        assertThat(savedUser.getIdUser()).isNotNull();
    }

    @Test
    @DisplayName("Should delete and user from the base")
    public void deleteUser() {

        User user = createNewUser("323");
        entityManager.persist(user);

        User foundUser = entityManager
                .find(User.class, user.getIdUser());
        repository.delete(foundUser);

        User deleteUser = entityManager
                .find(User.class, user.getIdUser());

        assertThat(deleteUser).isNull();
    }

    public static User createNewUser(String userAttribute) {
        return User.builder()
                .nameUser("Éslin Makicine")
                .dateRegistryUser("10/10/2021")
                .userAttribute(userAttribute).build();
    }
}
