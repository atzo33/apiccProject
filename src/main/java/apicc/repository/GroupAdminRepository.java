package apicc.repository;

import apicc.model.entity.GroupAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupAdminRepository extends JpaRepository<GroupAdmin, Integer> {

    @Query("SELECT DISTINCT ga FROM GroupAdmin ga WHERE ga.adminAt.id = ?1 AND ga.isDeleted = false")
    List<GroupAdmin> findAllByGroupID(Integer id);

    @Query("SELECT DISTINCT ga FROM GroupAdmin ga WHERE ga.id = ?1")
    Optional<GroupAdmin> findById(int id);

    @Query("SELECT DISTINCT ga FROM GroupAdmin ga WHERE ga.adminAt.id = ?1 AND ga.user.id = ?2 AND ga.isDeleted = false")
    Optional<GroupAdmin> findByUserIDAndGroupID(int groupID, int userID);

    @Query("SELECT DISTINCT ga FROM GroupAdmin ga WHERE ga.user.id = ?1 AND ga.isDeleted = false")
    List<GroupAdmin> findAllByUserID(int id);
}
