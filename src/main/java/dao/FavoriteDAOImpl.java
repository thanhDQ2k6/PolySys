package dao;

import entity.Favorite;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

public class FavoriteDAOImpl implements FavoriteDAO {
    private final EntityManager em;

    public FavoriteDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Favorite> findByUserId(String userId) {
            TypedQuery<Favorite> query = em.createNamedQuery("Favorite.findByUserId", Favorite.class);
            query.setParameter("userId", userId);
            return query.getResultList();
    }

    @Override
    public List<Favorite> findByVideoId(String videoId) {
            TypedQuery<Favorite> query = em.createNamedQuery("Favorite.findByVideoId", Favorite.class);
            query.setParameter("videoId", videoId);
            return query.getResultList();
    }

    @Override
    public Optional<Favorite> findByUserAndVideo(String userId, String videoId) {
            TypedQuery<Favorite> query = em.createNamedQuery("Favorite.findByUserAndVideo", Favorite.class);
            query.setParameter("userId", userId);
            query.setParameter("videoId", videoId);
            return query.getResultStream().findFirst();
    }

    @Override
    public void create(Favorite favorite) throws EntityExistsException {
        try {
            em.getTransaction().begin();
            if (favorite.getUser() != null && favorite.getVideo() != null && findByUserAndVideo(favorite.getUser().getId(), favorite.getVideo().getId()) != null) {
                throw new EntityExistsException("Favorite already exists: " + favorite.getId());
            }
            em.persist(favorite);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    @Override
    public void delete(String userId, String videoId) throws EntityNotFoundException {
        try {
            em.getTransaction().begin();
            Favorite fav = findByUserAndVideo(userId, videoId)
                    .orElseThrow(() -> new EntityNotFoundException("Favorite not found"));
            em.remove(fav);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    @Override
    public long countByVideoId(String videoId) {
        try {
            Query query = em.createQuery("SELECT COUNT(f) FROM Favorite f WHERE f.video.id = :videoId");
            query.setParameter("videoId", videoId);
            return (Long) query.getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }
}
