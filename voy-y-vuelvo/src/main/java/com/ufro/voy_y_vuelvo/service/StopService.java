package com.ufro.voy_y_vuelvo.service;

import com.ufro.voy_y_vuelvo.dto.ApiResponse;
import com.ufro.voy_y_vuelvo.dto.StopDto;
import com.ufro.voy_y_vuelvo.model.trips.Stop;
import com.ufro.voy_y_vuelvo.repository.StopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public StopDto findByName(String name) {

        StopDto  stopDto =


    }

    public StopDto toDto(Stop stop) {
        StopDto stopDto = new StopDto();
        stopDto.setId(stop.getId());
        stopDto.setName(stop.getName());
        return stopDto;
    }
}
