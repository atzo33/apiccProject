package apicc.model.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.naming.Name;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDateTime lastLogin;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles role;


    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> friends = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Post>posts = new HashSet<>();

    @Column(name = "blocked")
    private Boolean isBlocked;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "id")
    private Set<Comment>comments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Reactions>reactions = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Report>reports = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    private Set<FriendRequest>receivedRequests = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    private Set<FriendRequest>sentRequests = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY)

    private Set<GroupRequest> requests = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY)
    private Set<GroupAdmin>admins = new HashSet<>();

    @OneToMany(fetch =  FetchType.LAZY)
    private Set<Banned>towards = new HashSet<>();





}
