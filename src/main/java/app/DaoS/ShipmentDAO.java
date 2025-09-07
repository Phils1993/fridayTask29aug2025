package app.DaoS;

import app.entities.Shipment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ShipmentDAO implements Idao<Shipment, Integer> {
    private final EntityManagerFactory emf;

    public ShipmentDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Shipment create(Shipment shipment) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            if (!em.contains(shipment.getParcel())) {
                shipment.setParcel(em.merge(shipment.getParcel()));
            }
            if(!em.contains(shipment.getLocation())) {
                shipment.setLocation(em.merge(shipment.getLocation()));
            }
            if(!em.contains(shipment.getDestination())) {
                shipment.setDestination(em.merge(shipment.getDestination()));
            }
            em.persist(shipment);
            return shipment;
        }catch (Exception ex) {
            throw new RuntimeException("Error creating shipment", ex);
        }
    }

    @Override
    public Shipment getById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Shipment shipment = em.find(Shipment.class, id);
            em.getTransaction().commit();
            return shipment;
        } catch (Exception ex) {
            throw new RuntimeException("Error getting shipment ID ", ex);
        }
    }

    @Override
    public Shipment update(Shipment shipment) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(shipment);
            em.getTransaction().commit();
            return shipment;
        } catch (Exception ex) {
            throw new RuntimeException("Error updating shipment ID ", ex);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Shipment shipment = em.find(Shipment.class, id);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.remove(shipment);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            throw new RuntimeException("Error deleting shipment ID ", ex);
        }

    }

    @Override
    public List<Shipment> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            TypedQuery<Shipment> query = em.createQuery("SELECT s FROM Shipment s", Shipment.class);
            return query.getResultList();
        } catch (Exception ex) {
            throw new RuntimeException("Error listing all shipments ", ex);
        }
    }
}
