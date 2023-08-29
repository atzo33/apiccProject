package apicc.service;

import apicc.model.dto.GroupAdminDTO;
import apicc.model.entity.Group;
import apicc.model.entity.GroupAdmin;
import apicc.model.entity.User;

import java.util.List;

public interface GroupAdminService {

    GroupAdmin createEntity(Group group, User user);

    GroupAdmin delete(int id);

    List <GroupAdminDTO> findAllByGroupID(Integer id);

    boolean isLoggedUserAdminInGroup(int groupID);

    List<GroupAdminDTO> findAllWhereUserIsAdmin(int userID);
}
