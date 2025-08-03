package dao;

import entity.User;

import java.util.Optional;

public interface UserDAO extends GenericDAO<User, String> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}