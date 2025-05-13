package com.ufro.voy_y_vuelvo.service;

import com.ufro.voy_y_vuelvo.dto.authetication.CustomerRegisterRequest;
import com.ufro.voy_y_vuelvo.model.users.Customer;
import com.ufro.voy_y_vuelvo.model.users.UserType;
import com.ufro.voy_y_vuelvo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerCustomer(CustomerRegisterRequest request) {
        // Validar unicidad de email y RUT
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email ya registrado");
        }
        if (userRepository.existsByRut(request.getRut())) {
            throw new RuntimeException("RUT ya registrado");
        }

        Customer customer = new Customer();

        customer.setEmail(request.getEmail());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setUserName(request.getUserName());
        customer.setRut(request.getRut());

        customer.setUserType(UserType.CUSTOMER.getUserType());
        customer.setEmailVerificationCode(UUID.randomUUID().toString());

        userRepository.save(customer);
    }
}
