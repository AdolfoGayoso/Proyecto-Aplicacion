package com.ufro.voy_y_vuelvo.service.user;

import com.ufro.voy_y_vuelvo.dto.ApiResponse;
import com.ufro.voy_y_vuelvo.dto.user.CustomerDto;
import com.ufro.voy_y_vuelvo.model.users.Customer;
import com.ufro.voy_y_vuelvo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    public CustomerDto toDto(Customer customer) {
        CustomerDto userDto = new CustomerDto();
        userDto.setUserName(customer.getUserName());
        userDto.setEmail(customer.getEmail());
        userDto.setUserType(customer.getUserType());
        return userDto;
    }
}
