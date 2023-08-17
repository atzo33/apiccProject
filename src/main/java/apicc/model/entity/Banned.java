package apicc.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "banned")
public class Banned {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp")
    private Date timestamp;
    @JoinColumn(name = "group_admin_id",referencedColumnName = "id")
    @ManyToOne
    private GroupAdmin groupAdmin;
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    @ManyToOne
    User user;
    @JoinColumn(name = "group_id",referencedColumnName = "id")
    @ManyToOne
    Group group;

}
