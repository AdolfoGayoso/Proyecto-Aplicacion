package com.ufro.voy_y_vuelvo.service.user;

import com.ufro.voy_y_vuelvo.config.JwtUtils;
import com.ufro.voy_y_vuelvo.dto.ApiResponse;
import com.ufro.voy_y_vuelvo.model.users.Customer;
import com.ufro.voy_y_vuelvo.model.users.Publisher;
import com.ufro.voy_y_vuelvo.model.users.User;
import com.ufro.voy_y_vuelvo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;

    private final CustomerService customerService;
    private final PublisherService publisherService;

    public ApiResponse<Object> getUserInfo(String token) {

        if (!jwtUtils.validateToken(token)) {
            return new ApiResponse<>(401, "Token inválido o expirado", null);
        }

        Optional<User> userOptional = userRepository.findByEmail(jwtUtils.getEmailFromToken(token));

        if (userOptional.isEmpty()) {
            return new ApiResponse<>(404, "Usuario no encontrado", null);
        }

        User user = userOptional.get();

        if (user instanceof Customer customer) {
            return new ApiResponse<>(200, "Datos de cliente obtenidos correctamente", customerService.toDto(customer));
        } else {
            Publisher publisher = (Publisher) user;
            return new ApiResponse<>(200, "Datos de editor obtenidos correctamente", publisherService.toDto(publisher));
        }
    }
}
