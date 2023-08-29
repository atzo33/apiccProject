package apicc.repository;

import apicc.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Component
@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    @Query("SELECT DISTINCT p " +
            "FROM Post p " +
            "LEFT JOIN FETCH p.comments" +
            " LEFT JOIN FETCH p.images " +
            "WHERE p.isDeleted = false " +
            "AND p.user.id = ?1" )
    List<Post> findAllPostsByUser(int id);


    @Query("SELECT DISTINCT p " +
            "FROM Post p " +
            "LEFT JOIN FETCH p.comments" +
            " LEFT JOIN FETCH p.images " +
            "WHERE p.isDeleted = false " +
            "AND p.group.id = ?1" )
    List<Post> findAllPostsByGroup(int id);


    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.comments LEFT JOIN FETCH p.images WHERE p.isDeleted = false AND p.id = ?1")
    Optional<Post> findFirstByIdWithCollections(int id);
}
