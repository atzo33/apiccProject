package apicc.repository;

import apicc.model.entity.GroupRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Component
@Repository
public interface GroupRequestRepository extends JpaRepository<GroupRequest,Integer> {

    @Query("SELECT DISTINCT gr FROM GroupRequest gr WHERE gr.userRequesting.id = ?1 AND gr.groupTarget.id = ?2")
    Optional<GroupRequest> findFirstByUserIDAndGroupID(int userID, int groupID);

    @Query("SELECT DISTINCT gr FROM GroupRequest gr WHERE gr.approved IS NULL AND gr.groupTarget.id = ?1")
    List<GroupRequest> findAllByPendingAndGroupID(int groupID);

    @Query("SELECT DISTINCT gr FROM GroupRequest gr WHERE gr.id = ?1")
    Optional<GroupRequest> findFirstById(int id);

    @Query("SELECT DISTINCT gr FROM GroupRequest gr WHERE gr.approved = true AND gr.groupTarget.id = ?1")
    List<GroupRequest> findAllByApprovedAndGroupID(int groupID);
}
