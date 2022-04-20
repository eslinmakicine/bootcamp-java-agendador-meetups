package com.bootcamp.microservicemeetup.repository;

import com.bootcamp.microservicemeetup.model.entity.Registration;

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
@DataJpaTest //para simular comportamento de banco de dados
public class RegistrationRepositoryTest {


    @Autowired
    TestEntityManager entityManager; //é uma classe junto do JPA que conseguimos fazer simulações de teste. Simula varios comportamentos do contexto de teste

    @Autowired
    RegistrationRepository repository;


    @Test
    @DisplayName("Should return true when exists an registration already created.")
    public void returnTrueWhenRegistrationExists() {

        String registration = "123";

        Registration registration_Class_attribute = createNewRegistration(registration);
        entityManager.persist(registration_Class_attribute); //metodo persist é pra persistir (gravar o objeto no banco de dados) o objeto que foi criado no banco de dados

        boolean exists = repository.existsByRegistration(registration);

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should return false when doesn't exists an registration_attribute with a registration already created.")
    public void returnFalseWhenRegistrationAttributeDoesntExists() {

        String registration = "123";

        boolean exists = repository.existsByRegistration(registration); //como ele nao foi persistido, ele vai vir como false

        assertThat(exists).isFalse();

    }

    @Test
    @DisplayName("Should get an registration by id")
    public void findByIdTest() {

        Registration registration_Class_attribute = createNewRegistration("323");
        entityManager.persist(registration_Class_attribute);

        Optional<Registration> foundRegistration = repository
                .findById(registration_Class_attribute.getId());

        assertThat(foundRegistration.isPresent()).isTrue();

    }

    @Test
    @DisplayName("Should save an registration")
    public void saveRegistrationTest() {

        Registration registration_Class_attribute = createNewRegistration("323");

        Registration savedRegistration = repository.save(registration_Class_attribute); //aqui simula o save do banco de dados

        assertThat(savedRegistration.getId()).isNotNull(); //valida que nao está vazio, senao nao vai conseguir salvar

    }

    @Test
    @DisplayName("Should delete and registration from the base")
    public void deleteRegistation() {

        Registration registration_Class_attribute = createNewRegistration("323");
        entityManager.persist(registration_Class_attribute);

        Registration foundRegistration = entityManager
                .find(Registration.class, registration_Class_attribute.getId()); //dessa classe do registration, traga o id
        repository.delete(foundRegistration); //ao ter o id, delete o registro

        Registration deleteRegistration = entityManager
                .find(Registration.class, registration_Class_attribute.getId()); //chamo o registro que deletei anteriormente

        assertThat(deleteRegistration).isNull(); //garanto que ele é nulo, pra confirmar que foi excluido

    }


    public static Registration createNewRegistration(String registration) { //metodo auxiliar para construir os objetos
        return Registration.builder()
                .name("Ana Neri")
                .dateOfRegistration("10/10/2021")
                .registration(registration).build();
    }
}
