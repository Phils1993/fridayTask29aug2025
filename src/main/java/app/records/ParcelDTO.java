package app.records;

import java.time.LocalDate;

public record ParcelDTO(
        int id,
        String trackingNumber,
        String senderName,
        String reveiverName,
        String deliveryStatus,
        LocalDate updated) {
}
