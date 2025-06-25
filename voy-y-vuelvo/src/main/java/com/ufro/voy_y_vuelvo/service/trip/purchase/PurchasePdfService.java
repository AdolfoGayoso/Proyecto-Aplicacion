package com.ufro.voy_y_vuelvo.service.trip.purchase;

import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
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

        // Variables pdf

        String subida = stopFrom.getName();
        String bajada = stopTo.getName();
        String horaDeSalida = trip.getDepartureTime().toString();
        String fechaViaje = trip.getDepartureDate().toString();
        String patente = trip.getPlateNumber();
        String rut = purchase.getRut();
        String precio = trip.getPrice().toString();
        String codigoReserva = uuid;


        // Añadir información al PDF
        PdfFont fontBold = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD);
        PdfFont fontNormal = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA);

        // Encabezado
        Paragraph header = new Paragraph("PASAJE VOY Y VUELVO")
                .setFont(fontBold)
                .setFontSize(18)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
        document.add(header);

        // Código de reserva
        Paragraph codigo = new Paragraph("Código: " + codigoReserva)
                .setFont(fontBold)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(15);
        document.add(codigo);

        // Tabla con información principal
        float[] columnWidths = {150f, 200f};
        Table table = new Table(columnWidths);
        table.setMarginBottom(20);

        // Estilo de celda
        Color lightGray = ColorConstants.LIGHT_GRAY;
        Cell cellStyleKey = new Cell().setFont(fontBold).setBackgroundColor(lightGray).setPadding(5);
        Cell cellStyleValue = new Cell().setFont(fontNormal).setPadding(5);

        // Llenar tabla

        table.addCell(cellStyleKey.clone(true).add(new Paragraph("RUT")));
        table.addCell(cellStyleValue.clone(true).add(new Paragraph(rut)));

        table.addCell(cellStyleKey.clone(true).add(new Paragraph("Fecha de Viaje")));
        table.addCell(cellStyleValue.clone(true).add(new Paragraph(fechaViaje)));

        table.addCell(cellStyleKey.clone(true).add(new Paragraph("Hora de Salida")));
        table.addCell(cellStyleValue.clone(true).add(new Paragraph(horaDeSalida)));

        table.addCell(cellStyleKey.clone(true).add(new Paragraph("Subida")));
        table.addCell(cellStyleValue.clone(true).add(new Paragraph(subida)));

        table.addCell(cellStyleKey.clone(true).add(new Paragraph("Bajada")));
        table.addCell(cellStyleValue.clone(true).add(new Paragraph(bajada)));

        table.addCell(cellStyleKey.clone(true).add(new Paragraph("Patente Bus")));
        table.addCell(cellStyleValue.clone(true).add(new Paragraph(patente)));

        table.addCell(cellStyleKey.clone(true).add(new Paragraph("Precio")));
        table.addCell(cellStyleValue.clone(true).add(new Paragraph("$" + precio)));

        document.add(table);

        // Notas importantes
        Paragraph notas = new Paragraph("NOTAS IMPORTANTES:")
                .setFont(fontBold)
                .setMarginTop(20)
                .setMarginBottom(5);
        document.add(notas);

        Paragraph nota1 = new Paragraph("• Presentar este documento y cédula de identidad al abordar.")
                .setFont(fontNormal)
                .setMarginBottom(3);
        Paragraph nota2 = new Paragraph("• Llegar al menos 15 minutos antes de la salida.")
                .setFont(fontNormal)
                .setMarginBottom(3);

        document.add(nota1);
        document.add(nota2);

        // Agregar código QR con el UUID
        Paragraph qrTitle = new Paragraph("Presentar QR a la subida del bus:")
                .setFont(fontBold)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(20)
                .setMarginBottom(10);
        document.add(qrTitle);

        // Crear código QR
        BarcodeQRCode qrCode = new BarcodeQRCode(uuid);
        PdfFormXObject qrCodeObject = qrCode.createFormXObject(ColorConstants.BLACK, pdf);
        Image qrCodeImage = new Image(qrCodeObject).setWidth(100).setHeight(100).setHorizontalAlignment(HorizontalAlignment.CENTER);
        document.add(qrCodeImage);

        // Pie de página
        Paragraph qr = new Paragraph("Gracias por preferirnos • www.voyyvuelvo.cl")
                .setFont(fontNormal)
                .setFontSize(10)
                .setFontColor(ColorConstants.GRAY)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(20);
        document.add(qr);

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

