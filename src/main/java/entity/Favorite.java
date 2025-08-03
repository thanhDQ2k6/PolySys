package entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@NamedQueries({
        @NamedQuery(
                name = "Favorite.findByUserId",
                query = "SELECT f FROM Favorite f WHERE f.user.id = :userId ORDER BY f.likeDate DESC"
        ),
        @NamedQuery(
                name = "Favorite.findByVideoId",
                query = "SELECT f FROM Favorite f WHERE f.video.id = :videoId ORDER BY f.likeDate DESC"
        ),
        @NamedQuery(
                name = "Favorite.findByUserAndVideo",
                query = "SELECT f FROM Favorite f WHERE f.user.id = :userId AND f.video.id = :videoId"
        )
})
@Entity
@Table(name = "favorite", schema = "polysys",
        uniqueConstraints = @UniqueConstraint(
                name = "unique_favorite",
                columnNames = {"VideoId", "UserId"}))
@Getter
@Setter
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "VideoId", nullable = false)
    private Video video;

    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    @Column(name = "LikeDate", nullable = false)
    private LocalDate likeDate;
}