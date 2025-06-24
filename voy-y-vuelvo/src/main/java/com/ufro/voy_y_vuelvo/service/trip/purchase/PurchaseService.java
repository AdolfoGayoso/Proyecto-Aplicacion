package com.ufro.voy_y_vuelvo.service.trip.purchase;

import com.ufro.voy_y_vuelvo.config.JwtUtils;
import com.ufro.voy_y_vuelvo.dto.ApiResponse;
import com.ufro.voy_y_vuelvo.dto.trip.purchase.PurchaseResponseDto;
import com.ufro.voy_y_vuelvo.model.purchases.Purchase;
import com.ufro.voy_y_vuelvo.model.trips.Stop;
import com.ufro.voy_y_vuelvo.model.trips.Trip;
import com.ufro.voy_y_vuelvo.model.trips.TripStopOrder;
import com.ufro.voy_y_vuelvo.model.users.Customer;
import com.ufro.voy_y_vuelvo.model.users.User;
import com.ufro.voy_y_vuelvo.repository.*;
import com.ufro.voy_y_vuelvo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final TripRepository tripRepository;
    private final StopRepository stopRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PurchaseRepository purchaseRepository;
    private final TripStopOrderRepository tripStopOrderRepository;
    private final UserService userService;
    private final PurchasePdfService purchasePdfService;

    public ApiResponse<PurchaseResponseDto> purchaseTicket(String token, Long tripId, Long stopIdFrom, Long stopIdTo, String rut, String email) throws IOException {

        // Verificar q viaje existe
        Trip trip = tripRepository.findById(tripId).orElse(null);
        if (trip == null) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Viaje no encontrado.", null);
        }

        // Verificar q las paradas existen
        Stop stopFrom = stopRepository.findById(stopIdFrom).orElse(null);
        Stop stopTo = stopRepository.findById(stopIdTo).orElse(null);
        if (stopFrom == null || stopTo == null) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Paradas inválidas.", null);
        }

        // Verificar disponibilidad de los asientos
        List<TripStopOrder> tripStopOrders = trip.getStops();
        boolean availableSeats = false;
        for (int i = 0; i < tripStopOrders.size(); i++) {
            TripStopOrder stopOrder = tripStopOrders.get(i);
            if (stopOrder.getStop().equals(stopFrom) && stopOrder.getAvailableSeats() > 0) {
                availableSeats = true;
                // Reducir la disponibilidad de asientos entre las paradas de subida y bajada (excepto la bajada)
                for (int j = i; j < tripStopOrders.size() && !tripStopOrders.get(j).getStop().equals(stopTo); j++) {
                    TripStopOrder order = tripStopOrders.get(j);
                    order.setAvailableSeats(order.getAvailableSeats() - 1);  // Reducir solo 1 asiento
                }
                break;
            }
        }

        if (!availableSeats) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "No hay suficientes asientos disponibles para este tramo.", null);
        }

        String uuid = UUID.randomUUID().toString();

        Purchase purchase = new Purchase();
        purchase.setTrip(trip);
        purchase.setStopIdFrom(stopIdFrom);
        purchase.setStopIdTo(stopIdTo);
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase.setUuid(uuid);

        // 5. Si el usuario está logueado, asociamos su información
        if (token != null && !token.isEmpty()) {

            boolean isValid = userService.isUserAuthenticatedAndValidRole(token, Customer.class);
            if (!isValid) {
                return new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "Acceso denegado: El usuario no tiene el rol necesario o el token es inválido.", null);
            }

            User user = userRepository.findById(jwtUtils.getUserIdFromToken(token)).orElse(null);
            if (user != null) {
                purchase.setUser(user);
                purchase.setRut(user.getRut());
                purchase.setEmail(user.getEmail());
            } else {
                return new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "Usuario no autorizado.", null);
            }
        } else {
            // 6. Si el usuario no está logueado, se pasa la información manualmente
            if (rut != null && email != null) {
                // Aquí se puede crear un objeto "Usuario no registrado" si es necesario
                purchase.setUser(null);
                purchase.setRut(rut);
                purchase.setEmail(email);
            } else {
                return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Datos del usuario no proporcionados.", null);
            }
        }

        purchaseRepository.save(purchase);
        trip.setNumSeatsSold(trip.getNumSeatsSold() + 1);  // Incrementamos en 1
        tripRepository.save(trip);  // Guardamos el viaje actualizado
        tripStopOrderRepository.saveAll(tripStopOrders); // Paradas con disponibilidad actualizada



        PurchaseResponseDto purchaseDto = new PurchaseResponseDto();
        purchaseDto.setTripId(trip.getId());
        purchaseDto.setStopIdFrom(stopIdFrom);
        purchaseDto.setStopIdTo(stopIdTo);
        purchaseDto.setPurchaseDate(purchase.getPurchaseDate());
        purchaseDto.setRut(purchase.getRut());
        purchaseDto.setEmail(purchase.getEmail());
        purchaseDto.setUuid(uuid);

        return new ApiResponse<>(HttpStatus.CREATED.value(), "Compra realizada con éxito.", purchaseDto);
    }

    public ApiResponse<List<PurchaseResponseDto>> getPurchasesByUser(String token) {
        // Validar el token y obtener el usuario
        if (!jwtUtils.validateToken(token)) {
            return new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "Token inválido o expirado", null);
        }

        String userEmail = jwtUtils.getEmailFromToken(token);
        User user = userRepository.findByEmail(userEmail).orElse(null);

        if (user == null) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Usuario no encontrado", null);
        }

        // Obtener las compras del usuario
        List<Purchase> purchases = purchaseRepository.findByUser(user);

        if (purchases.isEmpty()) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "No se encontraron compras", null);
        }

        // Convertir las compras a DTOs
        List<PurchaseResponseDto> purchaseDtos = purchases.stream().map(purchase -> {
            PurchaseResponseDto dto = new PurchaseResponseDto();
            dto.setTripId(purchase.getTrip().getId());
            dto.setStopIdFrom(purchase.getStopIdFrom());
            dto.setStopIdTo(purchase.getStopIdTo());
            dto.setPurchaseDate(purchase.getPurchaseDate());
            dto.setRut(purchase.getRut());
            dto.setEmail(purchase.getEmail());
            dto.setUuid(purchase.getUuid());
            return dto;
        }).collect(Collectors.toList());

        // Retornar la respuesta con las compras
        return new ApiResponse<>(HttpStatus.OK.value(), "Compras obtenidas correctamente", purchaseDtos);
    }
}
