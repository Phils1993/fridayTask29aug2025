package app.services;

import app.DaoS.LocationDAO;
import app.DaoS.ParcelDAO;
import app.DaoS.ShipmentDAO;
import app.entities.Location;
import app.entities.Parcel;
import app.entities.Shipment;
import app.enums.DeliveryStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Populator {

    private final LocationDAO locationDAO;
    private final ParcelDAO parcelDAO;
    private final ShipmentDAO shipmentDAO;

    public Populator(LocationDAO locationDAO, ParcelDAO parcelDAO, ShipmentDAO shipmentDAO) {
        this.locationDAO = locationDAO;
        this.parcelDAO = parcelDAO;
        this.shipmentDAO = shipmentDAO;
    }

    public void populate() {
        // Create Locations
        Location copenhagen = locationDAO.create(Location.builder()
                .latitude(55.6761)
                .longitude(12.5683)
                .address("Copenhagen")
                .build());

        Location aarhus = locationDAO.create(Location.builder()
                .latitude(56.1629)
                .longitude(10.2039)
                .address("Aarhus")
                .build());

        Location oslo = locationDAO.create(Location.builder()
                .latitude(59.9139)
                .longitude(10.7522)
                .address("Oslo")
                .build());

        Location berlin = locationDAO.create(Location.builder()
                .latitude(52.5200)
                .longitude(13.4050)
                .address("Berlin")
                .build());

        //  Create Parcels
        Parcel parcel1 = parcelDAO.create(Parcel.builder()
                .trackingNumber("TRACK001")
                .senderName("Alice")
                .receiverName("Bob")
                .deliveryStatus(DeliveryStatus.PENDING)
                .updated(LocalDate.now())
                .build());

        Parcel parcel2 = parcelDAO.create(Parcel.builder()
                .trackingNumber("TRACK002")
                .senderName("Charlie")
                .receiverName("David")
                .deliveryStatus(DeliveryStatus.SHIPPED)
                .updated(LocalDate.now())
                .build());

        //  Create Shipments
        Shipment shipment1 = shipmentDAO.create(Shipment.builder()
                .parcel(parcel1)
                .sourceLocation(copenhagen)
                .destinationLocation(aarhus)
                .shipmentDateTime(LocalDateTime.now().minusDays(2))
                .build());

        Shipment shipment2 = shipmentDAO.create(Shipment.builder()
                .parcel(parcel1)
                .sourceLocation(aarhus)
                .destinationLocation(oslo)
                .shipmentDateTime(LocalDateTime.now().minusDays(1))
                .build());

        Shipment shipment3 = shipmentDAO.create(Shipment.builder()
                .parcel(parcel2)
                .sourceLocation(oslo)
                .destinationLocation(berlin)
                .shipmentDateTime(LocalDateTime.now())
                .build());

        //  Output for verification
        System.out.println(" Parcels created:");
        List.of(parcel1, parcel2).forEach(p -> System.out.println(" - " + p.getTrackingNumber()));

        System.out.println("\n Shipments created:");
        List.of(shipment1, shipment2, shipment3).forEach(s ->
                System.out.println(" - Parcel: " + s.getParcel().getTrackingNumber() +
                        " | From: " + s.getSourceLocation().getAddress() +
                        " → To: " + s.getDestinationLocation().getAddress()));
    }
}
