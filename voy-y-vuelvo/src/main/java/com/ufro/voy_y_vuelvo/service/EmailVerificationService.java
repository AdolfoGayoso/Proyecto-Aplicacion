package com.ufro.voy_y_vuelvo.service;

import com.ufro.voy_y_vuelvo.dto.AuthenticationResponseDTO;
import com.ufro.voy_y_vuelvo.dto.AuthenticationResponseMessages;
import com.ufro.voy_y_vuelvo.model.CustomerEntity;
import com.ufro.voy_y_vuelvo.repository.CustomerEntityRepository;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EmailVerificationService {
    private final JavaMailSender mailSender;
    private final CustomerEntityRepository customerEntityRepository;
    private final CustomerService customerService;

    public EmailVerificationService(JavaMailSender mailSender, CustomerEntityRepository customerEntityRepository, CustomerService customerService) {
        this.mailSender = mailSender;
        this.customerEntityRepository = customerEntityRepository;
        this.customerService = customerService;
    }

    public void sendVerificationEmail(String sendTo, String emailVerificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendTo);
        message.setSubject("Verifica tu email - Voy y Vuelvo");
        message.setText(String.format(
                "Por favor verifica tu email haciendo click en el siguiente enlace:\n" +
                        "%s/api/register/verify-email?emailVerificationCode=%s\n\n",
                ApiURL.API_URL.getUrl(), emailVerificationCode
        ));

        mailSender.send(message);
    }

    @Transactional
    public AuthenticationResponseDTO verifyEmail(String verificationCode) {

        AuthenticationResponseDTO response = new AuthenticationResponseDTO();

        Optional<CustomerEntity> customerOptional = customerEntityRepository.findByEmailVerificationCode(verificationCode);

        if (customerOptional.isPresent()) {
            CustomerEntity customer = customerOptional.get();
            customer.setEmailVerified(Boolean.TRUE);
            customer.setEmailVerificationCode(null);
            customerEntityRepository.save(customer);

            response.setSuccess(Boolean.TRUE);
            response.setCustomer(customerService.convertToDTO(customer));
            response.setMessage(AuthenticationResponseMessages.CUSTOMER_EMAIL_AUTHENTICATION_SUCCESS.message());

            return response;
        }

        response.setSuccess(Boolean.FALSE);
        response.setCustomer(null);
        response.setMessage(AuthenticationResponseMessages.CUSTOMER_EMAIL_AUTHENTICATION_FAILURE.message());

        return response;
    }

}
