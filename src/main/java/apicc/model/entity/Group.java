package apicc.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "group_table")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "description",nullable = false)
    private String description;
    @Column(name = "creation_date",nullable = false)
    private String creationDate;
    @Column(name = "is_suspended")
    private boolean isSuspended;
    @Column(name = "suspended_reason")
    private String suspendedReason;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    private Set<Post> posts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    private Set<GroupRequest>requests = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Banned>forGroup = new HashSet<>();
}
