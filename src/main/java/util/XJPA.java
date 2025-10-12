package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class XJPA implements AutoCloseable {

    /**
     * XJPA is a utility class for managing JPA EntityManager instances and factory.
     * It provides a singleton EntityManagerFactory and allows creating an instance
     * of this class to use with try-with-resources for automatic EntityManager closing.
     */

    // Singleton EntityManagerFactory
    private static EntityManagerFactory emf;
    private EntityManager em;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("polysysPU");
            System.out.println("\nEntityManagerFactory created");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create EntityManagerFactory", e);
        }
    }

    /**
     * Constructor for XJPA.
     * Creates and provides a new EntityManager instance.
     */
    public XJPA() {
        if (emf == null || !emf.isOpen()) {
            throw new IllegalStateException("EntityManagerFactory is not initialized or closed");
        }
        this.em = emf.createEntityManager();
        System.out.println("EntityManager created");
    }

    public EntityManager getEntityManager() {
        if (this.em == null || !this.em.isOpen()) {
            throw new IllegalStateException("EntityManager is not open");
        }
        return this.em;
    }

    /**
     * Closes the EntityManager instance held by this object.
     * This method is the implementation for the AutoCloseable interface.
     */
    @Override
    public void close() {
        if (this.em != null && this.em.isOpen()) {
            this.em.close();
            System.out.println("EntityManager closed");
        }
    }

    /**
     * Closes the singleton EntityManagerFactory.
     * This method must be called when the application shuts down.
     */
    public static void closeEmf() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("EntityManagerFactory closed");
        } else {
            System.out.println("EntityManagerFactory is already closed or not initialized");
        }
    }
}