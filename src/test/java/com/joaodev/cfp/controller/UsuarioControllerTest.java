package com.joaodev.cfp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class UsuarioControllerTest {

    @Autowired
    UsuarioController usuarioController;

    @Test
    void cenario1(){
        ResponseEntity this.usuarioController.buscarTodos();
    }
}
