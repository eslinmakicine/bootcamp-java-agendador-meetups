package com.bootcamp.microservicemeetup.controller.exceptions;

import com.bootcamp.microservicemeetup.exception.BusinessException;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiErrors {

    private final List<String> errors;

    public ApiErrors(BindingResult bindingResult) { //dentro desse, como se estivessemos mapeando e fazendo o construtor dessa classe, vamos chamar a classe bindingResult e nomeala novamente
        this.errors = new ArrayList<>(); //construtor da classe
        bindingResult.getAllErrors() //declarar a classe e chamar esse método
                .forEach(error -> this.errors.add(error.getDefaultMessage())); //mapear com foreach, trazer um lambda e definir construtor do erros. Dele vamos adc que td erro terá uma msg default. E a partir do default, vamos pegar essa msg e exibir ao usuário
    }

    public ApiErrors(BusinessException e) { //definir metodo e trazer BusinessException
        this.errors = Arrays.asList(e.getMessage()); //para cada classe de erro iremos trazer o construtor dela. E criar uma lista onde irá trazer a msg
    }

    public ApiErrors(ResponseStatusException e) {//definir metodo e trazer ResponseStatusException
        this.errors = Arrays.asList(e.getReason());//para cada classe de erro iremos trazer o construtor dela. E criar uma lista onde irá trazer a razão
                                                    //foi pego a getReason pois erros de status nao retornam msg, retornam razões
    }

    public List<String> getErrors() {
        return errors;
    }

}