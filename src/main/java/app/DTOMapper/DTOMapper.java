package app.DTOMapper;

import app.entities.Location;
import app.entities.Parcel;
import app.entities.Shipment;
import app.records.LocationDTO;
import app.records.ParcelDTO;
import app.records.ShipmentDTO;

public class DTOMapper {

    public static LocationDTO toLocationDTO(Location location) {
        if (location == null) return null;
        return new LocationDTO(
                location.getId(),
                location.getLatitude(),
                location.getLongitude(),
                location.getAddress()
        );
    }

    public static ParcelDTO toParcelDTO(Parcel parcel) {
        return new ParcelDTO(
                parcel.getId(),
                parcel.getTrackingNumber(),
                parcel.getSenderName(),
                parcel.getReceiverName(),
                parcel.getDeliveryStatus().name(),
                parcel.getUpdated()
        );
    }

    public static ShipmentDTO toShipmentDTO(Shipment shipment) {
        return new ShipmentDTO(
                shipment.getId(),
                shipment.getParcel().getTrackingNumber(),
                shipment.getSourceLocation().getAddress(),
                shipment.getDestinationLocation().getAddress(),
                shipment.getShipmentDateTime()
        );
    }
}
