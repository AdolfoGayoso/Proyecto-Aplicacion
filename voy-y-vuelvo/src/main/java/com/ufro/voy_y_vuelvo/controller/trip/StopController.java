package com.ufro.voy_y_vuelvo.controller.trip;

import com.ufro.voy_y_vuelvo.dto.ApiResponse;
import com.ufro.voy_y_vuelvo.service.trip.StopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
