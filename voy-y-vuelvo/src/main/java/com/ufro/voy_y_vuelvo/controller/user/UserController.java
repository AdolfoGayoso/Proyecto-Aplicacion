package com.ufro.voy_y_vuelvo.controller.user;

import com.ufro.voy_y_vuelvo.dto.ApiResponse;
import com.ufro.voy_y_vuelvo.dto.trip.purchase.PurchaseResponseDto;
import com.ufro.voy_y_vuelvo.service.trip.purchase.PurchaseService;
import com.ufro.voy_y_vuelvo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PurchaseService purchaseService;

    @GetMapping("/info")
    public ResponseEntity<ApiResponse<?>> getUserInfo(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        ApiResponse<?> response = userService.getUserInfo(jwt);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/purchases")
    public ApiResponse<List<PurchaseResponseDto>> getUserPurchases(@RequestHeader("Authorization") String token) {
        return purchaseService.getPurchasesByUser(token);
    }
}
