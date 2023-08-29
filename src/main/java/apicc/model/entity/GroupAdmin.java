package apicc.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@Table(name = "group_admin")
public class GroupAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Group adminAt;

    @OneToMany
    private Set<Banned> bans = new HashSet<>();

    @Column
    private boolean isDeleted;
}
