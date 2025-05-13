package com.ufro.voy_y_vuelvo.service.authentication;

import com.ufro.voy_y_vuelvo.config.JwtUtils;
import com.ufro.voy_y_vuelvo.dto.ApiResponse;
import com.ufro.voy_y_vuelvo.dto.authetication.login.LoginRequest;
import com.ufro.voy_y_vuelvo.dto.authetication.login.LoginResponse;
import com.ufro.voy_y_vuelvo.model.users.Customer;
import com.ufro.voy_y_vuelvo.model.users.User;
import com.ufro.voy_y_vuelvo.model.users.UserType;
import com.ufro.voy_y_vuelvo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public ApiResponse<LoginResponse> loginUser(LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());

        if (user.isEmpty()) {
            loginResponse.setToken(null);
            loginResponse.setEmail(null);
            loginResponse.setUsername(null);
            loginResponse.setUserType(null);

            return new ApiResponse<>(
                    404,
                    "Usuario no encontrado.",
                    loginResponse
            );
        } else {
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
                loginResponse.setToken(null);
                loginResponse.setEmail(user.get().getEmail());
                loginResponse.setUsername(null);
                loginResponse.setUserType(null);

                return new ApiResponse<>(
                        400,
                        "Contraseña incorrecta.",
                        loginResponse
                );
            }

            if (user.get() instanceof Customer customer) {
                if (!customer.getEmailVerified()) {
                    loginResponse.setToken(null);
                    loginResponse.setEmail(customer.getEmail());
                    loginResponse.setUsername(null);
                    loginResponse.setUserType(null);

                    return new ApiResponse<>(
                            401,
                            "Usuario no tiene su email validado.",
                            loginResponse
                    );
                }
            }

            loginResponse.setToken(jwtUtils.generateToken(user.get().getEmail(), user.get().getUserType()));
            loginResponse.setEmail(user.get().getEmail());
            loginResponse.setUsername(user.get().getEmail());
            loginResponse.setUserType(user.get().getUserType());
            return new ApiResponse<>(
                    200,
                    "Sesion iniciada con exito.",
                    loginResponse
            );
        }
    }

    public User getUserFromToken(String token) {
        if (!jwtUtils.validateToken(token)) {
            throw new RuntimeException("Token invalido");
        }
        String email = jwtUtils.getEmailFromToken(token);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

}
