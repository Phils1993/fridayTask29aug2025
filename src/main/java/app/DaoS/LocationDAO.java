package app.DaoS;

import app.DTOMapper.DTOMapper;
import app.entities.Location;
import app.records.LocationDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class LocationDAO implements Idao<Location, Integer> , IDTODAO<LocationDTO, Integer> {

    private final EntityManagerFactory emf;

    public LocationDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }


    @Override
    public LocationDTO getDtoById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Location location = em.find(Location.class, id);
            return DTOMapper.toLocationDTO(location);
        } catch (Exception ex) {
            throw new RuntimeException("Error getting LocationDTO by ID", ex);
        }
    }

    @Override
    public List<LocationDTO> getAllDtos() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Location> query = em.createQuery("SELECT l FROM Location l", Location.class);
            List<Location> locations = query.getResultList();
            return locations.stream()
                    .map(DTOMapper::toLocationDTO)
                    .toList();
        } catch (Exception ex) {
            throw new RuntimeException("Error getting all LocationDTOs", ex);
        }
    }

    @Override
    public Location create(Location location) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(location);
            em.getTransaction().commit();
            return location;
        } catch (Exception ex) {
            throw new RuntimeException("Error creating new location", ex);
        }
    }

    @Override
    public Location getById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Location.class, id);
        } catch (Exception ex) {
            throw new RuntimeException("Error getting location by ID", ex);
        }

    }

    @Override
    public Location update(Location location) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Location updated = em.merge(location);
            em.getTransaction().commit();
            return updated;
        } catch (Exception ex) {
            throw new RuntimeException("Error updating location", ex);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Location location = em.find(Location.class, id);
            if (location != null) {
                em.remove(location);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().rollback();
            return false;
        } catch (Exception ex) {
            throw new RuntimeException("Error deleting Location", ex);
        }
    }

    @Override
    public List<Location> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT l FROM Location l", Location.class).getResultList();
        } catch (Exception ex) {
            throw new RuntimeException("Error getting all Locations", ex);
        }
    }
}
