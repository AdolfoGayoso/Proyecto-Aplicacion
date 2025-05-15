package com.ufro.voy_y_vuelvo.service;

import com.ufro.voy_y_vuelvo.dto.StopDto;
import com.ufro.voy_y_vuelvo.model.trips.Stop;
import com.ufro.voy_y_vuelvo.repository.StopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StopService {

    private final StopRepository stopRepository;

    public List<StopDto> getAllStops() {
        return stopRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public Optional<StopDto> findByName(String name) {
        return stopRepository.findStopByNameIgnoreCase(name)
                .map(this::toDto);
    }

    public StopDto toDto(Stop stop) {
        StopDto stopDto = new StopDto();
        stopDto.setId(stop.getId());
        stopDto.setName(stop.getName());
        return stopDto;
    }
}
