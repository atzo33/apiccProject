package apicc.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "reaction")
public class Reactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private EReaction type;
    @Column(name = "timestamp")
    private LocalDateTime timeOfReaction;
    @Column(name = "deleted")
    private boolean isDeleted;
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    @ManyToOne
    private User reactedBy;
    @JoinColumn(name = "comment_id",referencedColumnName = "id")
    @ManyToOne
    private Comment commentIdReactedTo;
    @JoinColumn(name = "post_id",referencedColumnName = "id")
    @ManyToOne
    private Post postIdReactedTo;
}
