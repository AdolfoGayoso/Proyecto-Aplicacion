package com.ufro.voy_y_vuelvo.repository;

import com.ufro.voy_y_vuelvo.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByRut(String rut);
    Optional<CustomerEntity> findByEmail(String email);
    Optional<CustomerEntity> findByEmailVerificationCode(String verificationCode);
    boolean existsByRutAndEmailVerifiedTrue(String rut);
    boolean existsByEmailAndEmailVerifiedTrue(String email);
}
