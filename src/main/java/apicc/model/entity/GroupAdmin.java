package apicc.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "group_admin")
public class GroupAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    private User user;
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Banned> by = new HashSet<>();
}
