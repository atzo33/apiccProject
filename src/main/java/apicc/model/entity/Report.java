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
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "reason",nullable = false)
    @Enumerated(EnumType.STRING)
    private EReportType reason;

    @Column(name = "timeOfReport",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @JoinColumn(name = "user_id",referencedColumnName = "id")
    @ManyToOne
    private User byUser;

    @Column(name = "accepted",nullable = false)
    private boolean accepted;

    @Column(name = "deleted",nullable = false)
    private boolean isDeleted;
    @JoinColumn(name = "post_id",referencedColumnName = "id")
    @ManyToOne
    private Post post;
    @JoinColumn(name = "comment_id",referencedColumnName = "id")
    @ManyToOne
    private Comment comment;
}
