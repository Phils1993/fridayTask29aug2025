package app.DaoS;

import app.config.HibernateConfig;
import app.entities.Location;
import app.entities.Parcel;
import app.entities.Shipment;
import app.enums.DeliveryStatus;
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

    // LOCATION DAO TESTS

    @Test
    void testCreateGetUpdateDeleteLocation() {
        Location location = Location.builder()
                .latitude(48.8566)
                .longitude(2.3522)
                .address("Paris")
                .build();

        Location saved = locationDAO.create(location);
        assertNotNull(saved.getId());

        Location fetched = locationDAO.getById(saved.getId());
        assertEquals("Paris", fetched.getAddress());

        fetched.setAddress("Paris Updated");
        Location updated = locationDAO.update(fetched);
        assertEquals("Paris Updated", updated.getAddress());

        boolean deleted = locationDAO.delete(updated.getId());
        assertTrue(deleted);
    }

    @Test
    void testGetAllLocationDTOs() {
        List<Location> locations = locationDAO.getAll();
        assertNotNull(locations);
        assertFalse(locations.isEmpty());
    }

    // PARCEL DAO TESTS

    @Test
    void testCreateGetUpdateDeleteParcel() {
        Parcel parcel = Parcel.builder()
                .trackingNumber("TRACKTEST")
                .senderName("TestSender")
                .receiverName("TestReceiver")
                .deliveryStatus(DeliveryStatus.PENDING)
                .updated(LocalDate.now())
                .build();

        Parcel saved = parcelDAO.create(parcel);
        assertNotNull(saved.getId());

        Parcel fetched = parcelDAO.getById(saved.getId());
        assertEquals("TestSender", fetched.getSenderName());

        fetched.setSenderName("UpdatedSender");
        Parcel updated = parcelDAO.update(fetched);
        assertEquals("UpdatedSender", updated.getSenderName());

        boolean deleted = parcelDAO.delete(updated.getId());
        assertTrue(deleted);
    }


    // SHIPMENT DAO TESTS

    @Test
    void testCreateGetUpdateDeleteShipment() {
        Location source = locationDAO.create(Location.builder()
                .latitude(40.7128)
                .longitude(-74.0060)
                .address("New York")
                .build());

        Location destination = locationDAO.create(Location.builder()
                .latitude(34.0522)
                .longitude(-118.2437)
                .address("Los Angeles")
                .build());

        Parcel parcel = parcelDAO.create(Parcel.builder()
                .trackingNumber("TRACKDAO")
                .senderName("DAO Sender")
                .receiverName("DAO Receiver")
                .deliveryStatus(DeliveryStatus.SHIPPED)
                .updated(LocalDate.now())
                .build());

        Shipment shipment = Shipment.builder()
                .parcel(parcel)
                .sourceLocation(source)
                .destinationLocation(destination)
                .shipmentDateTime(LocalDateTime.now())
                .build();

        Shipment saved = shipmentDAO.create(shipment);
        assertNotNull(saved.getId());

        Shipment fetched = shipmentDAO.getById(saved.getId());
        assertEquals("New York", fetched.getSourceLocation().getAddress());

        fetched.setShipmentDateTime(LocalDateTime.now().minusDays(1));
        Shipment updated = shipmentDAO.update(fetched);
        assertTrue(updated.getShipmentDateTime().isBefore(LocalDateTime.now()));

        boolean deleted = shipmentDAO.delete(updated.getId());
        assertTrue(deleted);
    }

}
