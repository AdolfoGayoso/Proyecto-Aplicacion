package com.ufro.voy_y_vuelvo.service;

import com.ufro.voy_y_vuelvo.model.CustomerEntity;
import com.ufro.voy_y_vuelvo.repository.CustomerEntityRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationService {
    private final JavaMailSender mailSender;
    private final CustomerEntityRepository customerEntityRepository;

    public EmailVerificationService(JavaMailSender mailSender, CustomerEntityRepository customerEntityRepository) {
        this.mailSender = mailSender;
        this.customerEntityRepository = customerEntityRepository;
    }

    public void sendVerificationEmail(String sendTo, String verificationCode) {
    }

    public boolean verifyEmail(String verificationCode) {
        CustomerEntity customerToValidate = customerEntityRepository.findByEmailVerificationCode(verificationCode);
        if (customerToValidate != null) {
            customerToValidate.setEmailVerified(Boolean.TRUE);
            customerToValidate.setEmailVerificationCode(null);
            customerEntityRepository.save(customerToValidate);
            return true;
        } else {
            return false;
        }
    }


}
