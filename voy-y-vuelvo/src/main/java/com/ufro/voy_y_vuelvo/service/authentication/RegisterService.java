package com.ufro.voy_y_vuelvo.service.authentication;

import com.ufro.voy_y_vuelvo.dto.ApiResponse;
import com.ufro.voy_y_vuelvo.dto.authetication.register.CustomerRegisterResponse;
import com.ufro.voy_y_vuelvo.dto.authetication.register.CustomerRegisterRequest;
import com.ufro.voy_y_vuelvo.model.users.Customer;
import com.ufro.voy_y_vuelvo.model.users.UserType;
import com.ufro.voy_y_vuelvo.repository.UserRepository;
import com.ufro.voy_y_vuelvo.service.CustomerService;
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
    private final CustomerService customerService;

    @Transactional
    public ApiResponse<CustomerRegisterResponse> registerCustomer(CustomerRegisterRequest request) {
        // Creacion de la respuesta
        CustomerRegisterResponse customerRegisterResponse = new CustomerRegisterResponse();
        // Validar email unico
        if (userRepository.existsByEmail(request.getEmail())) {
            customerRegisterResponse.setUser(null);
            customerRegisterResponse.setUserType(null);
            customerRegisterResponse.setSuccess(Boolean.FALSE);
            return new ApiResponse<>(
                    400,
                    "Email ya se encuentra registrado.",
                    customerRegisterResponse);

        }
        // Validar rut unico
        if (userRepository.existsByRut(request.getRut())) {
            customerRegisterResponse.setUser(null);
            customerRegisterResponse.setUserType(null);
            customerRegisterResponse.setSuccess(Boolean.FALSE);
            return new ApiResponse<>(
                    400,
                    "Email rut se encuentra registrado.",
                    customerRegisterResponse);
        }

        Customer customer = new Customer();
        customer.setEmail(request.getEmail());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setUserName(request.getUserName());
        customer.setRut(request.getRut());
        customer.setUserType(UserType.CUSTOMER.getUserType());
        // Datos validacion por email (tod o)
        customer.setEmailVerificationCode(UUID.randomUUID().toString());
        customer.setEmailVerified(Boolean.FALSE);

        userRepository.save(customer);
        // Datos de la response
        customerRegisterResponse.setUser(customerService.convertCustomerToUserDto(customer));
        customerRegisterResponse.setUserType(UserType.CUSTOMER.getUserType());
        customerRegisterResponse.setSuccess(Boolean.TRUE);

        return new ApiResponse<>(
                201,
                "Usuario registrado con exito.",
                customerRegisterResponse
        );
    }
}
