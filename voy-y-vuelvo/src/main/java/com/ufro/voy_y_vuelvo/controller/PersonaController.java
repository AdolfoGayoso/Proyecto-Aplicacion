package com.ufro.voy_y_vuelvo.controller;

import com.ufro.voy_y_vuelvo.model.Persona;
import com.ufro.voy_y_vuelvo.repository.PersonaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    private final PersonaRepository personaRepository;

    public PersonaController(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    // Crear nueva persona
    @PostMapping
    public ResponseEntity<Persona> crearPersona(@RequestBody Persona persona) {
        return ResponseEntity.ok(personaRepository.save(persona));
    }

    // Obtener todas las personas
    @GetMapping
    public ResponseEntity<List<Persona>> obtenerTodasLasPersonas() {
        return ResponseEntity.ok(personaRepository.findAll());
    }
}