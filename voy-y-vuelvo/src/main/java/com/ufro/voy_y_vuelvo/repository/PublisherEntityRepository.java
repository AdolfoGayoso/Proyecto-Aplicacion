package com.ufro.voy_y_vuelvo.repository;

import com.ufro.voy_y_vuelvo.model.PublisherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherEntityRepository extends JpaRepository<PublisherEntity, Long> {
}
