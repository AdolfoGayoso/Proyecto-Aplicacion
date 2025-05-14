package com.ufro.voy_y_vuelvo.service.authentication;

import com.ufro.voy_y_vuelvo.controller.ApiURL;
import com.ufro.voy_y_vuelvo.dto.ApiResponse;
import com.ufro.voy_y_vuelvo.dto.authetication.register.EmailValidationResponse;
import com.ufro.voy_y_vuelvo.model.users.Customer;
import com.ufro.voy_y_vuelvo.repository.CustomerRepository;
import com.ufro.voy_y_vuelvo.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {
    private final JavaMailSender mailSender;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    public void sendVerificationEmail(String sendTo, String emailVerificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendTo);
        message.setSubject("Verifica tu email - Voy y Vuelvo");
        message.setText(String.format(
                "Por favor verifica tu email haciendo click en el siguiente enlace:\n" +
                        "%s/api/auth/register/verify-email?emailVerificationCode=%s\n\n",
                ApiURL.API_URL.getUrl(), emailVerificationCode
        ));

        mailSender.send(message);
    }

    @Transactional
    public ApiResponse<EmailValidationResponse> verifyEmail(String emailVerificationCode) {
        EmailValidationResponse response = new EmailValidationResponse();

        Optional<Customer> customer = customerRepository.findByEmailVerificationCode(emailVerificationCode);

        if (customer.isPresent()) {

            if (customer.get().getEmailVerified()) {

                response.setEmailToValidate(customer.get().getEmail());
                response.setSuccess(Boolean.TRUE);
                
                return new ApiResponse<>(
                        409,
                        "Email validado con exito.",
                        response
                );
            }


            Customer customerToValidate = customer.get();
            customerToValidate.setEmailVerified(Boolean.TRUE);
            customerToValidate.setEmailVerificationCode(null);

            customerRepository.save(customerToValidate);

            response.setEmailToValidate(customerToValidate.getEmail());
            response.setSuccess(Boolean.TRUE);

            return new ApiResponse<>(
                    200,
                    "Email validado con exito.",
                    response
            );
        }
        response.setSuccess(Boolean.FALSE);
        response.setEmailToValidate(null);

        return new ApiResponse<>(
                400,
                "No se ha podido validar el email.",
                response
        );
    }
}
