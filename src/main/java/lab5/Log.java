package lab5;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "log", schema = "polysys")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "Uri")
    private String uri;

    @Column(name = "AccessTime", nullable = false)
    private Instant accessTime;

    @Column(name = "Username", length = 50)
    private String username;

    public Log(String uri, String username) {
        this.uri = uri;
        this.username = username;
        this.accessTime = Instant.now();
    }

    public Log() {}
}