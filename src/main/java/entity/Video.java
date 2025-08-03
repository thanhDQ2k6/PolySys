package entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "video", schema = "polysys")
@Getter
@Setter
public class Video {
    @Id
    @Column(name = "Id", nullable = false, length = 11)
    private String id;

    @Column(name = "Title", nullable = false, length = 50)
    private String title;

    @Column(name = "Poster", nullable = false, length = 50)
    private String posterUrl;  // Thumbnail URL

    @Lob
    @Column(name = "`Desc`", columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "Active", nullable = false)
    private Boolean active = false;

    @Column(name = "Views", nullable = false)
    private Integer views = 0;

    @Column(name = "Link", nullable = false, length = 255)
    private String link;

    // Relationships
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    private Set<Favorite> favorites;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    private Set<Share> shares;
}