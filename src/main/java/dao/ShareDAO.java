package dao;

import entity.Share;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

public interface ShareDAO {
    List<Share> findByUserId(String userId);

    List<Share> findByVideoId(String videoId);

    Optional<Share> findByUserAndVideo(String userId, String videoId);

    void create(Share share);

    void delete(String userId, String videoId) throws EntityNotFoundException;

    long countByVideoId(String videoId);
}
