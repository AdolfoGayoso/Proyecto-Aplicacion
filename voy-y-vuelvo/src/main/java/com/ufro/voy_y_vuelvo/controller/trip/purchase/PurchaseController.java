package com.ufro.voy_y_vuelvo.controller.trip.purchase;

import com.ufro.voy_y_vuelvo.dto.ApiResponse;
import com.ufro.voy_y_vuelvo.dto.trip.purchase.PurchaseRequestDto;
import com.ufro.voy_y_vuelvo.dto.trip.purchase.PurchaseResponseDto;
import com.ufro.voy_y_vuelvo.service.trip.purchase.PurchasePdfService;
import com.ufro.voy_y_vuelvo.service.trip.purchase.PurchaseService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final PurchasePdfService purchasePdfService;

    @PostMapping("/buy")
    public ApiResponse<PurchaseResponseDto> purchaseTicket(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody PurchaseRequestDto request) throws IOException {

        // ✅ Limpieza del token si viene con "Bearer "
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        return purchaseService.purchaseTicket(
                token,
                request.getTripId(),
                request.getStopIdFrom(),
                request.getStopIdTo(),
                request.getRut(),
                request.getEmail()
        );
    }

    @GetMapping("/send-pdf")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<String> processPurchase(@RequestParam String uuid) {
        try {
            boolean isSuccessful = purchasePdfService.processPurchaseAndSendPdf(uuid);

            if (isSuccessful) {
                return new ApiResponse<>(HttpStatus.OK.value(), "PDF enviado con éxito al correo del usuario.", null);
            } else {
                return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Hubo un error al enviar el PDF.", null);
            }
        } catch (IOException | MessagingException e) {
            return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno al procesar la compra o enviar el PDF.", null);
        }
    }

}
