package apicc.repository;

import apicc.model.entity.Reactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reactions, Integer> {

    @Query("SELECT r FROM Reactions r WHERE r.postIdReactedTo.id = ?1 AND r.isDeleted = false")
    List<Reactions> findByPostId(int id);

    @Query("SELECT r FROM Reactions r WHERE r.commentIdReactedTo.id = ?1 AND r.isDeleted = false")
    List<Reactions> findByCommentId(int id);


}
