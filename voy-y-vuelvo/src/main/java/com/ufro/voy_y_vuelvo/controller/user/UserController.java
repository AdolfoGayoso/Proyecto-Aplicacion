package com.ufro.voy_y_vuelvo.controller.user;

import com.ufro.voy_y_vuelvo.dto.ApiResponse;
import com.ufro.voy_y_vuelvo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<ApiResponse<?>> getUserInfo(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        ApiResponse<?> response = userService.getUserInfo(jwt);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
