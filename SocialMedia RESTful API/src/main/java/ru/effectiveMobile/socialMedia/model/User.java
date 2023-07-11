package ru.effectiveMobile.socialMedia.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Message> messages;

    @ManyToMany
    @JoinTable(
            name = "user_subscription",
            joinColumns = {@JoinColumn(name = "channel_id")},
            inverseJoinColumns = @JoinColumn(name = "subscriber_id"))
    private Set<User> subscription = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_subscription",
            joinColumns = {@JoinColumn(name = "subscriber_id")},
            inverseJoinColumns = @JoinColumn(name = "channel_id"))
    private Set<User> subscribers = new HashSet<>();

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
