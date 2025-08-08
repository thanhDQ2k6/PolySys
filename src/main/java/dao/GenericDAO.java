package dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, ID> {
    List<T> findAll();

    Optional<T> findById(ID id);

    boolean create(T entity) throws EntityExistsException;

    T update(T entity) throws EntityNotFoundException;

    void delete(ID id) throws EntityNotFoundException;

    long count();
}