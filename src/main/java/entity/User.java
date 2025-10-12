package entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "user", schema = "polysys", uniqueConstraints = {
        @UniqueConstraint(name = "Email", columnNames = {"Email"})
})
@NoArgsConstructor
@AllArgsConstructor
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

    public User(String fullname, String username, String email, String password) {
        this.fullName = fullname;
        this.id = username;
        this.email = email;
        this.password = password;
        this.admin = false;
    }
}