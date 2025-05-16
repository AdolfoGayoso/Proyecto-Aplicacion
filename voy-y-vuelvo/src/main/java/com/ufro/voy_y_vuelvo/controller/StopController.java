package com.ufro.voy_y_vuelvo.controller;

import com.ufro.voy_y_vuelvo.dto.ApiResponse;
import com.ufro.voy_y_vuelvo.dto.StopDto;
import com.ufro.voy_y_vuelvo.service.StopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stop")
@RequiredArgsConstructor
public class StopController {
    private final StopService stopService;

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<?>> getAllStops() {
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.OK.value(), "Paradas obtenidas con exito.", stopService.getAllStops());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get-stop-by-name")
    public ResponseEntity<ApiResponse<?>> getStopByName(@RequestParam String name) {
        ApiResponse<?> response = new ApiResponse<>();
        Optional<StopDto> stopDto = stopService.findByName(name);



    }
}
