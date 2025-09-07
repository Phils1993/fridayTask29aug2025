package app.records;

import java.time.LocalDateTime;

public record ShipmentDTO(
        int id,
        String trackingNumber,
        String sourceAddress,
        String destinationAddress,
        LocalDateTime shipmentDateTime) {
}
