package com.ufro.voy_y_vuelvo.service.trip.purchase;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.ufro.voy_y_vuelvo.model.purchases.Purchase;
import com.ufro.voy_y_vuelvo.model.trips.Stop;
import com.ufro.voy_y_vuelvo.model.trips.Trip;
import com.ufro.voy_y_vuelvo.model.trips.TripStopOrder;
import com.ufro.voy_y_vuelvo.repository.PurchaseRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PurchasePdfService {

    private final JavaMailSender mailSender;
    private final PurchaseRepository purchaseRepository;

    public String generatePdfFromPurchaseUuid(String uuid) throws IOException {

        Purchase purchase = purchaseRepository.findByUuid(uuid);

        Trip trip = purchase.getTrip();
        List<TripStopOrder> stopOrders = trip.getStops();

        Stop stopFrom = stopOrders.getFirst().getStop();
        Stop stopTo = stopOrders.getLast().getStop();

        // Define la carpeta donde se guardará el archivo PDF
        String directoryPath = "src/main/java/com/ufro/voy_y_vuelvo/service/trip/purchase/generated/";

        File directory = new File(directoryPath);

        String fileName = purchase.getUuid() + ".pdf";
        String filePath = directoryPath + fileName;

        // Crea el escritor PDF y el documento
        PdfWriter writer = new PdfWriter(new FileOutputStream(new File(filePath)));
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Añadir información al PDF
        document.add(new Paragraph("Información de la compra"));
        document.add(new Paragraph("Subida: " + stopFrom.getName()));
        document.add(new Paragraph("Bajada: " + stopTo.getName()));
        document.add(new Paragraph("Hora de salida: " + trip.getDepartureTime()));
        document.add(new Paragraph("Patente: " + trip.getPlateNumber()));
        document.add(new Paragraph("Pasajero: " + purchase.getRut()));

        // Cerrar el documento
        document.close();

        return filePath; // Retorna la ruta donde se guardó el PDF
    }

    public void sendPdfByEmail(String email, String filePath) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Configurar el correo
        helper.setTo(email);
        helper.setFrom("voyyvuelvocomprapasajes@gmail.com");
        helper.setSubject("Detalles de tu compra de pasaje");
        helper.setText("Aquí están los detalles de tu compra. El archivo adjunto contiene la información de tu pasaje.");

        // Adjuntar el archivo PDF
        File file = new File(filePath);

        helper.addAttachment(file.getName(), file);

        // Enviar el correo
        mailSender.send(message);
    }

    public boolean processPurchaseAndSendPdf(String uuid) throws IOException, MessagingException {

        boolean success = false;

        try {
            // Generar el PDF
            String pdfFilePath = generatePdfFromPurchaseUuid(uuid);
            // Obtener la compra asociada
            Purchase purchase = purchaseRepository.findByUuid(uuid);
            // Verificar si se encuentra la compra
            if (purchase == null) {
                throw new IllegalArgumentException("Compra no encontrada con el UUID proporcionado");
            }
            // Enviar el PDF por correo electrónico
            sendPdfByEmail(purchase.getEmail(), pdfFilePath);
            // Eliminar el archivo PDF una vez enviado
            File file = new File(pdfFilePath);
            if (file.exists()) {
                file.delete(); // Eliminar el archivo PDF
            }
            success = true; // Marca como exitoso
        } catch (Exception e) {
            success = false; // Marca como fallido
        }

        return success;
    }

}

