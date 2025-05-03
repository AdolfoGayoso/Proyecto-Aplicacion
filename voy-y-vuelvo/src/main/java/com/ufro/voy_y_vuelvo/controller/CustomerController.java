package com.ufro.voy_y_vuelvo.controller;

import com.ufro.voy_y_vuelvo.dto.CustomerDTO;
import com.ufro.voy_y_vuelvo.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> usersRegistered = customerService.getAllRegisteredCostumers();
        return ResponseEntity.ok(usersRegistered);
    }
}
