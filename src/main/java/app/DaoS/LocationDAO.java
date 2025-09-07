package app.DaoS;

import app.entities.Location;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class LocationDAO implements Idao<Location, Integer> {

    private final EntityManagerFactory emf;

    public LocationDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }


    @Override
    public Location create(Location location) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(location);
            em.getTransaction().commit();
            return location;
        } catch (Exception ex) {
            throw new RuntimeException(" Error creatin new location", ex);
        }
    }

    @Override
    public Location getById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Location.class, id);
        } catch (Exception ex) {
            throw new RuntimeException("Error getting location id ", ex);
        }
    }

    @Override
    public Location update(Location location) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(location);
            em.getTransaction().commit();
            return location;
        } catch (Exception ex) {
            throw new RuntimeException("Error updating location id ", ex);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Location location = em.find(Location.class, id);
            em.remove(location);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            throw new RuntimeException("Error deleting location id ", ex);
        }
    }

    @Override
    public List<Location> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Location> query = em.createQuery("select l from Location l", Location.class);
            return query.getResultList();
        } catch (Exception ex) {
            throw new RuntimeException(" Error getting all locations", ex);
        }
    }
}
