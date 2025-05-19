package com.ufro.voy_y_vuelvo.service.authentication;

import com.ufro.voy_y_vuelvo.dto.ApiResponse;
import com.ufro.voy_y_vuelvo.dto.authetication.register.CustomerRegisterResponse;
import com.ufro.voy_y_vuelvo.dto.authetication.register.CustomerRegisterRequest;
import com.ufro.voy_y_vuelvo.model.users.Customer;
import com.ufro.voy_y_vuelvo.model.users.UserType;
import com.ufro.voy_y_vuelvo.repository.CustomerRepository;
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
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final EmailVerificationService emailVerificationService;

    @Transactional
    public void cleanupUnverifiedCustomer(String rut, String email) {
        customerRepository.findByRut(rut)
                .ifPresent(existingCustomer -> {
                    if (!existingCustomer.getEmailVerified()) {
                        customerRepository.delete(existingCustomer);
                    }
                });
        customerRepository.findByEmail(email)
                .ifPresent(existingCustomer -> {
                    if (!existingCustomer.getEmailVerified()) {
                        customerRepository.delete(existingCustomer);
                    }
                });
    }

    @Transactional
    public ApiResponse<CustomerRegisterResponse> registerCustomer(CustomerRegisterRequest request) {
        // Creacion de la respuesta
        CustomerRegisterResponse customerRegisterResponse = new CustomerRegisterResponse();

        // Eliminar usuarios no verificados con los datos de un nuevo posible a verificar
        cleanupUnverifiedCustomer(request.getRut(), request.getEmail());

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

        // Creacion codigo de validcion de email
        String emailVerificationCode = UUID.randomUUID().toString();

        // Set de datos de nuevo usuario no verificado
        Customer customer = new Customer();
        customer.setEmail(request.getEmail());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setUserName(request.getUserName());
        customer.setRut(request.getRut());
        customer.setUserType(UserType.CUSTOMER.getUserType());

        // Datos validacion por email (tod o)
        customer.setEmailVerificationCode(emailVerificationCode);
        customer.setEmailVerified(Boolean.FALSE);

        // Guardar el usuario creado en bd
        userRepository.save(customer);

        try {
            emailVerificationService.sendVerificationEmail(customer.getEmail(), emailVerificationCode);

            customerRegisterResponse.setSuccess(Boolean.TRUE);
            customerRegisterResponse.setUserType(UserType.CUSTOMER.getUserType());
            customerRegisterResponse.setUser(customerService.toDto(customer));

            return new ApiResponse<>(
                    202,
                    "Registro exitoso, se he enviado un email de verificaion.",
                    customerRegisterResponse
            );
        } catch (Exception e) {
            e.printStackTrace();
            customerRegisterResponse.setSuccess(Boolean.FALSE);
            customerRegisterResponse.setUserType(UserType.CUSTOMER.getUserType());
            customerRegisterResponse.setUser(null);

            customerRepository.delete(customer);

            return new ApiResponse<>(
                    500,
                    "No se ha podido registrar el usuario.",
                    customerRegisterResponse
            );
        }
    }
}
