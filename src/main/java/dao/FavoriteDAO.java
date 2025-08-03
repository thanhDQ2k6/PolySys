package dao;

import entity.Favorite;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

public interface FavoriteDAO {
    List<Favorite> findByUserId(String userId);

    List<Favorite> findByVideoId(String videoId);

    Optional<Favorite> findByUserAndVideo(String userId, String videoId);

    void create(Favorite favorite) throws EntityExistsException;

    void delete(String userId, String videoId) throws EntityNotFoundException;

    long countByVideoId(String videoId);
}