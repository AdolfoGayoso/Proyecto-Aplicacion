package com.ufro.voy_y_vuelvo.controller;

import com.ufro.voy_y_vuelvo.dto.AuthenticationResponseDTO;
import com.ufro.voy_y_vuelvo.dto.RegisterRequestDTO;
import com.ufro.voy_y_vuelvo.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/user/register")
    public ResponseEntity<AuthenticationResponseDTO> registerUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
        AuthenticationResponseDTO authenticationResponseDTO = registerService.registerCustomer(registerRequestDTO);
        return ResponseEntity.status(authenticationResponseDTO.isSuccess() ? 201 : 400).body(authenticationResponseDTO);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

}
