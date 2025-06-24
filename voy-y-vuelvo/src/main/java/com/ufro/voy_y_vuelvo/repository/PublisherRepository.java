package com.ufro.voy_y_vuelvo.repository;

import com.ufro.voy_y_vuelvo.model.users.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Publisher findByEmail(String email);
}
