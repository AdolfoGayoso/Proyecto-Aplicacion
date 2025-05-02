package com.ufro.voy_y_vuelvo.repository;

import com.ufro.voy_y_vuelvo.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, Long> {
    boolean existsByEmail(String email);

    boolean existsByRut(String rut);
}
