package com.bootcamp.microservicemeetup.controller;

import com.bootcamp.microservicemeetup.controller.exceptions.ApiErrors;
import com.bootcamp.microservicemeetup.exception.BusinessException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

//a ControllerAdvice serve para centralizar os tratamentos de exceção em um unico local. Dessa forma a controller fica mais organizada
@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class) //define o tipo de exceção que ele vai lidar que é MethodArgumentNotValidException
    @ResponseStatus(HttpStatus.BAD_REQUEST) //definir qual o status que esse erro vai retornar
    //metodo para lidar com uma Validação de Exception
    public ApiErrors handleValidateException(MethodArgumentNotValidException e) { //o tipo q coloco como parametro é o mesmo que eu defino em @ExceptionHandler
        BindingResult bindingResult = e.getBindingResult(); //quero trazer os argumentos dentro dos metodos que não sao validos

        return new ApiErrors(bindingResult);
    }

    @ExceptionHandler(BusinessException.class) //define o tipo de exceção que ele vai tratar e lidar
    @ResponseStatus(HttpStatus.BAD_REQUEST)   //definir qual o status que esse erro vai retornar
    public ApiErrors handleBusinessException(BusinessException e) {
        return new ApiErrors(e);
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus //como ele vai recebendo e retornando qualquer tipos de http status, nao será necessário definir qual será
    public ResponseEntity handleResponseStatusException(ResponseStatusException ex) {
        return new ResponseEntity(new ApiErrors(ex), ex.getStatus()); //precisa retornar o status
    }

}