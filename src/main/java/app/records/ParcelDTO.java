package app.records;

import java.time.LocalDate;

public record ParcelDTO(
        String trackingNumber,
        String senderName,
        String reveiverName,
        String deliveryStatus,
        LocalDate updated) {
}
