package com.ufro.voy_y_vuelvo.service;

import com.ufro.voy_y_vuelvo.dto.user.UserDto;
import com.ufro.voy_y_vuelvo.model.users.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final PasswordEncoder passwordEncoder;

    public UserDto toDto(Customer customer) {
        UserDto userDto = new UserDto();
        userDto.setUserName(customer.getUserName());
        userDto.setEmail(customer.getEmail());
        return userDto;
    }
}
