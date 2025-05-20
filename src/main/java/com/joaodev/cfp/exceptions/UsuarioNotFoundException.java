package com.joaodev.cfp.exceptions;

public class UsuarioNotFoundException extends RuntimeException {

     public UsuarioNotFoundException(Long id){
        super("Não foi possível achar o usuário " + id);
    }
}
