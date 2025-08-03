package lab5;

import javax.persistence.EntityManager;

public class LogDAO {
    private final EntityManager em;

    public LogDAO(EntityManager em) { this.em = em; }

    public void create(Log log) {
        try {
            em.getTransaction().begin();
            em.persist(log);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        }
    }
}
