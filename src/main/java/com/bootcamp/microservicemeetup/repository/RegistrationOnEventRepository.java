package com.bootcamp.microservicemeetup.repository;

        import com.bootcamp.microservicemeetup.model.entity.Meetup;
        import com.bootcamp.microservicemeetup.model.entity.Registration;
        import com.bootcamp.microservicemeetup.model.entity.RegistrationOnEvent;
        import org.springframework.data.domain.Page;
        import org.springframework.data.domain.Pageable;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.query.Param;

        import java.util.List;

public interface RegistrationOnEventRepository extends JpaRepository<RegistrationOnEvent, Integer> {

}