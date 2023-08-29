package apicc.repository;

import apicc.model.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Component
@Repository
public interface GroupRepository extends JpaRepository<Group,Integer> {
    @Query("SELECT g FROM Group g WHERE g.isSuspended = FALSE")
    List<Group> findAllGroups();

    @Query("SELECT g FROM GroupRequest gr JOIN gr.groupTarget g WHERE gr.approved = true AND gr.userRequesting.id = :userId")
    List<Group> getAllUserGroups(Integer userId);
}
