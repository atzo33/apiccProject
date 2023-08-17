package apicc.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "content")
    private String content;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Image> images = new HashSet<>();

    @ManyToOne
    private User user;

    @Column(name = "deleted")
    private boolean isDeleted;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Comment>comments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Reactions> reactions = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Report> reports = new HashSet<>();

    @ManyToOne
    private Group group;
}
