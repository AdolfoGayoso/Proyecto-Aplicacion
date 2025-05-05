package com.ufro.voy_y_vuelvo.service;

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

    public EmailVerificationService(JavaMailSender mailSender, CustomerEntityRepository customerEntityRepository) {
        this.mailSender = mailSender;
        this.customerEntityRepository = customerEntityRepository;
    }

    public void sendVerificationEmail(String sendTo, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendTo);
        message.setSubject("Verifica tu email - Voy y Vuelvo");
        message.setText(String.format(
                "Por favor verifica tu email haciendo click en el siguiente enlace:\n" +
                        "%s/api/verify-email?code=%s\n\n",
                ApiURL.API_URL, verificationCode
        ));

        mailSender.send(message);
    }

    @Transactional
    public boolean verifyEmail(String verificationCode) {
        Optional<CustomerEntity> customerOptional = customerEntityRepository.findByEmailVerificationCode(verificationCode);
        if (customerOptional.isEmpty()) {
            return false;
        }
        CustomerEntity customer = customerOptional.get();
        customer.setEmailVerified(Boolean.TRUE);
        customer.setEmailVerificationCode(null);
        customerEntityRepository.save(customer);
        return true;
    }


}
