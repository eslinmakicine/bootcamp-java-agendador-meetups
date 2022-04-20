package com.bootcamp.microservicemeetup.controller.resource;

import com.bootcamp.microservicemeetup.controller.dto.MeetupDTO;
import com.bootcamp.microservicemeetup.controller.dto.MeetupFilterDTO;
import com.bootcamp.microservicemeetup.controller.dto.RegistrationDTO;
import com.bootcamp.microservicemeetup.model.entity.Meetup;
import com.bootcamp.microservicemeetup.model.entity.Registration;
import com.bootcamp.microservicemeetup.service.MeetupService;
import com.bootcamp.microservicemeetup.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/meetups")
@RequiredArgsConstructor
public class MeetupController {

    private final MeetupService meetupService;
    private final RegistrationService registrationService;
    private final ModelMapper modelMapper;



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Integer create(@RequestBody MeetupDTO meetupDTO) {

        Registration registration = registrationService
                .getRegistrationByRegistrationAttribute(meetupDTO.getRegistrationAttribute()) //chama o service para buscar pelo atribute
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)); //se nao colocar o orElseThrow, irá retornar erro NullPointerException, pois é um tipo de requisição que precisa de tratamento
        Meetup entity = Meetup.builder() //aqui trazemos a entidade e não DTO, pois estamos montando a requisição e validar que o dado que
                                        // vier daquilo vai ser salvo no banco de dados, é necessario trazer a entidade
                //se trouxer uma camada de transf de objetos (DTO) para salvar no banco, o proprio spring exibirá erro. Por isso o DTO é bem basico, sem indicar qual coluna e etc
                //da o builder, traz os atributos e da get nos valores dele
                .registration(registration)  //como o registration é atributo de outra classe, e chamamos ele atraves do service, nos declaramos a registration dentro
                .event(meetupDTO.getEvent())
                .meetupDate("10/10/2021")
                .build(); // builder serve para criar automaticamente o código necessário para instanciar uma classe

        entity = meetupService.save(entity);
        return entity.getId(); //pra garantir que foi salvo, retorna o ID da entidade
    }


    @GetMapping
    public Page<MeetupDTO> find(MeetupFilterDTO dto, Pageable pageRequest) {
        Page<Meetup> result = meetupService.find(dto, pageRequest);
        List<MeetupDTO> meetups = result
                .getContent()
                .stream()
                .map(entity -> {

                    Registration registration = entity.getRegistration();
                    RegistrationDTO registrationDTO = modelMapper.map(registration, RegistrationDTO.class);

                    MeetupDTO meetupDTO = modelMapper.map(entity, MeetupDTO.class);
                    meetupDTO.setRegistration(registrationDTO);
                    return meetupDTO;

                }).collect(Collectors.toList());
        return new PageImpl<MeetupDTO>(meetups, pageRequest, result.getTotalElements());
    }
}
