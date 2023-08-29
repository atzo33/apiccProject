package apicc.service;

import apicc.model.dto.GroupAdminDTO;
import apicc.model.dto.GroupDTO;
import apicc.model.dto.GroupRequestDTO;

import java.util.List;

public interface GroupService {

    GroupDTO createGroup(GroupDTO groupDTO);

    GroupDTO deleteGroup(int id);

    GroupDTO updateGroup(GroupDTO groupDTO);

    GroupDTO findById(int id);
    List<GroupDTO> findAll();

    List<GroupDTO>findAllByUser(int id);

    GroupAdminDTO createNewAdmin(int groupID, int userID);

    GroupRequestDTO joinGroup(int id);

    List<GroupRequestDTO> getAllPendingMembersForGroup(int id);
}
