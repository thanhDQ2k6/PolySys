package dao;

import entity.Video;

import java.util.List;
import java.util.Optional;
import javax.persistence.*;

public class VideoDAOImpl implements VideoDAO {
    private final EntityManager em;

    public VideoDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Video> findAll() {
            TypedQuery<Video> query = em.createQuery("SELECT v FROM Video v", Video.class);
            return query.getResultList();
    }

    @Override
    public Optional<Video> findById(String id) {
            return Optional.ofNullable(em.find(Video.class, id));
    }

    @Override
    public boolean create(Video video) throws EntityExistsException {
        try {
            em.getTransaction().begin();
            if (video.getId() != null && em.find(Video.class, video.getId()) != null) {
                throw new EntityExistsException("Video already exists: " + video.getId());
            }
            em.persist(video);
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
    public Video update(Video video) throws EntityNotFoundException {
        try {
            em.getTransaction().begin();
            Video managed = em.find(Video.class, video.getId());
            if (managed == null) {
                throw new EntityNotFoundException("Video not found: " + video.getId());
            }
            Video merged = em.merge(video);
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
            Video video = em.find(Video.class, id);
            if (video == null) {
                throw new EntityNotFoundException("Video not found: " + id);
            }
            em.remove(video);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public List<Video> findByActiveTrue() {
        try {
            TypedQuery<Video> query = em.createQuery(
                    "SELECT v FROM Video v WHERE v.active = true", Video.class);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Video> findByTitleContaining(String keyword) {
        try {
            TypedQuery<Video> query = em.createQuery(
                    "SELECT v FROM Video v WHERE LOWER(v.title) LIKE LOWER(:keyword)",
                    Video.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public long count() {
        try{
            Query query = em.createQuery("SELECT COUNT(v) FROM Video v");
            return (Long) query.getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public long countByActiveTrue() {
        try{
            Query query = em.createQuery("SELECT COUNT(v) FROM Video v WHERE v.active = true");
            return (Long) query.getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }
}
