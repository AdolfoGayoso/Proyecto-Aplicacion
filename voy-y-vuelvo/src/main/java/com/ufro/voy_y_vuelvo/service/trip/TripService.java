package com.ufro.voy_y_vuelvo.service.trip;

import com.ufro.voy_y_vuelvo.config.JwtUtils;
import com.ufro.voy_y_vuelvo.dto.ApiResponse;
import com.ufro.voy_y_vuelvo.dto.trip.CreateTripDto;
import com.ufro.voy_y_vuelvo.dto.trip.TripSearchRequestDto;
import com.ufro.voy_y_vuelvo.model.trips.Trip;
import com.ufro.voy_y_vuelvo.model.trips.TripStopOrder;
import com.ufro.voy_y_vuelvo.model.users.Publisher;
import com.ufro.voy_y_vuelvo.repository.StopRepository;
import com.ufro.voy_y_vuelvo.repository.TripRepository;
import com.ufro.voy_y_vuelvo.repository.TripStopOrderRepository;
import com.ufro.voy_y_vuelvo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripService {

    private final StopRepository stopRepository;
    private final TripRepository tripRepository;
    private final TripStopOrderService tripStopOrderService;
    private final TripStopOrderRepository tripStopOrderRepository;
    private final UserService userService;
    private final JwtUtils jwtUtils;


    public ApiResponse<List<Trip>> findAll() {
        List<Trip> trips = tripRepository.findAll();
        return new ApiResponse<>(HttpStatus.OK.value(), "Lista de viajes obtenida correctamente", trips);
    }

    public ApiResponse<Trip> findById(Long id) {
        Trip trip = tripRepository.findById(id).orElse(null);
        if (trip == null) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Viaje no encontrado", null);
        }
        return new ApiResponse<>(HttpStatus.OK.value(), "Viaje encontrado", trip);
    }

    public ApiResponse<List<TripStopOrder>> findAllStopOrdersFromTripId(Long tripId) {
        return tripStopOrderService.findAllStopsFromTripId(tripId);
    }

    public ApiResponse<List<Trip>> searchTripByFilters(TripSearchRequestDto request) {
        List<Trip> trips = tripRepository.findByFilters(
                request.getOrigin(),
                request.getDestination(),
                request.getDate(),
                request.getDepartureTime()
        );

        if (trips.isEmpty()) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "No se encontraron viajes.", null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "Viajes encontrados", trips);
    }

    public ApiResponse<Trip> createTrip(String token, CreateTripDto tripRequest) {

        boolean isValid = userService.isUserAuthenticatedAndValidRole(token, Publisher.class);

        if (!isValid) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "Acceso denegado: El usuario no tiene el rol necesario o el token es inválido.", null);
        }

        Publisher publisher = (Publisher) userService.findById(jwtUtils.getUserIdFromToken(token));

        if (tripRequest.getStopsIds().contains(null)) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Algunos IDs de parada son nulos.", null);
        }

        List<TripStopOrder> tripStopOrders = stopRepository.findAllById(tripRequest.getStopsIds()).stream()
                .map(stop -> {
                    TripStopOrder tripStopOrder = new TripStopOrder();
                    tripStopOrder.setStop(stop);  // Asignamos la parada
                    tripStopOrder.setAvailableSeats(tripRequest.getNumTotalSeats());
                    tripStopOrder.setStopOrder(tripRequest.getStopsIds().indexOf(stop.getId()));
                    tripStopOrder.setTrip(null);
                    return tripStopOrder;
                })
                .collect(Collectors.toList());

        if (tripStopOrders.size() != tripRequest.getStopsIds().size()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Algunas paradas no existen en la base de datos.", null);
        }

        Trip trip = getTrip(tripRequest, publisher, tripStopOrders);

        // Guardar el viaje en la base de datos
        tripRepository.save(trip);

        for (TripStopOrder stopOrder : tripStopOrders) {
            stopOrder.setTrip(trip);
        }
        tripStopOrderRepository.saveAll(tripStopOrders);

        return new ApiResponse<>(HttpStatus.CREATED.value(), "Viaje creado con éxito.", trip);
    }

    private static Trip getTrip(CreateTripDto tripRequest, Publisher publisher, List<TripStopOrder> tripStopOrders) {
        Trip trip = new Trip();
        trip.setActive(true);  // Activar por defecto el viaje
        trip.setPublisher(publisher);  // Asociar al publisher logueado
        trip.setNumTotalSeats(tripRequest.getNumTotalSeats());
        trip.setPlateNumber(tripRequest.getPlateNumber());
        trip.setPrice(tripRequest.getPrice());
        trip.setDepartureDate(tripRequest.getDepartureDate());
        trip.setDepartureTime(tripRequest.getDepartureTime());
        trip.setStops(tripStopOrders);  // Asociar las paradas
        return trip;
    }


}
