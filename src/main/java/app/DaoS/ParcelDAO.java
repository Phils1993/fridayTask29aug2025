package app.DaoS;


import app.entities.Parcel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;


public class ParcelDAO implements Idao<Parcel, Integer> {

    private final EntityManagerFactory emf;

    public ParcelDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }


    @Override
    public Parcel create(Parcel parcel) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(parcel);
            em.getTransaction().commit();
            return parcel;

        } catch (Exception e) {
            throw new RuntimeException("No parcel created", e);
        }
    }

    @Override
    public Parcel getById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Parcel.class, id);
        } catch (Exception e) {
            throw new RuntimeException("No parcel found", e);
        }
    }

    @Override
    public Parcel update(Parcel parcel) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(parcel);
            em.getTransaction().commit();
            return parcel;
        } catch (Exception e) {
            throw new RuntimeException("No parcel updated", e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(em.find(Parcel.class, id));
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            throw new RuntimeException("No parcel deleted", e);
        }
    }

    @Override
    public List<Parcel> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Parcel> query = em.createQuery("select p from Parcel p", Parcel.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("No parcels list found", e);
        }
    }
}

