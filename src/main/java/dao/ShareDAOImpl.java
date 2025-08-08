package dao;

import entity.Share;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class ShareDAOImpl implements ShareDAO {
    private final EntityManager em;

    public  ShareDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Share> findByUserId(String userId) {
            TypedQuery<Share> query = em.createNamedQuery("Share.findByUserId", Share.class);
            query.setParameter("userId", userId);
            return query.getResultList();
    }

    @Override
    public List<Share> findByVideoId(String videoId) {
            TypedQuery<Share> query = em.createNamedQuery("Share.findByVideoId", Share.class);
            query.setParameter("videoId", videoId);
            return query.getResultList();
    }

    @Override
    public Optional<Share> findByUserAndVideo(String userId, String videoId) {
            TypedQuery<Share> query = em.createNamedQuery("Share.findByUserAndVideo", Share.class);
            query.setParameter("userId", userId);
            query.setParameter("videoId", videoId);
            return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public void create(Share share) {
        try {
            em.getTransaction().begin();
            em.persist(share);
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
            Share shr = findByUserAndVideo(userId, videoId)
                    .orElseThrow(() -> new EntityNotFoundException("Share not found"));
            em.remove(shr);
            em.getTransaction().commit();
        }  catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }

    @Override
    public long countByVideoId(String videoId) {
        try {
            Query query = em.createQuery("SELECT COUNT(s) FROM Share s WHERE s.video.id = :videoId");
            query.setParameter("videoId", videoId);
            return (Long) query.getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }
}
