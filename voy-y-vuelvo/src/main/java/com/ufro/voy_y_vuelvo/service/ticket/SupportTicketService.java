package com.ufro.voy_y_vuelvo.service.ticket;

import com.ufro.voy_y_vuelvo.config.JwtUtils;
import com.ufro.voy_y_vuelvo.dto.ApiResponse;
import com.ufro.voy_y_vuelvo.model.purchases.Purchase;
import com.ufro.voy_y_vuelvo.model.tickets.SupportTicket;
import com.ufro.voy_y_vuelvo.model.tickets.SupportTicketStatus;
import com.ufro.voy_y_vuelvo.model.trips.Trip;
import com.ufro.voy_y_vuelvo.model.users.Customer;
import com.ufro.voy_y_vuelvo.model.users.Publisher;
import com.ufro.voy_y_vuelvo.repository.*;
import com.ufro.voy_y_vuelvo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SupportTicketService {

    private final SupportTicketRepository supportTicketRepository;
    private final PurchaseRepository purchaseRepository;
    private final UserService userService;
    private final CustomerRepository customerRepository;
    private final JwtUtils jwtUtils;
    private final PublisherRepository publisherRepository;
    private final TripRepository tripRepository;

    public ApiResponse<SupportTicket> createSupportTicket(String token, String purchaseUuid, String title, String description) {

        Purchase purchase = purchaseRepository.findByUuid(purchaseUuid);
        if (purchase == null) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Compra no encontrada.", null);
        }

        Trip trip = purchase.getTrip();

        boolean isValid = userService.isUserAuthenticatedAndValidRole(token, Customer.class);

        if (!isValid) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "Acceso denegado: El usuario no tiene el rol necesario o el token es inválido.", null);
        }

        Optional<Customer> customer = customerRepository.findByEmail(jwtUtils.getEmailFromToken(token));

        if (customer.isEmpty()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "El usuario no se encuentra registrado", null);
        }

        SupportTicket ticket = new SupportTicket();
        ticket.setPurchase(purchase);
        ticket.setCustomer(customer.get());
        ticket.setTrip(trip);
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setStatus(SupportTicketStatus.OPEN);

        supportTicketRepository.save(ticket);

        return new ApiResponse<>(HttpStatus.CREATED.value(), "Ticket creado con éxito", ticket);
    }

    public ApiResponse<List<SupportTicket>> getTicketsByCustomer(String token) {

        boolean isValid = userService.isUserAuthenticatedAndValidRole(token, Customer.class);

        if (!isValid) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "Acceso denegado: El usuario no tiene el rol necesario o el token es inválido.", null);
        }

        Optional<Customer> customeroptional = customerRepository.findByEmail(jwtUtils.getEmailFromToken(token));

        if (customeroptional.isEmpty()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "El usuario no se encuentra registrado", null);
        }

        Customer customer = customeroptional.get();

        List<SupportTicket> tickets = supportTicketRepository.findByCustomer(customer);

        if (tickets.isEmpty()) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "No se encontraron tickets.", null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "Tickets obtenidos correctamente", tickets);

    }

    public ApiResponse<List<SupportTicket>> getTicketsByPublisher(String token) {

        boolean isValid = userService.isUserAuthenticatedAndValidRole(token, Publisher.class);
        if (!isValid) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "Acceso denegado: El usuario no tiene el rol necesario o el token es inválido.", null);
        }

        Publisher publisher = publisherRepository.findByEmail(jwtUtils.getEmailFromToken(token));

        if (publisher == null) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "El publisher no se encuentra registrado", null);
        }

        List<Trip> publisherTrips = tripRepository.findByPublisher(publisher);

        List<SupportTicket> tickets = supportTicketRepository.findByTripIn(publisherTrips);

        if (tickets.isEmpty()) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "No se encontraron tickets para los viajes del editor.", null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "Tickets obtenidos correctamente.", tickets);
    }

    public ApiResponse<SupportTicket> updateTicketStatus(String token, Long ticketId, SupportTicketStatus status) {

        boolean isValid = userService.isUserAuthenticatedAndValidRole(token, Publisher.class);
        if (!isValid) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "Acceso denegado: El usuario no tiene el rol necesario o el token es inválido.", null);
        }

        Optional<SupportTicket> ticketOptional = supportTicketRepository.findById(ticketId);

        if (ticketOptional.isEmpty()) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Ticket no encontrado.", null);
        }

        SupportTicket ticket = ticketOptional.get();

        ticket.setStatus(status);
        supportTicketRepository.save(ticket);

        return new ApiResponse<>(HttpStatus.OK.value(), "Estado del ticket actualizado con éxito.", ticket);
    }


}
