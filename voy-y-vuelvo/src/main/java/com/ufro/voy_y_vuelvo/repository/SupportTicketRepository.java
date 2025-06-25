package com.ufro.voy_y_vuelvo.repository;

import com.ufro.voy_y_vuelvo.model.tickets.SupportTicket;
import com.ufro.voy_y_vuelvo.model.trips.Trip;
import com.ufro.voy_y_vuelvo.model.users.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {
    List<SupportTicket> findByCustomer(Customer customer);

    List<SupportTicket> findByTripIn(Collection<Trip> trips);
}
