package com.example.meu_primeiroSpringBoot.Exception;

public class ExceptionDadosDuplicado extends RuntimeException{
    
    public ExceptionDadosDuplicado() {
        super("Esse Dado já existe");
    }
}
