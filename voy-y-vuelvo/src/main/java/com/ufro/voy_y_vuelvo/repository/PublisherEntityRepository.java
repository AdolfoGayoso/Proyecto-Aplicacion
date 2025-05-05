package com.ufro.voy_y_vuelvo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherEntityRepository extends JpaRepository<PublisherEntity, Long> {
}
