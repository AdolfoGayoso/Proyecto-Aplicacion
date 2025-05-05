package com.ufro.voy_y_vuelvo.controller;

import com.ufro.voy_y_vuelvo.dto.AuthenticationResponseDTO;
import com.ufro.voy_y_vuelvo.dto.RegisterRequestDTO;
import com.ufro.voy_y_vuelvo.service.EmailVerificationService;
import com.ufro.voy_y_vuelvo.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    private final RegisterService registerService;
    private final EmailVerificationService emailVerificationService;

    public RegisterController(RegisterService registerService, EmailVerificationService emailVerificationService) {
        this.registerService = registerService;
        this.emailVerificationService = emailVerificationService;
    }

    @PostMapping()
    public ResponseEntity<AuthenticationResponseDTO> registerUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
        AuthenticationResponseDTO authenticationResponseDTO = registerService.registerCustomer(registerRequestDTO);
        return ResponseEntity.status(authenticationResponseDTO.isSuccess() ? 201 : 400).body(authenticationResponseDTO);
    }

    @GetMapping("/verify-email")
    public ResponseEntity<AuthenticationResponseDTO> verifyEmail(@RequestParam String emailVerificationCode) {
        AuthenticationResponseDTO response = emailVerificationService.verifyEmail(emailVerificationCode);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response);
    }

}
