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
@Table(name = "friend_request")
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "approved",nullable = false)
    private boolean approved;
    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "at",nullable = true)
    private LocalDateTime at;

    @JoinColumn(name = "sender_id",referencedColumnName = "id")
    @ManyToOne
    private User sender;

    @JoinColumn(name = "receiver_id",referencedColumnName = "id")
    @ManyToOne
    private User receiver;
}
