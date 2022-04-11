package com.bootcamp.microservicemeetup.controller;

import com.bootcamp.microservicemeetup.model.RegistrationDTO;
import com.bootcamp.microservicemeetup.model.entity.Registration;
import com.bootcamp.microservicemeetup.service.RegistrationService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    private RegistrationService registrationService;

    private ModelMapper modelMapper;


    public RegistrationController(RegistrationService registrationService, ModelMapper modelMapper) {
        this.registrationService = registrationService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationDTO create(@RequestBody @Valid RegistrationDTO dto) {

        Registration entity = modelMapper.map(dto, Registration.class);
        entity = registrationService.save(entity);

        return modelMapper.map(entity, RegistrationDTO.class);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public RegistrationDTO get (@PathVariable Integer id) { //PathVariable é passado na url, PathParameter é no body

        return registrationService
                .getRegistrationById(id)
                .map(registration -> modelMapper.map(registration, RegistrationDTO.class))
                .orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByRegistrationId(@PathVariable Integer id) {
        Registration registration = registrationService.getRegistrationById(id) //mapeio a entidade, trago o id
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));  //trago tratamento em q se ele nao retornar isso, irá retornar uma ResponseStatusException com status Not Found
        registrationService.delete(registration);
    }


    @PutMapping("{id}")
    public RegistrationDTO update(@PathVariable Integer id, RegistrationDTO registrationDTO) {

        return registrationService.getRegistrationById(id).map(registration -> { //define os atributos que precisam ter na hora de atualizar
            registration.setName(registrationDTO.getName()); //seta os registros na DTO
            registration.setDateOfRegistration(registrationDTO.getDateOfRegistration()); //seta os registros na DTO
            registration = registrationService.update(registration); //faz segundo mapeamento onde dps de atualizar essas informações, chama o metodo para dar update nessas informações

            return modelMapper.map(registration, RegistrationDTO.class); //retorna modelMapper e faz como se fosse uma comparação entre registration e RegistrationDTO
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)); //

    }

    @GetMapping
    public Page<RegistrationDTO> find(RegistrationDTO dto, Pageable pageRequest) { //coloca o Pageable como se fosse a requisição desse método
        Registration filter = modelMapper.map(dto, Registration.class); //chamo a entidado que é onde aplicarei o filtro, dps mapear as informações
        Page<Registration> result = registrationService.find(filter, pageRequest); //chamo Page, aplico a entidade nele e trago como resultado o service.find, passando filter e pageRequest
                                                                                    //desse metodo, busque de tal forma e com qual requisição

        List<RegistrationDTO> list = result.getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, RegistrationDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<RegistrationDTO>(list, pageRequest, result.getTotalElements());
    }
}