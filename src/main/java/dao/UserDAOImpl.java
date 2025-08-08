package dao;

import entity.User;

import java.util.List;
import java.util.Optional;
import javax.persistence.*;

public class UserDAOImpl implements UserDAO {
    private final EntityManager em;

    public UserDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<User> findAll() {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
            return query.getResultList();
    }

    @Override
    public Optional<User> findById(String id) {
            return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public boolean create(User user) throws EntityExistsException {
        try {
            em.getTransaction().begin();
            if (user.getId() != null && em.find(User.class, user.getId()) != null) {
                throw new EntityExistsException("User already exists: " + user.getId());
            }
            em.persist(user);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                return false;
            }
            throw e;
        }
    }

    @Override
    public User update(User user) throws EntityNotFoundException {
        try {
            em.getTransaction().begin();
            User managed = em.find(User.class, user.getId());
            if (managed == null) {
                throw new EntityNotFoundException("User not found: " + user.getId());
            }
            User merged = em.merge(user);
            em.getTransaction().commit();
            return merged;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public void delete(String id) throws EntityNotFoundException {
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, id);
            if (user == null) {
                throw new EntityNotFoundException("User not found: " + id);
            }
            em.remove(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            return query.getResultStream().findFirst();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        try {
            Query query = em.createQuery(
                    "SELECT COUNT(u) FROM User u WHERE u.email = :email");
            query.setParameter("email", email);
            return (Long) query.getSingleResult() > 0;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        }
    }

    @Override
    public long count() {
        try {
            Query query = em.createQuery("SELECT COUNT(u) FROM User u");
            return (Long) query.getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }
}
