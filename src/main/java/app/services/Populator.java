package app.services;

import app.DTOMapper.DTOMapper;
import app.DaoS.LocationDAO;
import app.DaoS.ParcelDAO;
import app.DaoS.ShipmentDAO;
import app.entities.Location;
import app.entities.Parcel;
import app.entities.Shipment;
import app.enums.DeliveryStatus;
import app.records.ParcelDTO;
import app.records.LocationDTO;
import app.records.ShipmentDTO;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Populator {

    public static List<ParcelDTO> populate(ParcelDAO parcelDAO, LocationDAO locationDAO, ShipmentDAO shipmentDAO) {

        List<ParcelDTO> parcelDTOs = new ArrayList<>();

        // --- Create Parcels ---
        Parcel p1 = Parcel.builder()
                .trackingNumber("TRACK001")
                .senderName("Alice")
                .receiverName("Bob")
                .deliveryStatus(DeliveryStatus.PENDING)
                .updated(LocalDate.now())
                .build();

        Parcel p2 = Parcel.builder()
                .trackingNumber("TRACK002")
                .senderName("Charlie")
                .receiverName("David")
                .deliveryStatus(DeliveryStatus.SHIPPED)
                .updated(LocalDate.now())
                .build();

        Parcel p3 = Parcel.builder()
                .trackingNumber("TRACK003")
                .senderName("Eve")
                .receiverName("Frank")
                .deliveryStatus(DeliveryStatus.DELIVERED)
                .updated(LocalDate.now())
                .build();

        parcelDAO.create(p1);
        parcelDAO.create(p2);
        parcelDAO.create(p3);

        // --- Create Locations ---
        Location copenhagen = locationDAO.create(Location.builder()
                .latitude(55.6761)
                .longitude(12.5683)
                .address("Copenhagen")
                .build());

        Location oslo = locationDAO.create(Location.builder()
                .latitude(59.9139)
                .longitude(10.7522)
                .address("Oslo")
                .build());

        Location stockholm = locationDAO.create(Location.builder()
                .latitude(59.3293)
                .longitude(18.0686)
                .address("Stockholm")
                .build());

        Location berlin = locationDAO.create(Location.builder()
                .latitude(52.5200)
                .longitude(13.4050)
                .address("Berlin")
                .build());

        // --- Create Shipments ---
        Shipment s1 = Shipment.builder()
                .parcel(p1)
                .sourceLocation(copenhagen)
                .destinationLocation(oslo)
                .shipmentDateTime(LocalDateTime.now().minusDays(1))
                .build();

        Shipment s2 = Shipment.builder()
                .parcel(p2)
                .sourceLocation(stockholm)
                .destinationLocation(berlin)
                .shipmentDateTime(LocalDateTime.now())
                .build();

        Shipment s3 = Shipment.builder()
                .parcel(p3)
                .sourceLocation(oslo)
                .destinationLocation(copenhagen)
                .shipmentDateTime(LocalDateTime.now().minusDays(2))
                .build();

        p1.addShipment(s1);
        p2.addShipment(s2);
        p3.addShipment(s3);

        shipmentDAO.create(s1);
        shipmentDAO.create(s2);
        shipmentDAO.create(s3);

        // --- Convert to DTOs ---
        ParcelDTO dto1 = DTOMapper.toParcelDTO(p1);
        ParcelDTO dto2 = DTOMapper.toParcelDTO(p2);
        ParcelDTO dto3 = DTOMapper.toParcelDTO(p3);

        ShipmentDTO shipmentDTO1 = DTOMapper.toShipmentDTO(s1);
        ShipmentDTO shipmentDTO2 = DTOMapper.toShipmentDTO(s2);
        ShipmentDTO shipmentDTO3 = DTOMapper.toShipmentDTO(s3);

        LocationDTO locDTO1 = DTOMapper.toLocationDTO(copenhagen);
        LocationDTO locDTO2 = DTOMapper.toLocationDTO(oslo);
        LocationDTO locDTO3 = DTOMapper.toLocationDTO(stockholm);
        LocationDTO locDTO4 = DTOMapper.toLocationDTO(berlin);

        // --- Print DTOs ---
        System.out.println(" Parcel DTOs:");
        System.out.println(dto1);
        System.out.println(dto2);
        System.out.println(dto3);

        System.out.println("\n Shipment DTOs:");
        System.out.println(shipmentDTO1);
        System.out.println(shipmentDTO2);
        System.out.println(shipmentDTO3);

        System.out.println("\n Location DTOs:");
        System.out.println(locDTO1);
        System.out.println(locDTO2);
        System.out.println(locDTO3);
        System.out.println(locDTO4);

        parcelDTOs.add(dto1);
        parcelDTOs.add(dto2);
        parcelDTOs.add(dto3);

        return parcelDTOs;
    }

}
