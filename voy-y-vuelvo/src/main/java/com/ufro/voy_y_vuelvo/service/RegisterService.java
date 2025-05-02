package com.ufro.voy_y_vuelvo.service;

import com.ufro.voy_y_vuelvo.dto.AuthenticationResponseDTO;
import com.ufro.voy_y_vuelvo.dto.CustomerDTO;
import com.ufro.voy_y_vuelvo.dto.RegisterRequestDTO;
import com.ufro.voy_y_vuelvo.model.CustomerEntity;
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
        CustomerDTO customerDTO = new CustomerDTO(
                registerRequestDTO.getRut(),
                registerRequestDTO.getName(),
                registerRequestDTO.getEmail()
        );

        if(
                customerEntityRepository.existsByEmail(registerRequestDTO.getEmail()) ||
                customerEntityRepository.existsByRut(registerRequestDTO.getRut())
        ) {
            authenticationResponseDTO.setCustomer(null);
            authenticationResponseDTO.setMessage(AuthenticationResponseMessages.RUT_EMAIL_ALREADY_EXISTS.message());
            authenticationResponseDTO.setSuccess(Boolean.FALSE);
            return authenticationResponseDTO;
        }

        authenticationResponseDTO.setCustomer(customerDTO);
        authenticationResponseDTO.setMessage(AuthenticationResponseMessages.REGISTER_SUCCESSFUL.message());
        authenticationResponseDTO.setSuccess(Boolean.TRUE);

        CustomerEntity newCustomer = new CustomerEntity();

        newCustomer.setRut(registerRequestDTO.getRut());
        newCustomer.setName(registerRequestDTO.getName());
        newCustomer.setPassword(registerRequestDTO.getPassword());
        newCustomer.setEmail(registerRequestDTO.getEmail());
        newCustomer.setPoints(0);

        customerEntityRepository.save(newCustomer);

        return authenticationResponseDTO;
    }
}
