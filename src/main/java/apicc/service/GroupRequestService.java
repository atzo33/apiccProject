package apicc.service;

import apicc.model.dto.GroupRequestDTO;
import apicc.model.entity.Group;
import apicc.model.entity.GroupRequest;

import java.util.List;

public interface GroupRequestService {

    GroupRequestDTO save(Group group);
    GroupRequestDTO saveForUser(Group group, int userID);
    List<GroupRequestDTO> findAllMembersByGroupID(int id);
    GroupRequestDTO update(boolean updateStatus, int groupRequestID);
    List<GroupRequestDTO> findAllRequestsOnPendingByGroupID(int id);

}
