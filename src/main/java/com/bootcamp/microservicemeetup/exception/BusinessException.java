package com.bootcamp.microservicemeetup.exception;
//RuntimeException é exception em tempo de execução
public class BusinessException extends RuntimeException {
    public BusinessException(String s) {
        super(s);
    }
}
