package com.ufro.voy_y_vuelvo.repository;

import com.ufro.voy_y_vuelvo.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
}