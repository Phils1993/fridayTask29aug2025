package app.DaoS;

import app.config.HibernateConfig;
import app.entities.Location;
import app.entities.Parcel;
import app.entities.Shipment;
import app.enums.DeliveryStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import app.config.HibernateConfig;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntegrationTest {

    private EntityManagerFactory emf;
    private LocationDAO locationDAO;
    private ShipmentDAO shipmentDAO;
    private Parcel testParcel;

    @BeforeAll
    void setup() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        locationDAO = new LocationDAO(emf);
        shipmentDAO = new ShipmentDAO(emf);

        // Create and persist a test Parcel (no ParcelDAO in your code yet)
        testParcel = new Parcel("TRACK123", "Alice", "Bob", DeliveryStatus.PENDING, LocalDate.now());

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(testParcel);
        em.getTransaction().commit();
        em.close();
    }

    @AfterAll
    void tearDown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @Test
    void testCreateAndFindLocation() {
        Location loc = new Location(55.6761, 12.5683, "Copenhagen, Denmark");
        Location saved = locationDAO.create(loc);

        assertNotNull(saved.getId());
        Location found = locationDAO.getById(saved.getId());
        assertEquals("Copenhagen, Denmark", found.getAddress());
    }

    @Test
    void testCreateShipment() {
        Location source = locationDAO.create(new Location(55.6761, 12.5683, "Copenhagen"));
        Location destination = locationDAO.create(new Location(59.9139, 10.7522, "Oslo"));

        Shipment shipment = new Shipment(testParcel, source, destination, LocalDateTime.now());
        Shipment saved = shipmentDAO.create(shipment);

        assertNotNull(saved.getId());
        assertEquals(testParcel.getId(), saved.getParcel().getId());
        assertEquals("Copenhagen", saved.getSourceLocation().getAddress());
        assertEquals("Oslo", saved.getDestinationLocation().getAddress());
    }

}
