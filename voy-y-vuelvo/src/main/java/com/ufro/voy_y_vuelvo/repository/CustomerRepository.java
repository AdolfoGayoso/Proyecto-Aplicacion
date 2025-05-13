package com.ufro.voy_y_vuelvo.repository;

import com.ufro.voy_y_vuelvo.model.users.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmailVerificationCode(String emailVerificationCode);

    Optional<Customer> findByRut(String rut);

    Optional<Customer> findByEmail(String email);
}
