package app.DaoS;

import app.DTOMapper.DTOMapper;
import app.entities.Parcel;
import app.records.ParcelDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;


public class ParcelDAO implements Idao<Parcel, Integer>, IDTODAO<ParcelDTO, Integer> {

    private final EntityManagerFactory emf;

    public ParcelDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }


    @Override
    public ParcelDTO getDtoById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Parcel parcel = em.find(Parcel.class, id);
            return DTOMapper.toParcelDTO(parcel);
        } catch (Exception ex) {
            throw new RuntimeException("Error getting ParcelDTO by ID", ex);
        }
    }

    @Override
    public List<ParcelDTO> getAllDtos() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Parcel> query = em.createQuery("SELECT p FROM Parcel p", Parcel.class);
            List<Parcel> parcels = query.getResultList();
            return parcels.stream()
                    .map(DTOMapper::toParcelDTO)
                    .toList();
        } catch (Exception ex) {
            throw new RuntimeException("Error getting all ParcelDTOs", ex);
        }
    }


    @Override
    public Parcel create(Parcel parcel) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(parcel);
            em.getTransaction().commit();
            return parcel;
        } catch (Exception ex) {
            throw new RuntimeException("Error creating parcel", ex);
        }
    }


    @Override
    public Parcel getById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Parcel.class, id);
        } catch (Exception ex) {
            throw new RuntimeException("Error getting parcel by ID", ex);
        }
    }


    @Override
    public Parcel update(Parcel parcel) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Parcel updated = em.merge(parcel);
            em.getTransaction().commit();
            return updated;
        } catch (Exception ex) {
            throw new RuntimeException("Error updating parcel", ex);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Parcel parcel = em.find(Parcel.class, id);
            if (parcel != null) {
                em.remove(parcel);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().rollback();
            return false;
        } catch (Exception ex) {
            throw new RuntimeException("Error deleting parcel", ex);
        }
    }

    @Override
    public List<Parcel> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT p FROM Parcel p", Parcel.class).getResultList();
        } catch (Exception ex) {
            throw new RuntimeException("Error getting all parcels", ex);
        }
    }
}

