/*

package app.DaoS;

import app.config.HibernateConfig;
import app.entities.Parcel;
import app.enums.DeliveryStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// API to @before all etc.
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ParcelDAOTest {

    private EntityManagerFactory emf;
    private ParcelDAO parcelDAO;

    @BeforeAll
    void setup() {
        // Create an in-memory database for testing
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        parcelDAO = new ParcelDAO(emf);
    }

    @AfterAll
    public void teardown() {
        // closes the connection after tests are done
        emf.close();
    }

    @BeforeEach
        // cleans the test DB so the tests works.
        // without this there would exist objects in DB and the update and create tests would fail
    void setUp() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Parcel").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Test
    void createParcel() {
        // arrange
        Parcel parcel = new Parcel();
        // variables must not be null
        parcel.setTrackingNumber("Track12345");
        parcel.setSenderName("Philip");
        parcel.setReceiverName("Peter");
        parcel.setDeliveryStatus(DeliveryStatus.PENDING);

        // act
        parcelDAO.create(parcel);

        // assert
        List<Parcel> results = parcelDAO.parcelByTrackingNumber("Track12345");
        assertEquals(1, results.size(), "there should be exactly one Parcel");
        Parcel persistedParcel = results.get(0);
        assertEquals("Philip", persistedParcel.getSenderName(), "Sender name should match");
        assertEquals("Peter", persistedParcel.getReceiverName(), "Receiver name should match");
        assertEquals(DeliveryStatus.PENDING, persistedParcel.getDeliveryStatus(), "Delivery status should match");
    }

    @Test
    void parcelByTrackingNumber() {
        // Arrange
        Parcel parcel = new Parcel();
        parcel.setTrackingNumber("Track56789");
        parcel.setSenderName("Mette");
        parcel.setReceiverName("Julie");
        parcel.setDeliveryStatus(DeliveryStatus.DELIVERED);
        parcelDAO.createParcel(parcel);

        // Act
        parcelDAO.parcelByTrackingNumber("Track56789");

        // Assert
        List<Parcel> results = parcelDAO.parcelByTrackingNumber("Track56789");
        assertEquals(1, results.size(), "there should be exactly one Parcel");
        assertEquals("Mette", results.get(0).getSenderName(), "Sender name should match");
        assertEquals("Julie", results.get(0).getReceiverName(), "Receiver name should match");
        Parcel persistedParcel = results.get(0);
        assertEquals("Track56789", persistedParcel.getTrackingNumber());

    }

    @Test
    void getAllParcels() {
        // arrange
        List<Parcel> parcels = parcelDAO.getAllParcels();
        // act
        assertNotNull(parcels);
        // Assert
        assertTrue(true);
    }

    @Test
    void updateStatus() {
        // arrange
        Parcel parcel = new Parcel();
        parcel.setDeliveryStatus(DeliveryStatus.PENDING);
        parcel.setTrackingNumber("Track3456");
        parcel.setSenderName("Møffe");
        parcel.setReceiverName("Wilma");

        // act
        parcelDAO.createParcel(parcel);
        parcelDAO.updateStatus("Track3456", DeliveryStatus.DELIVERED);

        // assert
        List<Parcel> results = parcelDAO.parcelByTrackingNumber("Track3456");
        assertEquals(1, results.size(), "there should be exactly one Parcel");
        assertEquals(DeliveryStatus.DELIVERED, results.get(0).getDeliveryStatus(), "Delivery status should match");
        assertEquals("Møffe", results.get(0).getSenderName(), "Sender name should match");
        assertEquals("Wilma", results.get(0).getReceiverName(), "Receiver name should match");
        Parcel persistedParcel = results.get(0);
        assertEquals("Track3456", persistedParcel.getTrackingNumber());

    }

    @Test
    void deleteParcelByTrackingNumber_existingParcel_shouldDelete() {
        // arrange:
        Parcel parcel = new Parcel();
        parcel.setDeliveryStatus(DeliveryStatus.PENDING);
        parcel.setTrackingNumber("Track9876");
        parcel.setSenderName("Mikkel");
        parcel.setReceiverName("Valdemar");
        parcelDAO.createParcel(parcel);

        // act
        boolean deleted = parcelDAO.deleteParcelByTrackingNumber("Track9876");

        // assert
        assertTrue(deleted, "deleted parcel should be true");
        List<Parcel> results = parcelDAO.parcelByTrackingNumber("Track9876");
        assertEquals(0, results.size(), "there should be exactly 0 Parcel");
    }

    @Test
    void deleteParcelByTrackingNumber_nonExistentParcel_shouldReturnFalse() {
        // Arrange: ensure the tracking number does NOT exist
        String nonExistentTrackingNumber = "NonExistent123";

        // Act: attempt to delete
        boolean deleted = parcelDAO.deleteParcelByTrackingNumber(nonExistentTrackingNumber);

        // Assert: method returns false, DB remains unchanged
        assertFalse(deleted, "Delete should return false for non-existent parcel");

    }
}

 */