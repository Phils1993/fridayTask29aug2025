package app.DaoS;

import app.config.HibernateConfig;
import app.entities.Location;
import app.entities.Parcel;
import app.entities.Shipment;
import app.enums.DeliveryStatus;
import app.records.LocationDTO;
import app.records.ParcelDTO;
import app.records.ShipmentDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntegrationTest {

    private EntityManagerFactory emf;
    private LocationDAO locationDAO;
    private ShipmentDAO shipmentDAO;
    private ParcelDAO parcelDAO;

    @BeforeAll
    void setup() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        locationDAO = new LocationDAO(emf);
        shipmentDAO = new ShipmentDAO(emf);
        parcelDAO = new ParcelDAO(emf);
    }

    @AfterAll
    void tearDown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @Test
    void testCreateAndGetLocationDTO() {
        Location location = Location.builder()
                .latitude(55.6761)
                .longitude(12.5683)
                .address("Copenhagen")
                .build();

        Location saved = locationDAO.create(location);
        assertNotNull(saved.getId());

        LocationDTO dto = locationDAO.getDtoById(saved.getId());
        assertEquals("Copenhagen", dto.address());
        assertEquals(55.6761, dto.latitude(), 0.0001);
        assertEquals(12.5683, dto.longitude(), 0.0001);
    }

    @Test
    void testCreateAndGetParcelDTO() {
        Parcel parcel = Parcel.builder()
                .trackingNumber("TRACK999")
                .senderName("Philip")
                .receiverName("TestReceiver")
                .deliveryStatus(DeliveryStatus.SHIPPED)
                .updated(LocalDate.now())
                .build();

        Parcel saved = parcelDAO.create(parcel);
        assertNotNull(saved.getId());

        ParcelDTO dto = parcelDAO.getDtoById(saved.getId());
        assertEquals("TRACK999", dto.trackingNumber());
        assertEquals("Philip", dto.senderName());
        assertEquals("TestReceiver", dto.reveiverName());
        assertEquals(DeliveryStatus.SHIPPED.name(), dto.deliveryStatus());
    }

    @Test
    void testCreateAndGetShipmentDTO() {
        // Create locations
        Location source = locationDAO.create(Location.builder()
                .latitude(59.9139)
                .longitude(10.7522)
                .address("Oslo")
                .build());

        Location destination = locationDAO.create(Location.builder()
                .latitude(52.5200)
                .longitude(13.4050)
                .address("Berlin")
                .build());

        // Create parcel
        Parcel parcel = Parcel.builder()
                .trackingNumber("TRACK888")
                .senderName("SenderX")
                .receiverName("ReceiverY")
                .deliveryStatus(DeliveryStatus.PENDING)
                .updated(LocalDate.now())
                .build();

        Parcel savedParcel = parcelDAO.create(parcel);

        // Create shipment
        Shipment shipment = Shipment.builder()
                .parcel(savedParcel)
                .sourceLocation(source)
                .destinationLocation(destination)
                .shipmentDateTime(LocalDateTime.now())
                .build();

        Shipment savedShipment = shipmentDAO.create(shipment);
        assertNotNull(savedShipment.getId());

        ShipmentDTO dto = shipmentDAO.getDtoById(savedShipment.getId());
        assertEquals("TRACK888", dto.trackingNumber());
        assertEquals("Oslo", dto.sourceAddress());
        assertEquals("Berlin", dto.destinationAddress());
        assertNotNull(dto.shipmentDateTime());
    }

    @Test
    void testGetAllLocationDTOs() {
        List<LocationDTO> locations = locationDAO.getAllDtos();
        assertNotNull(locations);
        assertFalse(locations.isEmpty());
    }

    @Test
    void testGetAllParcelDTOs() {
        List<ParcelDTO> parcels = parcelDAO.getAllDtos();
        assertNotNull(parcels);
        assertFalse(parcels.isEmpty());
    }

    @Test
    void testGetAllShipmentDTOs() {
        List<ShipmentDTO> shipments = shipmentDAO.getAllDtos();
        assertNotNull(shipments);
        assertFalse(shipments.isEmpty());
    }
}
