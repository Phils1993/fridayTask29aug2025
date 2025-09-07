package app.DaoS;


import app.entities.Shipment;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * DAO class for managing Shipment entities and their corresponding DTOs.
 * Provides CRUD operations and DTO projections using JPA.
 */
public class ShipmentDAO implements Idao<Shipment, Integer> {

    // Factory for creating EntityManager instances
    private final EntityManagerFactory emf;

    // Constructor initializes the DAO with an EntityManagerFactory
    public ShipmentDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }


    @Override
    public Shipment create(Shipment shipment) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(shipment);
            em.getTransaction().commit();
            return shipment;
        } catch(Exception ex) {
            throw new RuntimeException("No shipment created",ex);
        }
    }

    @Override
    public Shipment getById(Integer id) {
        try(EntityManager em = emf.createEntityManager()) {
            Shipment shipment = em.find(Shipment.class, id);
            return shipment;
        } catch(Exception ex) {
            throw new RuntimeException("No shipment found",ex);
        }

    }

    @Override
    public Shipment update(Shipment shipment) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(shipment);
            em.getTransaction().commit();
            return shipment;
        }  catch(Exception ex) {
            throw new RuntimeException("No shipment updated",ex);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try(EntityManager em = emf.createEntityManager()) {
            Shipment shipment = em.find(Shipment.class, id);
            em.getTransaction().begin();
            em.remove(shipment);
            em.getTransaction().commit();
            return true;
        }  catch(Exception ex) {
            throw new RuntimeException("No shipment deleted",ex);
        }
    }

    @Override
    public List<Shipment> getAll() {
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<Shipment> query = em.createQuery("SELECT s FROM Shipment s", Shipment.class);
            return query.getResultList();
        } catch(Exception ex) {
            throw new RuntimeException("No shipment list found",ex);
        }
    }
}
