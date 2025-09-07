package app.DaoS;

import app.DTOMapper.DTOMapper;
import app.entities.Shipment;
import app.records.ShipmentDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * DAO class for managing Shipment entities and their corresponding DTOs.
 * Provides CRUD operations and DTO projections using JPA.
 */
public class ShipmentDAO implements Idao<Shipment, Integer>, IDTODAO<ShipmentDTO, Integer> {

    // Factory for creating EntityManager instances
    private final EntityManagerFactory emf;

    // Constructor initializes the DAO with an EntityManagerFactory
    public ShipmentDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * Retrieves a ShipmentDTO by its ID using a JPQL constructor expression.
     * Includes related Parcel and Location data.
     *
     * @param id the ID of the shipment
     * @return a ShipmentDTO containing simplified shipment data
     */
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

    /**
     * Persists a new Shipment entity to the database.
     *
     * @param shipment the Shipment entity to create
     * @return the persisted Shipment entity
     */
    @Override
    public Shipment create(Shipment shipment) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(shipment);
            em.getTransaction().commit();
            return shipment;
        } catch (Exception ex) {
            ex.printStackTrace(); // Consider logging instead
        }
        return shipment;
    }

    /**
     * Retrieves a Shipment entity by its ID.
     *
     * @param id the ID of the shipment
     * @return the Shipment entity, or null if not found
     */
    @Override
    public Shipment getById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Shipment.class, id);
        } catch (Exception ex) {
            throw new RuntimeException("Error getting Shipment by ID", ex);
        }
    }

    /**
     * Updates an existing Shipment entity in the database.
     *
     * @param shipment the Shipment entity with updated data
     * @return the merged Shipment entity
     */
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

    /**
     * Deletes a Shipment entity by its ID.
     *
     * @param id the ID of the shipment to delete
     * @return true if deletion was successful, false otherwise
     */
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

    /**
     * Retrieves all Shipment entities from the database.
     *
     * @return a list of all Shipment entities
     */
    @Override
    public List<Shipment> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT s FROM Shipment s", Shipment.class).getResultList();
        } catch (Exception ex) {
            throw new RuntimeException("Error getting all Shipments", ex);
        }
    }

    /**
     * Retrieves all ShipmentDTOs by fetching related Parcel and Location entities.
     *
     * @return a list of ShipmentDTOs with simplified shipment data
     */
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
