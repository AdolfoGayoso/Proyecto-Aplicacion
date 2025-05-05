package com.ufro.voy_y_vuelvo.service;

import com.ufro.voy_y_vuelvo.dto.AuthenticationResponseDTO;
import com.ufro.voy_y_vuelvo.dto.AuthenticationResponseMessages;
import com.ufro.voy_y_vuelvo.dto.CustomerDTO;
import com.ufro.voy_y_vuelvo.dto.RegisterRequestDTO;
import com.ufro.voy_y_vuelvo.model.CustomerEntity;
import com.ufro.voy_y_vuelvo.repository.CustomerEntityRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RegisterService {

    private final CustomerEntityRepository customerEntityRepository;
    private final EmailVerificationService emailVerificationService;
    private final PasswordEncoder passwordEncoder;


    public RegisterService(CustomerEntityRepository customerEntity, EmailVerificationService emailVerificationService) {
        this.customerEntityRepository = customerEntity;
        this.emailVerificationService = emailVerificationService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public void cleanupUnverifiedCustomer(String rut, String email) {
        customerEntityRepository.findByRut(rut)
                .ifPresent(existingCustomer -> {
                    if (!existingCustomer.isEmailVerified()) {
                        customerEntityRepository.delete(existingCustomer);
                    }
                });
        customerEntityRepository.findByEmail(email)
                .ifPresent(existingCustomer -> {
                    if (!existingCustomer.isEmailVerified()) {
                        customerEntityRepository.delete(existingCustomer);
                    }
                });
    }

    @Transactional
    public AuthenticationResponseDTO registerCustomer(RegisterRequestDTO registerRequestDTO) {
        AuthenticationResponseDTO authenticationResponseDTO = new AuthenticationResponseDTO();

        CustomerDTO customerDTO = new CustomerDTO(
                registerRequestDTO.getRut(),
                registerRequestDTO.getName(),
                registerRequestDTO.getEmail()
        );

        if(
                customerEntityRepository.existsByRutAndEmailVerifiedTrue(registerRequestDTO.getRut()) ||
                customerEntityRepository.existsByEmailAndEmailVerifiedTrue(registerRequestDTO.getEmail()))
        {
            authenticationResponseDTO.setCustomer(null);
            authenticationResponseDTO.setMessage(AuthenticationResponseMessages.RUT_EMAIL_ALREADY_EXISTS.message());
            authenticationResponseDTO.setSuccess(Boolean.FALSE);
            return authenticationResponseDTO;
        }

        String verificationCode = UUID.randomUUID().toString();
        CustomerEntity newCustomer = new CustomerEntity();

        newCustomer.setRut(registerRequestDTO.getRut());
        newCustomer.setName(registerRequestDTO.getName());
        newCustomer.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        newCustomer.setEmail(registerRequestDTO.getEmail());
        newCustomer.setPoints(0);
        newCustomer.setEmailVerified(Boolean.FALSE);
        newCustomer.setEmailVerificationCode(verificationCode);

        customerEntityRepository.save(newCustomer);

        try {
            emailVerificationService.sendVerificationEmail(newCustomer.getEmail(), verificationCode);
            authenticationResponseDTO.setMessage(AuthenticationResponseMessages.VERIFICATION_EMAIL_SENT.message());
            authenticationResponseDTO.setSuccess(Boolean.TRUE);
            authenticationResponseDTO.setCustomer(new CustomerDTO(
                    newCustomer.getRut(),
                    newCustomer.getName(),
                    newCustomer.getEmail()
            ));
        } catch (Exception e) {
            customerEntityRepository.delete(newCustomer);
            authenticationResponseDTO.setSuccess(Boolean.FALSE);
            authenticationResponseDTO.setMessage(AuthenticationResponseMessages.FAIL_TO_SEND_VERIFICATION_EMAIL.message());
        }

        return authenticationResponseDTO;
    }
}
