package entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@NamedQueries({
        @NamedQuery(
                name = "Share.findByUserId",
                query = "SELECT s FROM Share s WHERE s.user.id = :userId ORDER BY s.shareDate DESC"
        ),
        @NamedQuery(
                name = "Share.findByVideoId",
                query = "SELECT s FROM Share s WHERE s.video.id = :videoId ORDER BY s.shareDate DESC"
        ),
        @NamedQuery(
                name = "Share.findByUserAndVideo",
                query = "SELECT s FROM Share s WHERE s.user.id = :userId AND s.video.id = :videoId"
        )
})
@Entity
@Table(name = "share", schema = "polysys")
@Getter
@Setter
public class Share {
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

    @Column(name = "Emails", nullable = false, length = 50)
    private String emails;

    @Column(name = "ShareDate", nullable = false)
    private LocalDate shareDate;
}