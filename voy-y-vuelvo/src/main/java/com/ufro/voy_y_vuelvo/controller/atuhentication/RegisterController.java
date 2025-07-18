package com.ufro.voy_y_vuelvo.controller.atuhentication;

import com.ufro.voy_y_vuelvo.dto.ApiResponse;
import com.ufro.voy_y_vuelvo.dto.authetication.register.CustomerRegisterRequest;
import com.ufro.voy_y_vuelvo.service.authentication.EmailVerificationService;
import com.ufro.voy_y_vuelvo.service.authentication.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;
    private final EmailVerificationService emailVerificationService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register/customer")
    public ResponseEntity<ApiResponse<?>> registerCustomer(@RequestBody CustomerRegisterRequest request) {
        ApiResponse<?> response = registerService.registerCustomer(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/register/verify-email")
    public ResponseEntity<ApiResponse<?>> validateEmail(@RequestParam String emailVerificationCode) {
        ApiResponse<?> response = emailVerificationService.verifyEmail(emailVerificationCode);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @GetMapping("/hasheador")
    public String hasheador(@RequestParam String hash) {
        return passwordEncoder.encode(hash);
    }
}
