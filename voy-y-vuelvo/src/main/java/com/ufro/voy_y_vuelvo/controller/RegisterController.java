package com.ufro.voy_y_vuelvo.controller;

import com.ufro.voy_y_vuelvo.dto.CustomerDTO;
import com.ufro.voy_y_vuelvo.dto.RegisterRequestDTO;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class RegisterController {

    @PostMapping("/user/register")
    public ResponseEntity<CustomerDTO> registerUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
    }

}
