package com.ufro.voy_y_vuelvo.controller;

import com.ufro.voy_y_vuelvo.dto.authetication.CustomerRegisterRequest;
import com.ufro.voy_y_vuelvo.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService authService;

    @PostMapping("/register/customer")
    public ResponseEntity<String> registerCustomer(@RequestBody CustomerRegisterRequest request) {
        authService.registerCustomer(request);
        return ResponseEntity.ok("Cliente registrado exitosamente");
    }
}
