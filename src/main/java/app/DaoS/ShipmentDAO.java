package app.DaoS;

import app.DTOMapper.DTOMapper;
import app.entities.Shipment;
import app.records.ShipmentDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ShipmentDAO implements Idao<Shipment, Integer>, IDTODAO<app.records.ShipmentDTO, Integer> {
    private final EntityManagerFactory emf;

    public ShipmentDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }


    @Override
    public ShipmentDTO getDtoById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<ShipmentDTO> query = em.createQuery(
                    "SELECT new app.records.ShipmentDTO(" +
                            "s.id, " +
                            "s.parcel.trackingNumber, " +
                            "s.sourceLocation.address, " +
                            "s.destinationLocation.address, " +
                            "s.shipmentDateTime) " +
                            "FROM Shipment s " +
                            "WHERE s.id = :id", ShipmentDTO.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception ex) {
            throw new RuntimeException("Error getting ShipmentDTO by ID", ex);
        }
    }


    @Override
    public Shipment create(Shipment shipment) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(shipment);
            em.getTransaction().commit();
            return shipment;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return shipment;
    }

    @Override
    public Shipment getById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Shipment.class, id);
        } catch (Exception ex) {
            throw new RuntimeException("Error getting Shipment by ID", ex);
        }
    }

    @Override
    public Shipment update(Shipment shipment) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Shipment updated = em.merge(shipment);
            em.getTransaction().commit();
            return updated;
        } catch (Exception ex) {
            throw new RuntimeException("Error updating Shipment", ex);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Shipment shipment = em.find(Shipment.class, id);
            if (shipment != null) {
                em.remove(shipment);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().rollback();
            return false;
        } catch (Exception ex) {
            throw new RuntimeException("Error deleting Shipment", ex);
        }
    }

    @Override
    public List<Shipment> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT s FROM Shipment s", Shipment.class).getResultList();
        } catch (Exception ex) {
            throw new RuntimeException("Error getting all Shipments", ex);
        }
    }

    @Override
    public List<ShipmentDTO> getAllDtos() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Shipment> query = em.createQuery(
                    "SELECT s FROM Shipment s " +
                            "JOIN FETCH s.parcel " +
                            "JOIN FETCH s.sourceLocation " +
                            "JOIN FETCH s.destinationLocation", Shipment.class);
            List<Shipment> shipments = query.getResultList();
            return shipments.stream()
                    .map(DTOMapper::toShipmentDTO)
                    .toList();
        } catch (Exception ex) {
            throw new RuntimeException("Error getting all ShipmentDTOs", ex);
        }
    }
}
