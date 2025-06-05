package com.ufro.voy_y_vuelvo.service;

import com.ufro.voy_y_vuelvo.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TripService {
    private final TripRepository tripRepository;
}
