package com.ufro.voy_y_vuelvo.service;

import com.ufro.voy_y_vuelvo.dto.CustomerDTO;
import com.ufro.voy_y_vuelvo.model.CustomerEntity;
import com.ufro.voy_y_vuelvo.repository.CustomerEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerEntityRepository customerEntityRepository;

    public CustomerService(CustomerEntityRepository customerEntityRepository) {
        this.customerEntityRepository = customerEntityRepository;
    }

    public CustomerDTO convertToDTO(CustomerEntity customerEntity) {
        return new CustomerDTO(
                customerEntity.getRut(),
                customerEntity.getName(),
                customerEntity.getEmail()
        );
    }

    public List<CustomerDTO> getAllRegisteredCostumers() {
        List<CustomerEntity> customersRegistered = customerEntityRepository.findAll();
        return customersRegistered.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
