package com.ufro.voy_y_vuelvo.repository;

import com.ufro.voy_y_vuelvo.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByRut(String rut);
}
