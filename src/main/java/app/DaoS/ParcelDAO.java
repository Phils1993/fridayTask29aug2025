package app.DaoS;

import app.Threads.Singleton;
import app.annotation.VeryImportant;
import app.entities.Parcel;
import app.enums.DeliveryStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
@VeryImportant

public class ParcelDAO {

    private final EntityManagerFactory emf;
    // private final Singleton singleton; // behøver ikke singleton class, fordi EntityManagerFactory
    // er designet til at være en singleton-like. HibernateConfig sikre der KUN oprettes en per runtime

    public ParcelDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }



    public void createParcel(Parcel parcel) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(parcel);
            em.getTransaction().commit();
        } catch (Exception ex) {
            throw new RuntimeException("Error creating a new Parcel ", ex);
        }
    }

    public List<Parcel> parcelByTrackingNumber(String trackingNumber) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Parcel> query = em.createQuery(
                    "SELECT p FROM Parcel p WHERE p.trackingNumber = :trackingNumber", Parcel.class
            );
            query.setParameter("trackingNumber", trackingNumber);
            return query.getResultList();
        } catch (Exception ex) {
            throw new RuntimeException("Error getting parcel by trackingNumber ", ex);
        }
    }

    public List<Parcel> getAllParcels() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Parcel> query = em.createQuery("SELECT p FROM Parcel p", Parcel.class);
            return query.getResultList();
        } catch (Exception ex) {
            throw new RuntimeException("Error getting all parcels ", ex);
        }
    }


    // em.find bruger PK i DB som reference. Denne metoder tager tracking num som parameter
    // og derfor virker denne metode ikke.
    /*
    public void updateStatus(String trackingNumber, DeliveryStatus newStatus) {
        try (EntityManager em = emf.createEntityManager()) {
            Parcel parcel = em.find(Parcel.class, trackingNumber);
            parcel.setDeliveryStatus(newStatus);
            em.getTransaction().begin();
            em.merge(parcel);
            em.getTransaction().commit();
        } catch (Exception ex) {
            throw new RuntimeException("Error updating parcel status ", ex);
        }
    }

     */

    // chats option b for updating a parcel. em.find only works with PK and not tracking num
    public void updateStatus(String trackingNumber, DeliveryStatus newStatus) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            TypedQuery<Parcel> query = em.createQuery(
                    "SELECT p FROM Parcel p WHERE p.trackingNumber = :trackingNumber",
                    Parcel.class
            );
            query.setParameter("trackingNumber", trackingNumber);

            Parcel parcel = query.getSingleResult();
            parcel.setDeliveryStatus(newStatus);

            em.merge(parcel);
            em.getTransaction().commit();
        } catch (Exception ex) {
            throw new RuntimeException("Error updating parcel status ", ex);
        }
    }



    // em.find bruger PK i DB som reference. Denne metoder tager tracking num som parameter
    // og derfor virker denne metode ikke.
    /*
    public boolean deleteParcelByTrackingNumber(String trackingNumber) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(trackingNumber);
            em.getTransaction().commit();
        } catch (Exception ex) {
            throw new RuntimeException("Error deleting parcel ", ex);
        }
        return false;
    }
     */

    // same with em.remove only takes a PK and not tracking num
    public boolean deleteParcelByTrackingNumber(String trackingNumber) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Query for the parcel by tracking number
            TypedQuery<Parcel> query = em.createQuery(
                    "SELECT p FROM Parcel p WHERE p.trackingNumber = :trackingNumber",
                    Parcel.class
            );
            query.setParameter("trackingNumber", trackingNumber);
            List<Parcel> results = query.getResultList();

            // if statement if results = empty
            if (results.isEmpty()) {
                em.getTransaction().commit(); // nothing to delete
                return false;
            }

            // Delete the first (and should be only) matching parcel
            Parcel parcel = results.get(0);
            em.remove(parcel);
            em.getTransaction().commit();
            return true;

        } catch (Exception ex) {
            throw new RuntimeException("Error deleting parcel ", ex);
        }
    }


}
