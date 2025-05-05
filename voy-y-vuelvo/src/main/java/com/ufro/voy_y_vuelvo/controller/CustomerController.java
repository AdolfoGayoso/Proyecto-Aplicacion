package com.ufro.voy_y_vuelvo.controller;

import com.ufro.voy_y_vuelvo.dto.CustomerDTO;
import com.ufro.voy_y_vuelvo.service.CustomerService;
import com.ufro.voy_y_vuelvo.service.EmailVerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final EmailVerificationService emailVerificationService;

    public CustomerController(CustomerService customerService, EmailVerificationService emailVerificationService) {
        this.customerService = customerService;
        this.emailVerificationService = emailVerificationService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> usersRegistered = customerService.getAllRegisteredCostumers();
        return ResponseEntity.ok(usersRegistered);
    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String emailVerificationCode) {
        emailVerificationService.verifyEmail(emailVerificationCode);
        return ResponseEntity.ok("Email verificado exitosamente");
    }
}
