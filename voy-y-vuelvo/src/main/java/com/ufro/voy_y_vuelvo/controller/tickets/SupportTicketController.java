package com.ufro.voy_y_vuelvo.controller.tickets;

import com.ufro.voy_y_vuelvo.dto.ApiResponse;
import com.ufro.voy_y_vuelvo.dto.ticket.CreateTicketRequestDto;
import com.ufro.voy_y_vuelvo.dto.ticket.UpdateTicketStatusRequestDto;
import com.ufro.voy_y_vuelvo.model.tickets.SupportTicket;
import com.ufro.voy_y_vuelvo.service.ticket.SupportTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
@RequiredArgsConstructor
public class SupportTicketController {

    private final SupportTicketService supportTicketService;

    @PostMapping("/create")
    public ApiResponse<?> createSupportTicket(@RequestHeader("Authorization") String token, @RequestBody CreateTicketRequestDto createTicketRequestDto) {
        return supportTicketService.createSupportTicket(token, createTicketRequestDto.getPurchaseUuid(), createTicketRequestDto.getTittle(), createTicketRequestDto.getDescription());
    }

    @GetMapping("/customer")
    public ApiResponse<List<SupportTicket>> getTicketsByCustomer(@RequestHeader("Authorization") String token) {
        return supportTicketService.getTicketsByCustomer(token);
    }

    @GetMapping("/publisher")
    public ApiResponse<List<SupportTicket>> getTicketsByPublisher(@RequestHeader("Authorization") String token) {
        return supportTicketService.getTicketsByPublisher(token);
    }

    @PutMapping("/set-status")
    public ApiResponse<SupportTicket> updateTicketStatus(@RequestHeader("Authorization") String token, @RequestBody UpdateTicketStatusRequestDto requestDto) {
        return supportTicketService.updateTicketStatus(token, requestDto.getTicketId(), requestDto.getNewStatus());
    }

}


