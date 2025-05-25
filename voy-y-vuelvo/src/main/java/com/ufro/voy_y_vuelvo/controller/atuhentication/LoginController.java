package com.ufro.voy_y_vuelvo.controller.atuhentication;

import com.ufro.voy_y_vuelvo.dto.ApiResponse;
import com.ufro.voy_y_vuelvo.dto.authetication.login.LoginRequest;
import com.ufro.voy_y_vuelvo.dto.authetication.login.LoginResponse;
import com.ufro.voy_y_vuelvo.model.users.User;
import com.ufro.voy_y_vuelvo.service.authentication.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/user/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return loginService.loginUser(loginRequest);
    }

    @GetMapping("/user-info")
    public User getUserInfo(@RequestHeader("Authorization") String token) {
        // Eliminar "Bearer " del token si está presente
        String jwt = token.replace("Bearer ", "");
        return loginService.getUserFromToken(jwt);
    }
}
