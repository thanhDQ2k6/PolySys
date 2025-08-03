package entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user", schema = "polysys", uniqueConstraints = {
        @UniqueConstraint(name = "Email", columnNames = {"Email"})
})
@Getter
@Setter
public class User {
    @Id
    @Column(name = "Id", nullable = false, length = 20)
    private String id;

    @Column(name = "Password", nullable = false, length = 50)
    private String password;

    @Column(name = "FullName", nullable = false, length = 50)
    private String fullName;

    @Column(name = "Email", nullable = false, length = 50)
    private String email;

    @Column(name = "Admin", nullable = false)
    private Boolean admin = false;

    // Relationships
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Favorite> favorites;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Share> shares;
}