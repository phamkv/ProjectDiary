package de.phamkv.projectdiary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "profiles",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username")
        })
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "roles")
    private Set<String> roles = new HashSet<>(Collections.singletonList("ROLE_USER"));

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
