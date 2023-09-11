package apicc.repository;

import apicc.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c FROM Comment c LEFT JOIN c.post LEFT JOIN FETCH c.reactions LEFT JOIN FETCH c.replies  WHERE c.post.id = ?1 AND c.isDeleted = false")
    List<Comment> findAllPostComments(int id);

    @Query("SELECT c FROM Comment c LEFT JOIN c.post LEFT JOIN FETCH c.reactions LEFT JOIN FETCH c.replies  WHERE c.id = ?1 AND c.isDeleted = false")
    Optional<Comment> findById(int id);
}
