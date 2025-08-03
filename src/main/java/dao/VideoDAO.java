package dao;

import entity.Video;

import java.util.List;

public interface VideoDAO extends GenericDAO<Video, String> {
    List<Video> findByActiveTrue();

    List<Video> findByTitleContaining(String keyword);

    long countByActiveTrue();
}
