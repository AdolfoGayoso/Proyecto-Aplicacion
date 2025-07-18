package com.ufro.voy_y_vuelvo.service.authentication;

import com.ufro.voy_y_vuelvo.controller.ApiURL;
import com.ufro.voy_y_vuelvo.dto.ApiResponse;
import com.ufro.voy_y_vuelvo.dto.authetication.register.EmailValidationResponse;
import com.ufro.voy_y_vuelvo.model.users.Customer;
import com.ufro.voy_y_vuelvo.repository.CustomerRepository;
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

    public void sendVerificationEmail(String sendTo, String emailVerificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendTo);
        message.setFrom("voyyvuelvocomprapasajes@gmail.com");
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

            Customer customerToValidate = customer.get();

            if (!customer.get().getEmailVerified()) {
                customerToValidate.setEmailVerified(Boolean.TRUE);
                customerToValidate.setEmailVerificationCode(emailVerificationCode);

                customerRepository.save(customerToValidate);

                response.setEmailToValidate(customerToValidate.getEmail());
                response.setSuccess(Boolean.TRUE);

                return new ApiResponse<>(
                        200,
                        "Email validado con exito.",
                        response
                );
            } else {
                response.setEmailToValidate(customerToValidate.getEmail());
                response.setSuccess(Boolean.TRUE);

                return new ApiResponse<>(
                        204,
                        "Usuario ya ha verificado su email",
                        response
                );
            }
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
