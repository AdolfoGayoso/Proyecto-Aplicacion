package com.ufro.voy_y_vuelvo.service;

import com.ufro.voy_y_vuelvo.dto.AuthenticationResponseDTO;
import com.ufro.voy_y_vuelvo.dto.RegisterRequestDTO;
import com.ufro.voy_y_vuelvo.repository.CustomerEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final CustomerEntityRepository customerEntityRepository;

    public RegisterService(CustomerEntityRepository customerEntity) {
        this.customerEntityRepository = customerEntity;
    }

    public AuthenticationResponseDTO registerCustomer(RegisterRequestDTO registerRequestDTO) {
        AuthenticationResponseDTO authenticationResponseDTO = new AuthenticationResponseDTO();

        if(
                customerEntityRepository.existsByEmail(registerRequestDTO.getEmail()) ||
                customerEntityRepository.existsByRut(registerRequestDTO.getRut())) {

            authenticationResponseDTO.setMessage("El email ya existe");

        }

        return authenticationResponseDTO;
    }
}
