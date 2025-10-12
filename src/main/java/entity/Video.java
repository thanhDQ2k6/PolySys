package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Poster", nullable = false)
    private String posterUrl;  // Thumbnail URL

    @Lob
    @Column(name = "`Desc`", columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "Active", nullable = false)
    private Boolean active = false;

    @Column(name = "Views", nullable = false)
    private Integer views = 0;

    @Column(name = "Link", nullable = false)
    private String link;

    // Relationships
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    @JsonIgnore // Ngăn Jackson lặp vô hạn khi chuyển sang JSON
    private Set<Favorite> favorites;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    @JsonIgnore // Ngăn Jackson lặp vô hạn khi chuyển sang JSON
    private Set<Share> shares;
}