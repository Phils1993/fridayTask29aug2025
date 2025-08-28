package app.DaoS;

import app.InterFaces.Prepersist;
import app.InterFaces.Preupdate;
import app.entities.Parcel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ParcelDAO implements Preupdate, Prepersist {

    private final EntityManagerFactory emf;

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

    public boolean updateParcel(Parcel parcel) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(parcel);
            em.getTransaction().commit();
        } catch (Exception ex) {
            throw new RuntimeException("Error updating parcel ", ex);
        }
        return false;
    }

    public boolean deleteParcel(Parcel parcel) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(parcel);
            em.getTransaction().commit();
        } catch (Exception ex) {
            throw new RuntimeException("Error deleting parcel ", ex);
        }
        return false;
    }

    @Override
    public void preUpdate() {

    }

    @Override
    public void prePersist() {

    }
}
