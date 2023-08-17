package apicc.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "group_request")
public class GroupRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "approved")
    private boolean approved;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "at")
    private LocalDateTime at;
    @JoinColumn(name = "group_id",referencedColumnName = "id")
    @ManyToOne
    private Group group;
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    @ManyToOne
    private User user;

}
