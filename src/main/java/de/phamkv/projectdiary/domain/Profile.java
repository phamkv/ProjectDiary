package de.phamkv.projectdiary.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("profile")
    private List<Post> posts;

    public Profile(String username, String password) {
        this.username = username;
        this.password = password;
    }


    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
