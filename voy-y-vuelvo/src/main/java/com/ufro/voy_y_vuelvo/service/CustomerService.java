package com.ufro.voy_y_vuelvo.service;

import com.ufro.voy_y_vuelvo.dto.authetication.register.CustomerRegisterRequest;
import com.ufro.voy_y_vuelvo.dto.user.UserDto;
import com.ufro.voy_y_vuelvo.model.users.Customer;
import com.ufro.voy_y_vuelvo.model.users.UserType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final PasswordEncoder passwordEncoder;

    public UserDto convertCustomerToUserDto(Customer customer) {
        UserDto userDto = new UserDto();
        userDto.setUserName(customer.getUserName());
        userDto.setEmail(customer.getEmail());
        return userDto;
    }
}
